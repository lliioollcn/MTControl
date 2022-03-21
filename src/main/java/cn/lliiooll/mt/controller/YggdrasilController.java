package cn.lliiooll.mt.controller;

import cn.lliiooll.mt.beans.yggdrasil.BaseYggBean;
import cn.lliiooll.mt.beans.yggdrasil.YggdrasilHomeBean;
import cn.lliiooll.mt.utils.YggdrasilUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RequestMapping(value = "/yggdrasil", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class YggdrasilController {

    @PostMapping("/authserver/authenticate")
    public BaseYggBean authenticate() {
        return new BaseYggBean();
    }

    @RequestMapping("/")
    public YggdrasilHomeBean home() {
        return YggdrasilHomeBean.builder()
                .meta(YggdrasilHomeBean.HomeMetadataBean.builder()
                        .implementationName("MTControl")
                        .implementationVersion("v1.0")
                        .links(YggdrasilHomeBean.HomeMetadataBean.HomeMetadataLinksBean.builder()
                                .homepage("http://localhost:8080/")
                                .register("http://localhost:8080/register")
                                .build())
                        .serverName("MTControl Studio Server")
                        .build())
                .skinDomains(new ArrayList<String>() {{
                    add("http://localhost:8080/");
                }}.toArray(new String[0]))
                .signaturePublickey(YggdrasilUtils.publicKey)
                .build();
    }
}
