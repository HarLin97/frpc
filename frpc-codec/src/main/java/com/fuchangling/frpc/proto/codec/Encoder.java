package com.fuchangling.frpc.proto.codec;


/**
 * 序列化
 *
 * @author harlin
 */
public interface Encoder {

    byte[] encode(Object obj);

}
