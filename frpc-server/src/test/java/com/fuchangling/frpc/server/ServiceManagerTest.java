package com.fuchangling.frpc.server;

import com.fuchangling.frpc.proto.Request;
import com.fuchangling.frpc.proto.ServiceDescriptor;
import com.fuchangling.frpc.proto.common.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertNotNull;

public class ServiceManagerTest {

    ServiceManager sm;

    @Before
    public void init() {
        sm = new ServiceManager();
        TestInterface bean = new TestClass();
        sm.register(TestInterface.class, bean);
    }

    @Test
    public void register() {
        TestInterface bean = new TestClass();
        sm.register(TestInterface.class, bean);
    }

    @Test
    public void lookup() {
        Method methods = ReflectionUtils.getPublicMethods(TestInterface.class)[0];

        ServiceDescriptor sdp = ServiceDescriptor.from(TestInterface.class, methods);
        Request request = new Request();
        request.setService(sdp);

        ServiceInstance sis = sm.lookup(request);
        assertNotNull(sis);

    }
}
