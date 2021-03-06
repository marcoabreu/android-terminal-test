package com.marcoabreu.att.device;

import com.marcoabreu.att.communication.BridgeEndpoint;
import com.marcoabreu.att.communication.BridgeMessageListener;
import com.marcoabreu.att.host.HostApp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server for communication between devices and host
 * Created by AbreuM on 02.08.2016.
 */
public class DeviceServer implements Closeable, BridgeEndpoint {
    private static final Logger LOG = LogManager.getLogger();
    private final int port;
    private final DeviceManager deviceManager;
    private ServerSocket serverSocket;
    private PairingListenerThread pairingThreadRunnable;
    private Thread pairingThread;
    private Set<BridgeMessageListener> listeners;

    /**
     * Initialize a server to listen for connections on the specified port
     * @param port Port to listen on
     */
    public DeviceServer(DeviceManager deviceManager, int port) {
        this.deviceManager = deviceManager;
        this.port = port;
        this.listeners = ConcurrentHashMap.newKeySet();
    }

    public void start() throws IOException {
        LOG.debug("Start listening for device pairings on port " + port);
        serverSocket = new ServerSocket(port);
        pairingThreadRunnable = new PairingListenerThread(serverSocket, this);
        pairingThread = new Thread(pairingThreadRunnable);
        pairingThread.setDaemon(true);
        pairingThread.start();
    }

    @Override
    public void close() throws IOException {
        stop();
    }

    public void stop() {
        //Close listener thread and close connection to all devices
        if(pairingThreadRunnable != null) {
            pairingThreadRunnable.stop();
        }
    }

    @Override
    public void registerMessageListener(BridgeMessageListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeMessageListener(BridgeMessageListener listener) {
        listeners.remove(listener);
    }

    public void invokeOnMessage(PairedDevice device, com.marcoabreu.att.communication.message.BaseMessage message) {
        //TODO: Invoke in separate tasks
        for(BridgeMessageListener listener : listeners) {
            try {
                listener.onMessage(device, message);
            } catch (IOException e) {
                LOG.error("Exception during message invokation", e);
            }
        }
    }



    /**
     * Main thread to listen for incoming connections of newly connected devices
     */
    private class PairingListenerThread implements Runnable {
        private final ServerSocket socket;
        private final DeviceServer deviceServer;
        private boolean run = true;
        public PairingListenerThread(ServerSocket socket, DeviceServer deviceServer) {
            this.socket = socket;
            this.deviceServer = deviceServer;
        }

        /**
         * Ask the thread and its children to stop
         */
        public void stop() {
            run = false;
            try {
                socket.close();
            } catch (IOException e) {
            }

            for(PairedDevice device : deviceManager.getPairedDevices()) {
                try {
                    device.close();
                } catch (IOException e) {
                }
            }
        }

        @Override
        public void run() {
            while (run) {
                try {
                    Socket socket = serverSocket.accept(); //new device attempting to connect

                    //out.flush() has to be done first to publish headers
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    out.flush();
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                    PairedDevice pairedDevice = new PairedDevice(deviceServer, in, out);
                    LOG.info("New pairing attempt");

                    //Start listener for incoming messages
                    Thread messageHandlerThread = new Thread(new DeviceMessageThread(pairedDevice, deviceServer));
                    messageHandlerThread.setDaemon(true);
                    pairedDevice.setMessageThread(messageHandlerThread);
                    messageHandlerThread.start();
                } catch (IOException e) {
                    LOG.error("Exception during pairing acceptance procedure", e);
                }
            }
        }
    }

    /**
     * Thread to handle incoming messages of the device
     */
    private class DeviceMessageThread implements Runnable {
        private final PairedDevice pairedDevice;
        private final DeviceServer deviceServer;
        public DeviceMessageThread(PairedDevice pairedDevice, DeviceServer deviceServer) {
            this.pairedDevice = pairedDevice;
            this.deviceServer = deviceServer;
        }

        @Override
        public void run() {
            try (PairedDevice device = pairedDevice) {
                while (true) {
                    com.marcoabreu.att.communication.message.BaseMessage message = device.readMessage();
                    LOG.debug("Received message on device " + device.getSerial() + " : " + message.getClass().getName());
                    deviceServer.invokeOnMessage(device, message);
                }
            } catch (IOException ex) {
                LOG.error("Device " + pairedDevice.getSerial() + " disconnected" , ex);
                DeviceManager.getInstance().invokeOnDeviceUnpaired(pairedDevice);
                HostApp.handleException(ex);
            }
        }
    }

}
