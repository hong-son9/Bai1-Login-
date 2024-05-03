package com.example.Login.controller;

import com.example.Login.dtos.request.ApiResponse;
import com.example.Login.dtos.request.PostRequestDTO;
import com.example.Login.dtos.request.response.PostResponseDTO;
import com.example.Login.dtos.request.response.PostUpdateResponseDTO;
import com.example.Login.entity.Post;
import com.example.Login.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public PostResponseDTO createPost(@RequestBody PostRequestDTO postDTO) {
        return postService.createPost(postDTO);
    }

    @GetMapping
    public List<PostUpdateResponseDTO> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/{post_id}")
    public PostUpdateResponseDTO getPostTitle(@PathVariable("post_id") String post_id){
        return postService.getPostId(post_id);

    }

    @PutMapping("/{title}")
    public PostUpdateResponseDTO updatePost(@PathVariable String title, @RequestBody PostRequestDTO request){
        return postService.updatePost(title, request);

    }
    @DeleteMapping("/{post_id}")
    String deletePost(@PathVariable String post_id){
        postService.deletePost(post_id);
        return "Post has been deleted";
    }
}