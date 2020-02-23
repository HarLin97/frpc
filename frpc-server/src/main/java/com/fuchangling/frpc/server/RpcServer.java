package com.fuchangling.frpc.server;

import com.fuchangling.frpc.proto.Request;
import com.fuchangling.frpc.proto.Response;
import com.fuchangling.frpc.proto.codec.Decoder;
import com.fuchangling.frpc.proto.codec.Encoder;
import com.fuchangling.frpc.proto.common.ReflectionUtils;
import com.fuchangling.frpc.transport.RequestHandler;
import com.fuchangling.frpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author harlin
 */
@Slf4j
public class RpcServer {

    private RpcServerConfig config;

    private TransportServer net;

    private Encoder encoder;

    private Decoder decoder;

    private ServiceManager serviceManager;

    private ServiceInvoker serviceInvoker;

    public RpcServer(RpcServerConfig config) {

        this.config = config;

        this.net = ReflectionUtils.newInstance(config.getTransportClass());

        this.net.init(config.getPort(), this.handler);

        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());

        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());

        this.serviceManager = new ServiceManager();

        this.serviceInvoker = new ServiceInvoker();
    }

    public <T> void register(Class<T> interfaceClass, Object bean) {
        serviceManager.register(interfaceClass, bean);
    }

    public void start() {
        this.net.start();
        ;
    }

    public void stop() {
        this.net.stop();
    }

    private RequestHandler handler = (receive, os) -> {
        Response resp = new Response();
        try {
            byte[] bytes = IOUtils.readFully(receive, receive.available());
            Request request = decoder.decoder(bytes, Request.class);
            log.info("get request: {}", request);

            ServiceInstance instance = serviceManager.lookup(request);
            Object ret = serviceInvoker.invoker(instance, request);
            resp.setData(ret);

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            resp.setCode(1);
            resp.setMsg("RpcServer got error: " + e.getClass().getName() + " " + e.getMessage());
        } finally {
            byte[] bytes = encoder.encode(resp);
            try {
                os.write(bytes);
                log.info("response client");
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
            }
        }
    };

//    private RequestHandler handler = new RequestHandler() {
//        @Override
//        public void onRequest(InputStream recive, OutputStream toResp) {
//            Response resp = new Response();
//            try {
//                byte[] inBytes = IOUtils.readFully(recive, recive.available());
//                Request request = decoder.decoder(inBytes, Request.class);
//
//                log.info("get request: {}", request);
//                ServiceInstance sis = serviceManager.lookup(request);
//                Object ret = serviceInvoker.invoker(sis, request);
//                resp.setData(ret);
//            } catch (IOException e) {
//                log.warn(e.getMessage(), e);
//                resp.setCode(1);
//                resp.setMsg("RcpServer got error" + e.getClass().getName()
//                        + ":" + e.getMessage());
//            } finally {
//                try {
//                    byte[] outBytes = encoder.encode(resp);
//                    toResp.write(outBytes);
//                    log.info("response client");
//                } catch (IOException e) {
//                    log.warn(e.getMessage(), e);
//                }
//            }
//
//        }
//
//    };
//}
}
