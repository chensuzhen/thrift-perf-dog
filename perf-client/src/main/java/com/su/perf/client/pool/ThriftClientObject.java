package com.su.perf.client.pool;

import com.su.perf.client.endpoint.Endpoint;
import com.su.thrift.SuService;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class ThriftClientObject<TClient extends TServiceClient> {

    private static final int DEFAULT_CLIENT_TIME_OUT = 1000;

    private TClient tClient;
    private Endpoint endpoint;

    public ThriftClientObject(TClient tClient, Endpoint endpoint) {
        this.tClient = tClient;
        this.endpoint = endpoint;
    }

    public TClient gettClient() {
        return tClient;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public static ThriftClientObject createClientObject(Endpoint endpoint) {
        // 设置调用的服务地址-端口
        TTransport transport =
                new TFramedTransport(new TSocket(endpoint.getHost(), endpoint.getPort(), DEFAULT_CLIENT_TIME_OUT));
        // 使用二进制协议
        TProtocol protocol = new TBinaryProtocol(transport);
        // 使用的接口
        SuService.Client client = new SuService.Client(protocol);
        // 打开socket
        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
        }

        return new ThriftClientObject(client, endpoint);
    }

    public static void destoryClientObject(ThriftClientObject thriftClientObject) {
        thriftClientObject.gettClient().getOutputProtocol().getTransport().close();
        thriftClientObject.gettClient().getInputProtocol().getTransport().close();
    }
}
