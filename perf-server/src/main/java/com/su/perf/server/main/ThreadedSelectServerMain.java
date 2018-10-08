package com.su.perf.server.main;

import com.su.perf.server.application.SuServiceImpl;
import com.su.thrift.SuService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadedSelectServerMain {
    private static final Logger logger = LoggerFactory.getLogger(ThreadedSelectServerMain.class);
    public static void startThreadSelectServer(int port) {
        try {
            final SuService.Processor processor = new SuService.Processor(new SuServiceImpl());
            TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(port);
            TThreadedSelectorServer.Args arg = new TThreadedSelectorServer.Args(serverTransport);
            arg.protocolFactory(new TBinaryProtocol.Factory());
            arg.transportFactory(new TFramedTransport.Factory());
            arg.processorFactory(new TProcessorFactory(processor));
            TServer server = new TThreadedSelectorServer(arg);

            logger.warn("Starting the  Thrift ThreadedSelector server ...");
            server.serve();
        } catch (Exception e) {
            logger.error("Error in start Thrift ThreadedSelector server", e);
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        startThreadSelectServer(9003);
    }
}
