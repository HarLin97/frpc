package com.fuchangling.frpc.server;

import com.fuchangling.frpc.proto.codec.Decoder;
import com.fuchangling.frpc.proto.codec.Encoder;
import com.fuchangling.frpc.proto.codec.JSONDecoder;
import com.fuchangling.frpc.proto.codec.JSONEncoder;
import com.fuchangling.frpc.transport.HttpTransportServer;
import com.fuchangling.frpc.transport.TransportServer;
import lombok.Data;

/**
 * server配置
 *
 * @author harlin
 */
@Data
public class RpcServerConfig {

    private Class<? extends TransportServer> transportClass = HttpTransportServer.class;

    private Class<? extends Encoder> encoderClass = JSONEncoder.class;

    private Class<? extends Decoder> decoderClass = JSONDecoder.class;

    private int port = 6888;

}
