//package com.mars.mars_test.configs;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
//
//@Configuration
//public class ElasticSearchConfiguration extends ElasticsearchConfiguration {
//    @Value("${spring.data.elasticsearch.uris}")
//    String esUris;
//
//    @Override
//    public ClientConfiguration clientConfiguration() {
//        return ClientConfiguration.builder()
//                .connectedTo(esUris)
//                .usingSsl()
//                .build();
//    }
//
//}
