package com.example.Login.service;

import com.example.Login.dtos.request.PostDTO;
import com.example.Login.entity.Post;
import com.example.Login.entity.User;
import com.example.Login.repository.CommentRepository;
import com.example.Login.repository.PostRepository;
import com.example.Login.repository.UserRepository;
import com.example.Login.exception.AppException;
import com.example.Login.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;

    public Post createPost(PostDTO postDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            if (authentication == null || !authentication.isAuthenticated()) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (optionalUser.isEmpty()) {
                throw new AppException(ErrorCode.USER_NOT_EXISTED);
            }
        User user = optionalUser.get();
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setCreateAt(new Timestamp(System.currentTimeMillis()));
        post.setCreateBy(user);
        return postRepository.save(post);
    }catch (Exception ex) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public Post getPostId(String id){
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }
    public Post updatePost(String postId, PostDTO postDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            if (authentication == null || !authentication.isAuthenticated()) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }

            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (optionalUser.isEmpty()) {
                throw new AppException(ErrorCode.USER_NOT_EXISTED);
            }

            Post post = getPostId(postId);
            User user = optionalUser.get();
            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());
            post.setCreateAt(post.getCreateAt());
            post.setUpdateAt(new Timestamp(System.currentTimeMillis()));
            post.setCreateBy(user);
            return postRepository.save(post);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    public void deletePost(String id){
        postRepository.deleteById(id);
    }
    }

