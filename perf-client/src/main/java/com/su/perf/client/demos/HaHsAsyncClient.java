package com.su.perf.client.demos;

import com.su.thrift.OperateType;
import com.su.thrift.SuService;
import com.su.thrift.SuStruct;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class HaHsAsyncClient {
    private static final Logger logger = LoggerFactory.getLogger(HaHsAsyncClient.class);
    public static void main(String[] args)
            throws IOException, TException, InterruptedException {
        TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
        TAsyncClientManager clientManager = new TAsyncClientManager();
        TNonblockingSocket transport = new TNonblockingSocket("localhost", 9001);
        SuService.AsyncClient asyncClient = new SuService.AsyncClient(protocolFactory, clientManager, transport);

        CountDownLatch countDownLatch = new CountDownLatch(2);
        long left = 1L;
        long right = 2L;

        asyncClient.simpleCalculate(left, right, OperateType.MINUS,
                new HaHsAsyncResultHandler(left, right, OperateType.MINUS, countDownLatch));

        asyncClient.simpleCalculate(left, right, OperateType.SUM,
                new HaHsAsyncResultHandler(left, right, OperateType.SUM, countDownLatch));

        countDownLatch.await(2000, TimeUnit.MILLISECONDS);
        transport.close();
    }

    public static class HaHsAsyncResultHandler implements AsyncMethodCallback<SuStruct> {

        private CountDownLatch countDownLatch;
        private long leftOperator;
        private long rightOperator;
        private OperateType operateType;

        public HaHsAsyncResultHandler(long leftOperator,
                                      long rightOperator,
                                      OperateType operateType,
                                      CountDownLatch countDownLatch) {
            this.leftOperator = leftOperator;
            this.rightOperator = rightOperator;
            this.operateType = operateType;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void onComplete(SuStruct response) {
            System.out.println(response);
            this.countDownLatch.countDown();
        }

        @Override
        public void onError(Exception exception) {
            logger.error("Error in call simple calculate with left operator {} {} with right operator {} .",
                    this.leftOperator, this.rightOperator, this.operateType, exception);
            this.countDownLatch.countDown();
        }
    }
}
