package com.blog.blogemail.service;

import com.blog.base.emailApi.req.EmailContentEntity;

public interface EmailService {

    boolean send(EmailContentEntity emailContentEntity);

    boolean cancel(Integer id);

    boolean query(Integer id);

}
