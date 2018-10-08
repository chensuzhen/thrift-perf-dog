package com.su.perf.client.pool;

import com.su.perf.client.endpoint.Endpoint;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class ThriftClientPoolFactory
        extends BaseKeyedPooledObjectFactory<Endpoint, ThriftClientObject> {

    @Override
    public ThriftClientObject create(Endpoint key) throws Exception {
        return ThriftClientObject.createClientObject(key);
    }

    @Override
    public PooledObject<ThriftClientObject> wrap(ThriftClientObject value) {
        return new DefaultPooledObject<>(value);
    }

    @Override
    public void destroyObject(final Endpoint endpoint, final PooledObject<ThriftClientObject> p)
            throws Exception {
        ThriftClientObject.destoryClientObject(p.getObject());
    }

    @Override
    public boolean validateObject(final Endpoint endpoint, final PooledObject<ThriftClientObject> p) {
        return ThriftClientObject.validClientObject(p.getObject());
    }
}
