package com.fuchangling.frpc.proto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 标识网络传输的一个端点
 *
 * @author harlin
 */
@Data
@AllArgsConstructor
public class Peer {

    private String host;

    private int port;
}
