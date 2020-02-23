package com.fuchangling.frpc.transport;

import com.fuchangling.frpc.proto.Peer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author harlin
 */
public class HttpTransportClient implements TransportClient {

    private String url;

    @Override
    public void connect(Peer peer) {
        this.url = "http://" + peer.getHost() + ":" + peer.getPort();
    }

    @Override
    public InputStream write(InputStream data) {
        try {
            HttpURLConnection httpURLCon = (HttpURLConnection) new URL(url).openConnection();
            httpURLCon.setDoOutput(true);
            httpURLCon.setDoInput(true);
            httpURLCon.setUseCaches(false);
            httpURLCon.setRequestMethod("POST");

            httpURLCon.connect();

            IOUtils.copy(data, httpURLCon.getOutputStream());

            int resultCode = httpURLCon.getResponseCode();
            if (resultCode == HttpURLConnection.HTTP_OK) {
                return httpURLCon.getInputStream();
            } else {
                return httpURLCon.getErrorStream();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }
}
