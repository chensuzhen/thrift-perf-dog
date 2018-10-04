# thrift-perf-dog

## perf-thrift

Defination for test thrift. 

## perf-server 

Thrift Server for performance test. 

Current Three Mode: 

- HsHa Server 
- Threadpool Server
- ThreadedSelect Server  

## perf-client

Thrift client for performance test. 

Current only support Sync client. 

## TODO List

- [ ] Read Thrift Server Source Code for different mode. 
- [ ] Implement async client. 
- [ ] Implement Performance test logic, with java dynamic proxy. 
- [ ] Connect Mysql with mybatis, and test. 
- [ ] Integrate Spring and ZK for Thrift Cluster. 
