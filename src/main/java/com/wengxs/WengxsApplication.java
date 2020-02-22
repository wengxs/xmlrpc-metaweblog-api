package com.wengxs;

import org.apache.xmlrpc.webserver.XmlRpcServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WengxsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WengxsApplication.class, args);
    }

    /**
     * 没有web.xml的情况下需要注册Servlet到容器
     * @return
     */
    @SuppressWarnings("unchecked")
    @Bean
    public ServletRegistrationBean registerServlet() {
        return new ServletRegistrationBean(
                new XmlRpcServlet(),
                "/xmlrpc" // xml-rpc访问接口
        );
    }

}
