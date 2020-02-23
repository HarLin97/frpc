package com.fuchangling.frpc.proto.codec;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class JSONEncoderTest {

    @Test
    public void encode() {
        Encoder encoder = new JSONEncoder();
        TestBean bean = new TestBean();
        bean.setAge(1);
        bean.setName("admin");

        byte[] bytes = encoder.encode(bean);

        assertNotNull(bytes);
    }
}
