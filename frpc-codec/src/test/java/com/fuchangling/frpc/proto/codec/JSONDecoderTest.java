package com.fuchangling.frpc.proto.codec;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JSONDecoderTest {

    @Test
    public void decoder() {

        Encoder encoder = new JSONEncoder();
        TestBean bean = new TestBean();
        bean.setAge(1);
        bean.setName("admin");

        byte[] bytes = encoder.encode(bean);

        assertNotNull(bytes);

        Decoder decoder =new JSONDecoder();
        TestBean bean1 = decoder.decoder(bytes,TestBean.class);
        assertEquals(bean.getAge(),bean1.getAge());
        assertEquals(bean.getName(),bean1.getName());
    }
}
