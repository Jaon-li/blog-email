package com.blog.blogemail.controller;

import com.blog.base.emailApi.api.EmailApi;
import com.blog.base.emailApi.req.EmailContentEntity;
import com.blog.base.response.BaseResponse;
import com.blog.blogemail.service.EmailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;

@Api("邮件相关服务")
public class EmailController implements EmailApi {

    @Autowired
    private EmailService emailService;


    @Override
    public BaseResponse<Boolean> send(EmailContentEntity emailContentEntity) {
        boolean send = emailService.send(emailContentEntity);
        return BaseResponse.ok(send);
    }

    @Override
    public BaseResponse<Boolean> cancel(Integer id) {
        return null;
    }

    @Override
    public BaseResponse<Boolean> query(Integer id) {
        return null;
    }
}
