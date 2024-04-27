package com.example.Login.Controller;

import com.example.Login.Dto.Request.ApiResponse;
import com.example.Login.Dto.Request.PostDTO;
import com.example.Login.Entity.Post;
import com.example.Login.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ApiResponse<Post> createPost(@RequestBody PostDTO postDTO) {
        Post post = postService.createPost(postDTO);
        ApiResponse<Post> response = new ApiResponse<>();
        if (post == null) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("User not found");
        } else {
            response.setResult(post);
        }
        return response;
    }
}