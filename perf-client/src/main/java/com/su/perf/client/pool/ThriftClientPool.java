package com.su.perf.client.pool;

import com.su.perf.client.endpoint.Endpoint;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftClientPool{

    private static final Logger logger = LoggerFactory.getLogger(ThriftClientPool.class);

    private static final int DEFAULT_MAX_IDLE = 20;
    private static final int DEFAULT_MAX_TOTAL = 40;

    private GenericKeyedObjectPool<Endpoint, ThriftClientObject> pool;
    private ThriftClientPoolFactory thriftClientPoolFactory;

    private static ThriftClientPool instance = new ThriftClientPool();

    public static ThriftClientPool getInstance() {
        return instance;
    }

    private ThriftClientPool() {
        this.thriftClientPoolFactory = new ThriftClientPoolFactory();
        this.pool = new GenericKeyedObjectPool<>(thriftClientPoolFactory);
        this.pool.setMaxIdlePerKey(DEFAULT_MAX_IDLE);
        this.pool.setMaxTotalPerKey(DEFAULT_MAX_TOTAL);
        this.pool.setTestOnBorrow(true);
    }

    public ThriftClientObject borrowClient(Endpoint endpoint) throws Exception {
        logger.info("[borrow client!] current active num" + this.pool.getNumActive(endpoint));
        logger.info("[borrow client!] current idle num" + this.pool.getNumIdle(endpoint));
        logger.info("[borrow client!] current total waiter num" + this.pool.getNumWaiters());
        return this.pool.borrowObject(endpoint);
    }

    public void returnClient(Endpoint endpoint, ThriftClientObject thriftClientObject) {
        this.pool.returnObject(endpoint, thriftClientObject);
        logger.info("return client! current idle num" + this.pool.getNumActive(endpoint));
    }

    public void close() {
        this.pool.close();
    }
}
