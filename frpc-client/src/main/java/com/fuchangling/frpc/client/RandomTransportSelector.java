package com.fuchangling.frpc.client;

import com.fuchangling.frpc.proto.Peer;
import com.fuchangling.frpc.proto.common.ReflectionUtils;
import com.fuchangling.frpc.transport.TransportClient;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author harlin
 */
@Slf4j
public class RandomTransportSelector implements TransportSelector {
    /**
     * 已经连接好的client
     */
    private List<TransportClient> clients;

    public RandomTransportSelector() {
        clients = new ArrayList<>();
    }

    @Synchronized
    @Override
    public void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {

        count = Math.max(count, 1);
        for (Peer peer : peers) {
            for (int i = 0; i < count; i++) {
                TransportClient client = ReflectionUtils.newInstance(clazz);
                client.connect(peer);
                clients.add(client);
            }
            log.info("connect server:{}", peer);
        }

    }

    @Synchronized
    @Override
    public TransportClient select() {
        int i = new Random().nextInt(clients.size());
        return clients.remove(i);
    }

    @Override
    public void release(TransportClient client) {
        clients.add(client);
    }

    @Synchronized
    @Override
    public void close() {
        for (TransportClient client : clients) {
            client.close();
        }
        clients.clear();
    }
}
