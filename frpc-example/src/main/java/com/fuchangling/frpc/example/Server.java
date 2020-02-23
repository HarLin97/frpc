package com.fuchangling.frpc.example;

import com.fuchangling.frpc.server.RpcServer;
import com.fuchangling.frpc.server.RpcServerConfig;

/**
 * @author harlin
 */
public class Server {

    public static void main(String[] args) {
        RpcServer server = new RpcServer(new RpcServerConfig());
        server.register(CalcService.class, new CalcServiceImpl());
        server.start();
    }
}
