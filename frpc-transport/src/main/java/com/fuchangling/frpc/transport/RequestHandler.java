package com.fuchangling.frpc.transport;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 处理网络请求handler
 *
 * @author harlin
 */
public interface RequestHandler {
    void onRequest(InputStream recive, OutputStream toResp);
}
