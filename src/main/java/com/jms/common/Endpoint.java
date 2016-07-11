package com.jms.common;

import java.net.URI;


//https://github.com/hawkular/hawkular-bus/tree/master/hawkular-bus-common/src/main/java/org/hawkular/bus/common/producer
public class Endpoint {
    public static final Endpoint TEMPORARY_QUEUE = new Endpoint(Type.QUEUE, "__tmpQueue__", true);
    public static final Endpoint TEMPORARY_TOPIC = new Endpoint(Type.TOPIC, "__tmpTopic__", true);

    public enum Type {
        QUEUE, TOPIC
    }

    private final Type type;
    private final String name;
    private final boolean isTemp;

    /**
     * An endpoint as specified in URI format: "type://name"
     * 
     * @param destination the endpoint
     * @throws IllegalArgumentException invalid or null destination string
     */
    public Endpoint(String destination) throws IllegalArgumentException {
        if (destination == null) {
            throw new IllegalArgumentException("destination must not be null");
        }

        URI uri;
        try {
            uri = new URI(destination);
        } catch (Exception e) {
            throw new IllegalArgumentException("Not a valid destination URI: " + destination);
        }

        String typeStr = uri.getScheme().toUpperCase();
        Type type;
        try {
            type = Type.valueOf(typeStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("Not a valid destination URI [" + destination + "]; the endpoint type must be either QUEUE or TOPIC: " + typeStr);
        }

        String name = uri.getHost();

        this.type = type;
        this.name = name;
        this.isTemp = false;
    }

    public Endpoint(Type type, String name) {
        this(type, name, false);
    }

    public Endpoint(Type type, String name, boolean isTemp) {
        if (type == null) {
            throw new IllegalArgumentException("type must not be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        this.type = type;
        this.name = name;
        this.isTemp = isTemp;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isTemporary() {
        return isTemp;
    }

    @Override
    public String toString() {
        if (isTemporary()) {
            return "{" + type.name() + "}$TEMPORARY$";
        } else {
            return "{" + type.name() + "}" + name;
        }
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + (isTemp ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Endpoint)) {
            return false;
        }

        Endpoint other = (Endpoint) obj;

        if (type != other.type) {
            return false;
        }

        if (!name.equals(other.name)) {
            return false;
        }

        if (isTemp != other.isTemp) {
            return false;
        }

        return true;
    }

}
