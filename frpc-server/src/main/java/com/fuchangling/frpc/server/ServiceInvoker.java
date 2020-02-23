package com.fuchangling.frpc.server;

import com.fuchangling.frpc.proto.Request;
import com.fuchangling.frpc.proto.common.ReflectionUtils;

/**
 * 调用具体服务
 *
 * @author harlin
 */
public class ServiceInvoker {

    public Object invoker(ServiceInstance service, Request request) {
        return ReflectionUtils.invoke(service.getTarget(), service.getMethod(),  request.getParameters());
    }
}
