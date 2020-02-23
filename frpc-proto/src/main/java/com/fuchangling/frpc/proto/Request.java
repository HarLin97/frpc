package com.fuchangling.frpc.proto;

import lombok.Data;

/**
 * 标识RPC的一个请求
 *
 * @author harlin
 */
@Data
public class Request {

    private ServiceDescriptor service;

    private Object[] parameters;
}
