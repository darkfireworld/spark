package org.dfw.spark.core.template;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

public class EsTemplate {

    Client client;

    // 集群名字
    String esClusterName;
    // es host
    String esHost;
    // es port
    int esPort;

    public EsTemplate(String esClusterName, String esHost, int esPort) {
        this.esClusterName = esClusterName;
        this.esHost = esHost;
        this.esPort = esPort;
    }

    @PostConstruct
    public void init() throws Exception {
        Settings settings = Settings.builder()
                .put("client.transport.sniff", true)
                .put("cluster.name", esClusterName)
                .build();
        //noinspection unchecked
        client = TransportClient.builder()
                .settings(settings)
                .build()
                .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(esHost, esPort)));
    }

    public Client getClient() {
        return client;
    }

    @PreDestroy
    public void destroy() {
        if (client != null) {
            try {
                client.close();
            } catch (Exception e) {
                // NONE
            }
        }
    }
}
