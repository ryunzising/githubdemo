package com.example.githubdemo.Service;

import com.alibaba.fastjson.JSON;
import com.example.githubdemo.POJO.AccessTokenDTO;
import com.example.githubdemo.POJO.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 *@author yzhch
 *@date 2020/5/28 17:25
 **/

@Service
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (
                Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);//access_token=c231d1e359bed82f6f98b2a2d86eb916478b56d1&scope=user&token_type=bearer
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try (
                Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            return JSON.parseObject(string, GithubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
