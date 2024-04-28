package com.example.Login.Controller;

import com.example.Login.Dto.Request.ApiResponse;
import com.example.Login.Dto.Request.PostDTO;
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
            response.setMessage("User not found");
        } else {
            response.setResult(post);
        }
        return response;
    }
    @GetMapping
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/{post_id}")
    public Post getPostId(@PathVariable("post_id") String post_id){
        return postService.getPostId(post_id);
    }

    @PutMapping("/{post_id}")
    public Post updatePost(@PathVariable String post_id, @RequestBody PostDTO request){
        return postService.updatePost(post_id, request);
    }
    @DeleteMapping("/{post_id}")
    String deletePost(@PathVariable String post_id){
        postService.deletePost(post_id);
        return "Post has been deleted";
    }
}