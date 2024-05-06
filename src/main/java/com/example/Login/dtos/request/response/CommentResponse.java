package com.example.Login.dtos.request.response;

import lombok.Data;

import java.util.Date;
@Data
public class CommentResponse {
    private String id;
    private String content;
    private Date createAt;
    private String createBy;

}
