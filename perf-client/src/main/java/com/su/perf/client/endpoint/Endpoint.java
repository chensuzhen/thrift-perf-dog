package com.su.perf.client.endpoint;

import org.apache.commons.lang3.StringUtils;

public class Endpoint {
    private String host;
    private int port;

    public Endpoint(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return String.format("%s:%d", this.host, this.port);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Endpoint)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        Endpoint other = (Endpoint) obj;
        return StringUtils.equals(host, other.host) && port == other.port;
    }

    @Override
    public int hashCode() {
        return host.hashCode() * 31 & port;
    }
}
