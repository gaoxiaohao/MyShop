package com.gxh.shop.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gxh
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "secure.ignored")
public class UrlsConfig {

    private List<String> urls = new ArrayList<>();
}
