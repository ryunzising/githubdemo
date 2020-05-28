package com.example.githubdemo.POJO;

import lombok.Data;

/*
 *@author yzhch
 *@data 2020/5/28 17:24
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
