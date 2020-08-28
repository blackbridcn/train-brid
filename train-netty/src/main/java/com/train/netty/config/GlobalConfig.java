package com.train.netty.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Author: yuzzha
 * Date: 2020-08-13 14:03
 * Description: ${DESCRIPTION}
 */
@Data
@Component
@ConfigurationProperties(prefix = "config")
public class GlobalConfig {

    @ApiModelProperty("连接数")
    private int cunt;

    @ApiModelProperty("端口号")
    private int tport;


}
