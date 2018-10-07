package com.su.perf.client.pool;

import com.su.perf.client.endpoint.Endpoint;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

public class ThriftClientFactory {

    private static final Logger logger = LoggerFactory.getLogger(ThriftClientFactory.class);

    private static String IFACE_SUFFIX = "$Iface";
    private static String CLIENT_SUFFIX = "$Client";

    public static  <Iface, TClient extends TServiceClient> Iface createClient(Class<Iface> iClazz, String ip, int port, int timeout) {
        Class clientClass = getClientClassByIfaceClass(iClazz);
        ThriftClientProxy<Iface, TClient> clientProxy = new ThriftClientProxy<>(new Endpoint(ip, port));

        Iface iface = (Iface) Proxy.newProxyInstance(clientClass.getClassLoader(), new Class[] { iClazz }, clientProxy);
        return iface;
    }

    private static <Iface> String getClientClassNameByIfaceClass(Class<Iface> clazz) {
        return StringUtils.removeEnd(clazz.getName(), IFACE_SUFFIX).concat(CLIENT_SUFFIX);
    }

    private static <TClient extends TServiceClient> Class getClientClassByIfaceClass(Class iClazz) {
        String className = getClientClassNameByIfaceClass(iClazz);
        try {
            Class<TClient> clientClass = (Class<TClient>) iClazz.getClassLoader().loadClass(className);
            return clientClass;
        } catch (IncompatibleClassChangeError e) {
            logger.error("{} class for Iface {} is not compatible", className, iClazz.getName(), e);
            throw e;
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(String.format("Cannot load class %s for Iface %s", className, iClazz.getName()), e);
        }
    }
}
