package com.marcoabreu.att.profile.data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AbreuM on 29.07.2016.
 */
public abstract class AttParameterScript extends AttParameter implements DynamicScript {
    @XmlAttribute(name = "path")
    private String path;

    @XmlAttribute(name = "method")
    private String method;

    @XmlAttribute(name = "timeout")
    private long timeoutMs;

    @XmlElementRef
    private List<AttParameter> parameters;

    public AttParameterScript() {
        this.parameters = new ArrayList<>();
    }

    public void addParameter(AttParameter attParameter) {
        this.parameters.add(attParameter);
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public long getTimeoutMs() {
        return timeoutMs;
    }

    public void setTimeoutMs(long timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    @Override
    public List<AttParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<AttParameter> parameters) {
        this.parameters = parameters;
    }
}
