namespace java com.su.thrift

exception CatchableException {
    1: i32 code;
    2: string msg;
}

enum OperateType{
    SUM,
    MINUS
}

struct SuStruct{
    1: i64 result;
    2: string operateType;
}

service SuService {
    SuStruct simpleCalculate(1:i64 a, 2:i64 b, 3:OperateType op) throws (CatchableException ce);
}
