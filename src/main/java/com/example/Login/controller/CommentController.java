package com.example.Login.controller;

import com.example.Login.dtos.request.CommentRequest;
import com.example.Login.dtos.request.response.CommentResponse;
import com.example.Login.dtos.request.response.CommentUpdateResponse;
import com.example.Login.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentService commentService;
    @PostMapping("/{post_id}")
    public CommentResponse createComment(@PathVariable String post_id, @RequestBody CommentRequest commentDTO) {
        return commentService.createComment(post_id, commentDTO);
    }
    @PutMapping("/{commentId}")
    public CommentUpdateResponse updateComment(@PathVariable String commentId,
                                               @RequestBody CommentRequest commentDTO) {
        return commentService.updateComment(commentId, commentDTO);
    }
    @GetMapping("/{commentId}")
    public CommentUpdateResponse getCommentById(@PathVariable String commentId) {
        return commentService.getCommentById(commentId);

    }
    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable String commentId){
        commentService.deleteComment(commentId);
        return "Comment has been deleted";
    }
    @GetMapping
    public List<CommentUpdateResponse> getAllComments(){
    return commentService.getAllComments();
}
}
