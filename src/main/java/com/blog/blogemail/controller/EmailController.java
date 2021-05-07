package com.blog.blogemail.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.blog.base.emailApi.api.EmailApi;
import com.blog.base.emailApi.req.EmailContentEntity;
import com.blog.base.response.BaseResponse;
import com.blog.blogemail.service.EmailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("邮件相关服务")
@RestController
@RequestMapping("/api/email")
public class EmailController implements EmailApi {

    @Autowired
    private EmailService emailService;


    @SentinelResource("send")
    @Override
    public BaseResponse<Boolean> send(EmailContentEntity emailContentEntity) {
        boolean send = emailService.send(emailContentEntity);
        return BaseResponse.ok(send);
    }

    @SentinelResource("cancel")
    @Override
    public BaseResponse<Boolean> cancel(Integer id) {
        return null;
    }

    @SentinelResource("query")
    @Override
    public BaseResponse<Boolean> query(Integer id) {
        return null;
    }
}
