package com.example.Login.controller;

import com.example.Login.dtos.request.ApiResponse;
import com.example.Login.dtos.request.CommentDTO;
import com.example.Login.entity.Comment;
import com.example.Login.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentService commentService;
    @PostMapping("/{post_id}")
    public ApiResponse<Comment> createComment(@PathVariable String post_id, @RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.createComment(post_id, commentDTO);
        ApiResponse<Comment> response = new ApiResponse<>();
        if (comment == null) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Comment not found");
        } else {
            response.setCode(HttpStatus.CREATED.value());
            response.setMessage("Comment created successfully");
            response.setResult(comment);
        }
        return response;
    }
    @PutMapping("/{commentId}")
    public ApiResponse<Comment> updateComment(@PathVariable String commentId,
                                                              @RequestBody CommentDTO commentDTO) {
        Comment updatedComment = commentService.updateComment(commentId, commentDTO);
        ApiResponse<Comment> response = new ApiResponse<>();
        response.setResult(updatedComment);
        response.setMessage("Comment updated successfully");
        return response;
    }
    @GetMapping("/{commentId}")
    public ApiResponse<Comment> getCommentById(@PathVariable String commentId) {
        Comment comment = commentService.getCommentById(commentId);

        ApiResponse<Comment> response = new ApiResponse<>();
        response.setResult(comment);
        response.setMessage("Comment retrieved successfully");
        return response;
    }

    @DeleteMapping("/{commentId}")
    String deleteComment(@PathVariable String commentId){
        commentService.deleteComment(commentId);
        return "Comment has been deleted";
    }
@GetMapping
public ApiResponse<List<Comment>> getAllComments(){
    List<Comment> comments = commentService.getAllComments();
    ApiResponse<List<Comment>> response = new ApiResponse<>();
    response.setResult(comments);
    response.setMessage("Comments retrieved all successfully");
    return response;
}
}
