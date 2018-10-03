package com.su.perf.client.rpc;

import com.su.thrift.OperateType;
import com.su.thrift.SuService;
import com.su.thrift.SuStruct;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class DemoClient {
    public static void main(String[] args) throws TException {
        // 设置调用的服务地址-端口
        TTransport transport = new TSocket("localhost", 9090);
        // 使用二进制协议
        TProtocol protocol = new TBinaryProtocol(transport);
        // 使用的接口
        SuService.Client client = new SuService.Client(protocol);
        // 打开socket
        transport.open();
        SuStruct result = client.simpleCalculate(1, 1, OperateType.MINUS);
        System.out.println(result.toString());
        transport.close();
    }
}
