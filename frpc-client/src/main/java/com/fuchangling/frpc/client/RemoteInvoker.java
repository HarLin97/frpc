package com.fuchangling.frpc.client;

import com.fuchangling.frpc.proto.Request;
import com.fuchangling.frpc.proto.Response;
import com.fuchangling.frpc.proto.ServiceDescriptor;
import com.fuchangling.frpc.proto.codec.Decoder;
import com.fuchangling.frpc.proto.codec.Encoder;
import com.fuchangling.frpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 调用远程服务的代理类
 *
 * @author harlin
 */
@Slf4j
public class RemoteInvoker implements InvocationHandler {

    private Class clazz;

    private Encoder encoder;

    private Decoder decoder;

    private TransportSelector selector;

    RemoteInvoker(Class clazz, Encoder encoder, Decoder decoder, TransportSelector selector) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
        this.selector = selector;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) {

        Request request = new Request();
        request.setService(ServiceDescriptor.from(clazz, method));
        request.setParameters(objects);

        Response resp = invokeRemote(request);
        if (resp == null || resp.getCode() != 0) {
            throw new IllegalStateException("fail to invoke remote:" + resp);
        }
        return resp.getData();
    }

    private Response invokeRemote(Request request) {
        Response resp = new Response();
        TransportClient client = null;
        try {
            client = selector.select();
            byte[] outBytes = encoder.encode(request);
            InputStream revice = client.write(new ByteArrayInputStream(outBytes));
            byte[] inBytes = IOUtils.readFully(revice, revice.available());
            resp = this.decoder.decoder(inBytes, Response.class);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            resp.setCode(1);
            resp.setMsg("RpcClient got error:" + e.getClass() + ":" + e.getMessage());
        } finally {
            if (selector != null) {
                selector.release(client);
            }
        }
        return resp;
    }
}
