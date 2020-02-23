package com.fuchangling.frpc.client;

import com.fuchangling.frpc.proto.codec.Decoder;
import com.fuchangling.frpc.proto.codec.Encoder;
import com.fuchangling.frpc.proto.common.ReflectionUtils;

import java.lang.reflect.Proxy;

/**
 * @author harlin
 */
public class RpcClient {

    private RpcClientConfig config;

    private Encoder encoder;

    private Decoder decoder;

    private TransportSelector selector;

    public RpcClient() {
        this(new RpcClientConfig());
    }

    public RpcClient(RpcClientConfig config) {
        this.config = config;
        this.encoder = ReflectionUtils.newInstance(this.config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(this.config.getDecoderClass());
        this.selector = ReflectionUtils.newInstance(this.config.getSelectorClass());

        this.selector.init(
                this.config.getServers(),
                this.config.getConnectCount(),
                this.config.getTransportClientClass()
        );
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{clazz}, new RemoteInvoker(clazz, encoder, decoder, selector));
    }
}
