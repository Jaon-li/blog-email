package com.blog.blogemail.service.impl;

import com.blog.base.emailApi.req.EmailContentEntity;
import com.blog.blogemail.service.BaseService;
import com.blog.blogemail.service.EmailService;
import com.blog.blogemail.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl extends BaseService implements EmailService {

    @Autowired
    private EmailUtils emailUtils;

    @Override
    public boolean send(EmailContentEntity emailContentEntity) {
        return emailUtils.sendEmail(emailContentEntity);
    }

    @Override
    public boolean cancel(Integer id) {
        return false;
    }

    @Override
    public boolean query(Integer id) {
        return false;
    }
}
