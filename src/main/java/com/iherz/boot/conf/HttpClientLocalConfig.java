package com.iherz.boot.conf;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class HttpClientLocalConfig {

    private HttpComponentsClientHttpRequestFactory factory;

    @Bean
    public HttpComponentsClientHttpRequestFactory getLocalConfig() {
        HttpClientBuilder builder = HttpClientBuilder.create();
        factory = new HttpComponentsClientHttpRequestFactory();
        HttpHost proxy = new HttpHost("10.226.14.1", 8080);
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        builder.setDefaultRequestConfig(config);
        CredentialsProvider cp = new BasicCredentialsProvider();
        cp.setCredentials(new AuthScope(null, -1, null),
                new UsernamePasswordCredentials("bad",
                        "asdf"));
        builder.setDefaultCredentialsProvider(cp);
        factory.setHttpClient(builder.build());
        return factory;
    }
}
