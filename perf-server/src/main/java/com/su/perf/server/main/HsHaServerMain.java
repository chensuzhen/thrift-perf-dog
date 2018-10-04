package com.su.perf.server.main;

import com.su.perf.server.application.SuServiceImpl;
import com.su.thrift.SuService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 与TNonblockingServer模式相比，THsHaServer在完成数据读取之后，
 * 将业务处理过程交由一个线程池来完成，主线程直接返回进行下一次循环操作，
 * 效率大大提升；
 */
public class HsHaServerMain {
    private static final Logger logger = LoggerFactory.getLogger(HsHaServerMain.class);
    public static void startHaHsServer(int port) {
        try {

            TNonblockingServerSocket nonblockingServerSocket = new TNonblockingServerSocket(port);
            final SuService.Processor processor = new SuService.Processor(new SuServiceImpl());
            THsHaServer.Args arg = new THsHaServer.Args(nonblockingServerSocket);
            // 高效率的、密集的二进制编码格式进行数据传输              
            // 使用非阻塞方式，按块的大小进行传输，类似于 Java 中的 NIO             
            arg.protocolFactory(new TBinaryProtocol.Factory());
            arg.transportFactory(new TFramedTransport.Factory());
            arg.processorFactory(new TProcessorFactory(processor));
            TServer server = new THsHaServer(arg);

            System.out.println("Starting the Thrift Half-Sync/Half-Async server ...");
            server.serve();
        } catch (Exception e) {
            logger.error("Error in start Thrift Half-Sync/Half-Async server", e);
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        startHaHsServer(9001);
    }
}
