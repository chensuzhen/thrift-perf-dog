package com.su.perf.client.demos;

import com.su.thrift.OperateType;
import com.su.thrift.SuService;
import com.su.thrift.SuStruct;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class SyncClient {
    public static final int THREADPOOL_SERVER_PORT = 9002;
    public static final int HSHA_SERVER_PORT = 9001;
    public static final int THREADED_SELECT_SERVER_PORT = 9003;

    public static final String LOCAL_HOST = "localhost";

    public static void calculateFromRemote(String ip, int port, int timeoutMills) throws TException {
        // 设置调用的服务地址-端口
        TTransport transport = new TFramedTransport(new TSocket(ip, port, timeoutMills));
        // 使用二进制协议
        TProtocol protocol = new TBinaryProtocol(transport);
        // 使用的接口
        SuService.Client client = new SuService.Client(protocol);
        // 打开socket
        transport.open();
        SuStruct sumResult = client.simpleCalculate(1, 2, OperateType.SUM);
        SuStruct minusResult = client.simpleCalculate(1, 2, OperateType.MINUS);
        System.out.println(sumResult.toString());
        System.out.println(minusResult.toString());
        transport.close();
    }
    public static void main(String[] args) throws TException {
        calculateFromRemote(LOCAL_HOST, THREADED_SELECT_SERVER_PORT, 2000);
        calculateFromRemote(LOCAL_HOST, HSHA_SERVER_PORT, 2000);
        calculateFromRemote(LOCAL_HOST, THREADPOOL_SERVER_PORT, 200);
    }
}
