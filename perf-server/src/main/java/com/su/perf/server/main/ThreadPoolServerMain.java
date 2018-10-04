package com.su.perf.server.main;

import com.su.perf.server.application.SuServiceImpl;
import com.su.thrift.SuService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ThreadPoolServerMain {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolServerMain.class);
    public static void startThreadPoolServer(int port) {
        try {
            final SuService.Processor processor = new SuService.Processor(new SuServiceImpl());
            TServerTransport serverTransport = new TServerSocket(port);
            TThreadPoolServer.Args arg = new TThreadPoolServer.Args(serverTransport);
            arg.protocolFactory(new TBinaryProtocol.Factory());
            arg.transportFactory(new TFramedTransport.Factory());
            arg.processorFactory(new TProcessorFactory(processor));
            TServer server = new TThreadPoolServer(arg);

            System.out.println("Starting the Thrift ThreadPool server ...");
            server.serve();
        } catch (Exception e) {
            logger.error("Error in start Thrift ThreadPool server", e);
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        startThreadPoolServer(9002);
    }
}
