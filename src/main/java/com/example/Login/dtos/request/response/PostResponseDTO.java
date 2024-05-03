package com.example.Login.dtos.request.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostResponseDTO {
    private String id;
    private String title;
    private String content;
    private Date createAt;
    private String createBy;
    private List<CommentResponseDTO> comments;
}
