package com.example.Login.Controller;

import com.example.Login.Dto.Request.ApiResponse;
import com.example.Login.Dto.Request.PostDTO;
import com.example.Login.Entity.Comment;
import com.example.Login.Entity.Post;
import com.example.Login.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            response.setMessage("Post not found");
        } else {
            response.setResult(post);
        }
        return response;
    }
    @GetMapping
    public ApiResponse<List<Post>> getAllPosts(){
        List<Post> posts = postService.getAllPosts();
        ApiResponse<List<Post>> response = new ApiResponse<>();
        response.setResult(posts);
        response.setMessage("Posts retrieved all successfully");
        return response;
    }


    @GetMapping("/{post_id}")
    public ApiResponse<Post> getPostId(@PathVariable("post_id") String post_id){
        Post post = postService.getPostId(post_id);
        ApiResponse<Post> response = new ApiResponse<>();
        response.setResult(post);
        response.setMessage("Post retrieved successfully");
        return response;
    }

    @PutMapping("/{post_id}")
    public ApiResponse<Post> updatePost(@PathVariable String post_id, @RequestBody PostDTO request){
        Post post = postService.updatePost(post_id, request);
        ApiResponse<Post> response = new ApiResponse<>();
        response.setResult(post);
        response.setMessage("Post update successfully");
        return response;
    }
    @DeleteMapping("/{post_id}")
    String deletePost(@PathVariable String post_id){
        postService.deletePost(post_id);
        return "Post has been deleted";
    }
}