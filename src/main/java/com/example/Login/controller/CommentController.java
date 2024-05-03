package com.example.Login.controller;

import com.example.Login.dtos.request.ApiResponse;
import com.example.Login.dtos.request.CommentDTO;
import com.example.Login.dtos.request.response.CommentResponseDTO;
import com.example.Login.dtos.request.response.CommentUpdateResponseDTO;
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
    public CommentResponseDTO createComment(@PathVariable String post_id, @RequestBody CommentDTO commentDTO) {
        return commentService.createComment(post_id, commentDTO);
    }
    @PutMapping("/{commentId}")
    public CommentUpdateResponseDTO updateComment(@PathVariable String commentId,
                                                  @RequestBody CommentDTO commentDTO) {
        return commentService.updateComment(commentId, commentDTO);
    }
    @GetMapping("/{commentId}")
    public CommentUpdateResponseDTO getCommentById(@PathVariable String commentId) {
        return commentService.getCommentById(commentId);

    }

    @DeleteMapping("/{commentId}")
    String deleteComment(@PathVariable String commentId){
        commentService.deleteComment(commentId);
        return "Comment has been deleted";
    }
@GetMapping
public List<CommentUpdateResponseDTO> getAllComments(){
    return commentService.getAllComments();

}
}
