package com.example.githubdemo.controller;

import com.example.githubdemo.POJO.AccessTokenDTO;
import com.example.githubdemo.POJO.GithubUser;
import com.example.githubdemo.Service.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/*
 *@author yzhch
 *@data 2020/5/28 18:31
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(clientSecret);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);

        System.out.println("user.getId()：" + user.getId());


        if(user != null){
            //登录成功 写入cookie和session
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else{
            //登录失败  重新登录
            return "redirect:/";
        }
    }

   
}

