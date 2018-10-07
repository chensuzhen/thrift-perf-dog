package com.su.perf.client.pool;

import com.su.perf.client.endpoint.Endpoint;
import org.apache.thrift.TServiceClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ThriftClientProxy<Iface, TClient extends TServiceClient> implements InvocationHandler {
    private Endpoint endpoint;

    public ThriftClientProxy(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        ThriftClientObject thriftClientObject =  ThriftClientPool.getInstance().borrowClient(this.endpoint);
        TClient client = (TClient)thriftClientObject.gettClient();
        Object result = method.invoke(client, args);
        ThriftClientPool.getInstance().returnClient(this.endpoint, thriftClientObject);
        long millis = System.currentTimeMillis() - start;
        System.out.println("call " + method.getName() + " totally cost " + millis + "mills.");
        return result;
    }
}
