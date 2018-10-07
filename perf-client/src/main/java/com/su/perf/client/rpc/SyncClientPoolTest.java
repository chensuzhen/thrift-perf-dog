package com.su.perf.client.rpc;

import com.su.perf.client.pool.ThriftClientFactory;
import com.su.perf.client.pool.ThriftClientPool;
import com.su.thrift.OperateType;
import com.su.thrift.SuService;
import org.apache.thrift.TException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyncClientPoolTest {
    public static final SuService.Iface client = ThriftClientFactory
            .createClient(SuService.Iface.class, "localhost", 9001, 2000);
    public static void main(String[] args) throws TException, InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(10);

        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                try {
                    System.out.println(client.simpleCalculate(1, 2, OperateType.SUM));
                } catch (TException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        System.out.println("finally 100 test request.");

        ThriftClientPool.getInstance().close();
        System.exit(0);
    }
}
