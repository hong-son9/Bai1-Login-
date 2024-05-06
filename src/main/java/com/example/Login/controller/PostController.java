package com.example.Login.controller;

import com.example.Login.dtos.request.PostRequest;
import com.example.Login.dtos.request.response.PostResponse;
import com.example.Login.dtos.request.response.PostUpdateResponse;
import com.example.Login.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public PostResponse createPost(@RequestBody PostRequest postDTO) {
        return postService.createPost(postDTO);
    }

    @GetMapping
    public List<PostUpdateResponse> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/{post_id}")
    public PostUpdateResponse getPostTitle(@PathVariable("post_id") String post_id){
        return postService.getPostId(post_id);

    }

    @PutMapping("/{title}")
    public PostUpdateResponse updatePost(@PathVariable String title, @RequestBody PostRequest request){
        return postService.updatePost(title, request);

    }
    @DeleteMapping("/{post_id}")
    public String deletePost(@PathVariable String post_id){
        postService.deletePost(post_id);
        return "Post has been deleted";
    }
}