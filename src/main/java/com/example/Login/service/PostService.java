package com.example.Login.service;

import com.example.Login.dtos.request.PostRequestDTO;
import com.example.Login.dtos.request.response.PostResponseDTO;
import com.example.Login.dtos.request.response.PostUpdateResponseDTO;
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
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;

    public PostResponseDTO createPost(PostRequestDTO postDTO) {
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

        Post savedPost = postRepository.save(post);
        PostResponseDTO postResponseDTO = new PostResponseDTO();
        postResponseDTO.setId(savedPost.getId());
        postResponseDTO.setTitle(savedPost.getTitle());
        postResponseDTO.setContent(savedPost.getContent());
        postResponseDTO.setCreateAt(savedPost.getCreateAt());
        postResponseDTO.setCreateBy(user.getUsername());
        return postResponseDTO;
    }catch (Exception ex) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
    public List<PostUpdateResponseDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> {
                    PostUpdateResponseDTO postUpdateResponseDTO = new PostUpdateResponseDTO();
                    postUpdateResponseDTO.setId(post.getId());
                    postUpdateResponseDTO.setTitle(post.getTitle());
                    postUpdateResponseDTO.setContent(post.getContent());
                    postUpdateResponseDTO.setCreateAt(post.getCreateAt());
                    postUpdateResponseDTO.setUpdateAt(post.getUpdateAt());
                    postUpdateResponseDTO.setCreateBy(post.getCreateBy().getUsername());
                    return postUpdateResponseDTO;
                })
                .collect(Collectors.toList());
    }
    public PostUpdateResponseDTO getPostId(String id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            PostUpdateResponseDTO postUpdateResponseDTO = new PostUpdateResponseDTO();
            postUpdateResponseDTO.setId(post.getId());
            postUpdateResponseDTO.setTitle(post.getTitle());
            postUpdateResponseDTO.setContent(post.getContent());
            postUpdateResponseDTO.setCreateAt(post.getCreateAt());
            postUpdateResponseDTO.setUpdateAt(post.getUpdateAt());
            postUpdateResponseDTO.setCreateBy(post.getCreateBy().getUsername());
            return postUpdateResponseDTO;
        } else {
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }
    }
    public Post getPost(String id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            return optionalPost.get();
        } else {
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }
    }
    public PostUpdateResponseDTO updatePost(String postId, PostRequestDTO postDTO) {
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
            Post post = getPost(postId);
            User user = optionalUser.get();
            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());
            post.setCreateAt(post.getCreateAt());
            post.setUpdateAt(new Timestamp(System.currentTimeMillis()));
            post.setCreateBy(user);

            Post savedPost = postRepository.save(post);
            PostUpdateResponseDTO postUpdateResponseDTO = new PostUpdateResponseDTO();
            postUpdateResponseDTO.setId(savedPost.getId());
            postUpdateResponseDTO.setTitle(savedPost.getTitle());
            postUpdateResponseDTO.setContent(savedPost.getContent());
            postUpdateResponseDTO.setCreateAt(savedPost.getCreateAt());
            postUpdateResponseDTO.setUpdateAt(savedPost.getUpdateAt());
            postUpdateResponseDTO.setCreateBy(user.getUsername());
            return postUpdateResponseDTO;
        } catch (Exception ex) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
    public void deletePost(String id){
        postRepository.deleteById(id);
    }
    }

