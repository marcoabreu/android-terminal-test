package com.marcoabreu.att.host;

import com.marcoabreu.att.ui.MainForm;
import com.marcoabreu.att.utilities.Configuration;
import com.marcoabreu.att.utilities.FileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBException;
import java.io.File;

public class HostApp {
    private static final Logger LOG = LogManager.getLogger();
    /*
    public static void main(String args[]) {
        System.out.println("Test");

        //establish connection and start the app
        try {
            new AdbServerLauncher().launch();
            JadbConnection jadb = new JadbConnection();
            List<JadbDevice> devices = jadb.getDevices();

            for(JadbDevice device : devices) {
                System.out.println(device.toString());
            }

            JadbDevice curDevice = devices.get(0);


            Transport transport = jadb.createTransport();
            transport.send(String.format("host-serial:%s:forward:tcp:%d;tcp:%d", curDevice.getSerial(), 12022, 12022));
            transport.verifyResponse();

            //Launch app
            PackageManager pm = new PackageManager(curDevice);
            pm.launch(new Package("com.marcoabreu.att"));
            Thread.sleep(5000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JadbException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try (
                Socket echoSocket = new Socket("127.0.0.1", 12022);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ){
            System.out.println("Enter your message:");
            String curMsg = "";

            while((curMsg = stdIn.readLine()).compareToIgnoreCase("exit") != 0) {
                out.write(curMsg + "\r\n");
                out.flush();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /*public static void main(String args[]) {
        Composite sequence = new Sequence(
                new Action(() -> {
                    System.out.println("Action1");
                    try { Thread.sleep(200); } catch(Exception ex) { return false; }
                    return true;
                }),
                new Action(() -> {
                    System.out.println("Action2");
                    try { Thread.sleep(200); } catch(Exception ex) { return false; }
                    return true;
                }),
                new IfStatement(() -> true,
                    new Action(() -> {
                        System.out.println("Hello from the IfStatement");
                        return true;
                    })),
                new Action(() -> {
                    System.out.println("Action3");
                    try { Thread.sleep(200); } catch(Exception ex) { return false; }
                    return true;
                }),
                new IfStatement(() -> false,
                    new Action(() -> {
                        System.out.println("This should not be called");
                        return true;
                    }),
                    new Action(() -> {
                        System.out.println("Hello from the else-case of the IfStatement");
                        return true;
                    })
                ),
                new Action(() -> {
                    System.out.println("Action4");
                    try { Thread.sleep(200); } catch(Exception ex) { return false; }
                    return true;
                })
            );

        Composite repeat = new Repeat(5,
            new Action(() -> {
                System.out.println("Repeat this 5 times, Part 1");
                return true;
            }),
            new Action(() -> {
                System.out.println("Repeat this 5 times, Part 2");
                return true;
            })
        );

        Date end = new Date(new Date().getTime() + 5000);
        Composite repeat2 = new Repeat(() -> new Date().after(end),
            new Action(() -> {
                System.out.println("It's currently " + new Date().toString());
                try { Thread.sleep(200); } catch(Exception ex) { return false; }
                return true;
            })
        );

        Composite compositeToExecute = new Sequence(
            sequence,
            repeat,
            repeat2
        );

        try(Executor ex = new Executor(compositeToExecute)) {
            ex.start();
            while(ex.execute(100) == RunStatus.RUNNING) {
            }
        } catch(Exception e) {

        }
    }*/

    /*public static void main(String args[]) {
        Composite compositeToExecute = new Sequence(
        );

        try(Executor ex = new Executor(compositeToExecute)) {
            ex.start();
            while(ex.execute(100) == RunStatus.RUNNING) {
            }
        } catch(Exception e) {

        }
    }*/

    /*public static void main(String args[]) {
        AttProfile profile = new AttProfile();
        profile.setDescription("Testdescription");
        profile.setIdentifier("TestId");
        profile.setName("Testname");

        AttActionHost action1 = new AttActionHost();
        action1.setName("Set up Koppelfeld");
        action1.setMethod("setSignals");
        action1.setPath("Peripherals/Koppelfeld");
        action1.setTimeoutMs(60000);
        action1.addParameter(new AttParameterText("3G_1", "20"));
        action1.addParameter(new AttParameterText("3G_2", "20"));
        action1.addParameter(new AttParameterText("3G_3", "20"));
        action1.addParameter(new AttParameterText("3G_4", "40"));
        action1.addParameter(new AttParameterText("2G_1", "0"));
        action1.addParameter(new AttParameterText("noise1", "93"));
        profile.addChild(action1);

        AttActionDevice action2 = new AttActionDevice();
        action2.setTargetDevice("device1");
        action2.setName("Await 3G");
        action2.setMethod("AwaitConnectivity");
        action2.setPath("Network/Connectivity");
        action2.setTimeoutMs(60000);
        action2.addParameter(new AttParameterText("networkType", "3G"));
        profile.addChild(action2);

        AttActionDevice action3 = new AttActionDevice();
        action3.setTargetDevice("device1");
        action3.setName("Call device2");
        action3.setMethod("StartCall");
        action3.setPath("Phone/Calls");
        action3.setTimeoutMs(60000);
        profile.addChild(action3);

        AttParameterScriptDevice deviceActionParam1 = new AttParameterScriptDevice();
        deviceActionParam1.setKey("number");
        deviceActionParam1.setTargetDevice("device2");
        deviceActionParam1.setMethod("GetPhoneNumber");
        deviceActionParam1.setPath("Phone/Informations");
        deviceActionParam1.setTimeoutMs(60000);
        action3.addParameter(deviceActionParam1);

        AttActionDevice action4 = new AttActionDevice();
        action4.setTargetDevice("device2");
        action4.setName("Pickup call");
        action4.setMethod("PickupCall");
        action4.setPath("Phone/Calls");
        action4.setTimeoutMs(60000);
        profile.addChild(action4);

        AttSleep sleep1 = new AttSleep(10000);
        sleep1.setName("Wait 10s");
        profile.addChild(sleep1);

        AttActionHost action5 = new AttActionHost();
        action5.setName("Disable 3G");
        action5.setMethod("setSignals");
        action5.setPath("Peripherals/Koppelfeld");
        action5.setTimeoutMs(60000);
        action5.addParameter(new AttParameterText("3G_1", "93"));
        action5.addParameter(new AttParameterText("3G_2", "93"));
        action5.addParameter(new AttParameterText("3G_3", "93"));
        action5.addParameter(new AttParameterText("3G_4", "93"));
        profile.addChild(action5);

        AttActionDevice action6 = new AttActionDevice();
        action6.setTargetDevice("device1");
        action6.setName("Await 2G");
        action6.setMethod("AwaitConnectivity");
        action6.setPath("Network/Connectivity");
        action6.setTimeoutMs(60000);
        action6.addParameter(new AttParameterText("networkType", "2G"));
        profile.addChild(action6);

        try {
            ProfileMarshaller.saveProfile(profile, "");
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        ProfileExecutor pe = new ProfileExecutor(profile);

        pe.start();
    }*/

    /*public static void main(String args[]) {
        AttProfile profile = new AttProfile();
        profile.setDescription("Testdescription");
        profile.setIdentifier("TestId");
        profile.setName("Testname");

        AttWhile while1 = new AttWhile();
        profile.addChild(while1);

        AttEquals equals1 = new AttEquals();
        equals1.setValue1(new AttParameterText("", "as"));
        equals1.setValue2(new AttParameterText("", "as"));
        while1.setCondition(equals1);

        AttSleep sleep1 = new AttSleep(10000);
        sleep1.setName("Wait 10s");

        while1.addChild(sleep1);


        try {
            ProfileMarshaller.saveProfile(profile, "");
        } catch (JAXBException e) {
            e.printStackTrace();
        }


    }*/

    //Working phone-calls
    /*public static void main(String args[]) {
        try {
            JavaInterpreter.init();

            DeviceManager deviceManager = DeviceManager.getInstance();

            //register listeners
            deviceManager.getDeviceServer().registerMessageListener(new DataStorageGetHandler());
            deviceManager.getDeviceServer().registerMessageListener(new DataStorageSaveHandler());
            deviceManager.getDeviceServer().registerMessageListener(new PairRequestHandler());


            deviceManager.start();

            deviceManager.getConnectedDevices().forEach(System.out::println);

            deviceManager.startPairing(deviceManager.getConnectedDevices().get(0));


            AttProfile profile = new AttProfile();
            profile.setDescription("Testdescription");
            profile.setIdentifier("TestId");
            profile.setName("Testname");

            AttActionHost userMessage2 = new AttActionHost();
            userMessage2.setName("Show message");
            userMessage2.setMethod("showMessage");
            userMessage2.setPath("UserInteraction/Message");
            userMessage2.addParameter(new AttParameterText("message", "Setting up Koppelfeld"));
            profile.addChild(userMessage2);

            AttActionHost action1 = new AttActionHost();
            action1.setName("Set up Koppelfeld");
            action1.setMethod("connectCells");
            action1.setPath("Peripherals/Koppelfeld");
            action1.setTimeoutMs(60000);
            action1.addParameter(new AttParameterText("3G_1", "20"));
            action1.addParameter(new AttParameterText("3G_2", "20"));
            action1.addParameter(new AttParameterText("3G_3", "20"));
            action1.addParameter(new AttParameterText("3G_4", "40"));
            action1.addParameter(new AttParameterText("2G_1", "0"));
            action1.addParameter(new AttParameterText("noise1", "93"));
            profile.addChild(action1);

            AttActionHost userMessage8 = new AttActionHost();
            userMessage8.setName("Show message");
            userMessage8.setMethod("showMessage");
            userMessage8.setPath("UserInteraction/Message");
            userMessage8.addParameter(new AttParameterText("message", "Changing signals"));
            profile.addChild(userMessage8);

            AttActionHost action4 = new AttActionHost();
            action4.setName("Set signal strengths");
            action4.setMethod("setSignals");
            action4.setPath("Peripherals/Koppelfeld");
            action4.setTimeoutMs(60000);
            action4.addParameter(new AttParameterText("3G_1", "20"));
            action4.addParameter(new AttParameterText("3G_2", "20"));
            action4.addParameter(new AttParameterText("3G_3", "20"));
            action4.addParameter(new AttParameterText("3G_4", "40"));
            action4.addParameter(new AttParameterText("2G_1", "0"));
            action4.addParameter(new AttParameterText("noise1", "93"));
            profile.addChild(action4);

            AttActionHost userMessage3 = new AttActionHost();
            userMessage3.setName("Show message");
            userMessage3.setMethod("showMessage");
            userMessage3.setPath("UserInteraction/Message");
            userMessage3.addParameter(new AttParameterText("message", "Call with simple parameters"));
            profile.addChild(userMessage3);

            AttActionDevice action2 = new AttActionDevice();
            action2.setName("Call Number");
            action2.setMethod("startCall");
            action2.setPath("Phone/Calls");
            action2.setTargetDevice("device1");
            action2.setTimeoutMs(60000);
            action2.addParameter(new AttParameterText("phoneNumber", "12356700000"));
            profile.addChild(action2);

            AttSleep sleepAa = new AttSleep(5000);
            sleepAa.setName("Wait 5s");
            profile.addChild(sleepAa);


            AttActionDevice action3 = new AttActionDevice();
            action3.setName("layOffCall");
            action3.setMethod("endCall");
            action3.setTargetDevice("device1");
            action3.setPath("Phone/Calls");
            action3.setTimeoutMs(60000);
            profile.addChild(action3);

            AttActionHost userMessage4 = new AttActionHost();
            userMessage4.setName("Show message");
            userMessage4.setMethod("showMessage");
            userMessage4.setPath("UserInteraction/Message");
            userMessage4.addParameter(new AttParameterText("message", "Call with dynamic number"));
            profile.addChild(userMessage4);

            AttActionDevice action5 = new AttActionDevice();
            action5.setName("Call Number");
            action5.setMethod("startCall");
            action5.setPath("Phone/Calls");
            action5.setTargetDevice("device1");
            action5.setTimeoutMs(60000);
            profile.addChild(action5);

            AttParameterScriptDevice deviceActionParam1 = new AttParameterScriptDevice();
            deviceActionParam1.setKey("phoneNumber");
            deviceActionParam1.setTargetDevice("device1");
            deviceActionParam1.setMethod("getPhoneNumber");
            deviceActionParam1.setPath("Phone/Informations");
            deviceActionParam1.setTimeoutMs(60000);
            action5.addParameter(deviceActionParam1);

            AttSleep sleep4 = new AttSleep(10000);
            sleep4.setName("Wait 10s");
            profile.addChild(sleep4);

            AttActionDevice action6 = new AttActionDevice();
            action6.setName("layOffCall");
            action6.setMethod("endCall");
            action6.setTargetDevice("device1");
            action6.setPath("Phone/Calls");
            action6.setTimeoutMs(60000);
            profile.addChild(action6);

            try {
                ProfileMarshaller.saveProfile(profile, "");
            } catch (JAXBException e) {
                e.printStackTrace();
            }

            while(deviceManager.getPairedDevices().size() < 1) {
                Thread.sleep(1000);
                System.out.println("Waiting for connection");
            }

            ProfileExecutor pe = new ProfileExecutor(profile);

            pe.start();

            while(true) {
                RunStatus runState = pe.getRunState();
                //System.out.println(runState);
                if(runState != RunStatus.RUNNING) {
                    break;
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }*/

    private static MainForm form;
    public static void handleException(Exception ex) {
        form.handleException(ex);
    }

    public static void main(String args[]) {
        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        context.setConfigLocation(new File(new File(FileHelper.getApplicationPath().toFile(), "config"), "log4j2.xml").toURI());

        try {
            Configuration.loadConfiguration(new File(new File(FileHelper.getApplicationPath().toFile(), "config"), "config.xml"));
        } catch (JAXBException e) {
            LOG.error("Unable to load configuration", e);
        }

        //Set up config
        if(Configuration.getInstance().getJdk7path().isEmpty()) {
            //Ask user
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select javac.exe from JDK7");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    ".exe files", "exe");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                if(!chooser.getSelectedFile().getName().endsWith("javac.exe")) {
                    throw new RuntimeException("You need to select the java compiler called javac.exe");
                }

                Configuration.getInstance().setJdk7path(chooser.getSelectedFile().getAbsolutePath());
            } else {
                throw new RuntimeException("You did not select a javac compiler");
            }
        }

        if(Configuration.getInstance().getAndroidSdkPath().isEmpty()) {
            //Ask user
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select dx.bat Android-SDK build-tools");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    ".bat files", "bat");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                if(!chooser.getSelectedFile().getName().endsWith("dx.bat")) {
                    throw new RuntimeException("You need to select the dex compiler called dx.bat");
                }

                Configuration.getInstance().setAndroidSdkPath(chooser.getSelectedFile().getAbsolutePath());
            } else {
                throw new RuntimeException("You did not select a dex compiler");
            }
        }


        form = new MainForm();
        form.show();

        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
