package com.fuchangling.frpc.proto.codec;

/**
 * 反序列化
 *
 * @author harlin
 */
public interface Decoder {

    <T> T decoder(byte[] bytes, Class<T> clazz);
}
