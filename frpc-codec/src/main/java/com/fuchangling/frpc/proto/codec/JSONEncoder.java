package com.fuchangling.frpc.proto.codec;

import com.alibaba.fastjson.JSON;

/**
 * 基于json的序列化实现
 *
 * @author harlin
 */
public class JSONEncoder implements Encoder {

    @Override
    public byte[] encode(Object obj) {
        return JSON.toJSONBytes(obj);
    }
}
