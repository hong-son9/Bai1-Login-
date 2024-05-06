package com.example.Login.dtos.request.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostUpdateResponse {
    private String id;
    private String title;
    private String content;
    private Date createAt;
    private Date updateAt;
    private String createBy;
    private List<CommentResponse> comments;
}
