package com.su.perf.server.main;

import com.su.perf.server.application.SuServiceImpl;
import com.su.thrift.SuService;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class ThriftMain {
    public static void main(String[] args) {
        try {

            SuService.Processor processor = new SuService.Processor(new SuServiceImpl());
            TServerTransport serverTransport = new TServerSocket(9090);
//            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));

//             Use this for a multithreaded server
             TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the simple server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
