# custom-rpc
采用 java 实现的简易版的 rpc 框架

# 使用方法

## v1.0版本 

> 服务提供者

```
  ServiceManager.exportService(8866);// 8866 是端口号
```
> 服务消费者

```
  // 封装远程调用对象
  RpcData rpcData = new RpcData();
  rpcData.setHost("localhost");// 服务调用地址
  rpcData.setPort(8866);// 服务端口号
  rpcData.setImplClassQualifyName("com.nimo.proxy.HelloServiceImpl");// 服务实现类
  rpcData.setCls(HelloService.class);// 服务接口
  rpcData.setArgs(new String[]{"zhangsan"});// 参数
  rpcData.setParameterType(new Class[]{String.class});// 参数类型
  rpcData.setMethodName("sayHello");// 方法名
  
  ServiceManager.referService(rpcData);// 引用服务

```


