package com.su.perf.server.application;

import com.su.thrift.CatchableException;
import com.su.thrift.OperateType;
import com.su.thrift.SuService;
import com.su.thrift.SuStruct;
import org.apache.thrift.TException;

public class SuServiceImpl implements SuService.Iface {
    @Override
    public SuStruct simpleCalculate(long a, long b, OperateType op) throws TException {
        SuStruct result = new SuStruct();
        switch (op) {
            case SUM:
                result.setResult(a + b);
                result.setOperateType(op.name());
                break;
            case MINUS:
                result.setResult(a - b);
                result.setOperateType(op.name());
                break;
            default:
                throw new CatchableException(500, "Unsupported Operator type!");
        }

        return result;
    }
}
