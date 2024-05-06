package com.example.Login.dtos.request.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostResponse {
    private String id;
    private String title;
    private String content;
    private Date createAt;
    private String createBy;
    private List<CommentResponse> comments;
}
