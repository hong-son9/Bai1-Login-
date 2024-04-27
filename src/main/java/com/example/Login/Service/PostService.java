package com.example.Login.Service;

import com.example.Login.Dto.Request.PostDTO;
import com.example.Login.Entity.Post;
import com.example.Login.Entity.User;
import com.example.Login.Repository.PostRepository;
import com.example.Login.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    public Post createPost(PostDTO postDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            // Xử lý khi không tìm thấy người dùng
            return null;
        }

        User user = optionalUser.get();

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setCreateAt(new Timestamp(System.currentTimeMillis()));
        post.setCreateBy(user);

        return postRepository.save(post);
    }
    }

