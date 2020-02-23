package com.fuchangling.frpc.client;

import com.fuchangling.frpc.proto.Peer;
import com.fuchangling.frpc.proto.codec.Decoder;
import com.fuchangling.frpc.proto.codec.Encoder;
import com.fuchangling.frpc.proto.codec.JSONDecoder;
import com.fuchangling.frpc.proto.codec.JSONEncoder;
import com.fuchangling.frpc.transport.HttpTransportClient;
import com.fuchangling.frpc.transport.TransportClient;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @author harlin
 */
@Data
public class RpcClientConfig {
    private Class<? extends TransportClient> transportClientClass = HttpTransportClient.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;
    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;
    private int connectCount = 1;
    private List<Peer> servers = Collections.singletonList(
            new Peer("127.0.0.1", 6888)
    );
}
