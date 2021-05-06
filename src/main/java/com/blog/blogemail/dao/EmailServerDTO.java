package com.blog.blogemail.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("email_server")
public class EmailServerDTO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String password;

    private String host;

    private String agreement;

    private Integer port;

    private Boolean isDeleted;

    private Date ctime;

    private Date mtime;

}