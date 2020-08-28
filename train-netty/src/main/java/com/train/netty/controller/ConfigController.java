package com.train.netty.controller;



import com.train.netty.config.GlobalConfig;
import com.train.netty.param.BaseParam;
import com.train.netty.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api("配置Api")
@RestController
@RequestMapping("config")
public class ConfigController {

    //@Autowired
    //ITimeService iTimeService;

    @Autowired
    GlobalConfig config;

    @ApiOperation("service接口测试")
    @RequestMapping(value = "/api/test", method = RequestMethod.POST)
    public ResponseVo<BaseParam> test(@RequestBody BaseParam param) {
        log.info("--------->  "+param.getCode());
        //iTimeService.bind(param.getCode());
        return ResponseVo.success(param);
    }


}
