package com.fuchangling.frpc.transport;

/**
 * 1.启动、监听
 * 2.接受请求
 * 3.关闭监听
 *
 * @author harlin
 */
public interface TransportServer {

    void init(int port, RequestHandler handler);

    void start();


    void stop();
}
