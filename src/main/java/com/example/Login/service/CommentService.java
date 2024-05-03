package com.example.Login.service;

import com.example.Login.dtos.request.CommentDTO;
import com.example.Login.dtos.request.response.CommentResponseDTO;
import com.example.Login.dtos.request.response.CommentUpdateResponseDTO;
import com.example.Login.dtos.request.response.PostUpdateResponseDTO;
import com.example.Login.entity.Comment;
import com.example.Login.entity.Post;
import com.example.Login.entity.User;
import com.example.Login.repository.CommentRepository;
import com.example.Login.repository.PostRepository;
import com.example.Login.repository.UserRepository;
import com.example.Login.exception.AppException;
import com.example.Login.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public CommentResponseDTO createComment(String postId, CommentDTO commentDTO) {
        Logger logger = LoggerFactory.getLogger(CommentService.class);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String username = authentication.getName();

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }

        Post post = optionalPost.get();
        User user = optionalUser.get();

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setCreateAt(new Timestamp(System.currentTimeMillis()));
        comment.setCreateBy(user);
        Comment savedComment = commentRepository.save(comment);
        post.getComments().add(savedComment);

        CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
        commentResponseDTO.setId(savedComment.getId());
        commentResponseDTO.setContent(savedComment.getContent());
        commentResponseDTO.setCreateAt(savedComment.getCreateAt());
        commentResponseDTO.setCreateBy(user.getUsername());
        return commentResponseDTO;
    }


    public List<CommentUpdateResponseDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(post -> {
                    CommentUpdateResponseDTO commentUpdateResponseDTO = new CommentUpdateResponseDTO();
                    commentUpdateResponseDTO.setId(post.getId());
                    commentUpdateResponseDTO.setContent(post.getContent());
                    commentUpdateResponseDTO.setCreateAt(post.getCreateAt());
                    commentUpdateResponseDTO.setUpdateAt(post.getUpdateAt());
                    commentUpdateResponseDTO.setCreateBy(post.getCreateBy().getUsername());
                    return commentUpdateResponseDTO;
                })
                .collect(Collectors.toList());
    }

    //
    public CommentUpdateResponseDTO getCommentById(String id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            CommentUpdateResponseDTO commentUpdateResponseDTO = new CommentUpdateResponseDTO();
            commentUpdateResponseDTO.setId(comment.getId());
            commentUpdateResponseDTO.setContent(comment.getContent());
            commentUpdateResponseDTO.setCreateAt(comment.getCreateAt());
            commentUpdateResponseDTO.setUpdateAt(comment.getUpdateAt());
            commentUpdateResponseDTO.setCreateBy(comment.getCreateBy().getUsername());
            return commentUpdateResponseDTO;
        } else {
            throw new AppException(ErrorCode.COMMENT_NOT_FOUND);
        }
    }

    //
    public CommentUpdateResponseDTO updateComment(String commentId, CommentDTO commentDTO) {
        Logger logger = LoggerFactory.getLogger(CommentService.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        Optional<User> optionalUser = userRepository.findByUsername(authentication.getName());
        if (optionalUser.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new AppException(ErrorCode.COMMENT_NOT_FOUND);
        }

        Comment comment = optionalComment.get();
        User user = optionalUser.get();
        comment.setContent(commentDTO.getContent());
        comment.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        comment.setCreateBy(user);


        Comment saveComments = commentRepository.save(comment);
        CommentUpdateResponseDTO commentUpdateResponseDTO = new CommentUpdateResponseDTO();
        commentUpdateResponseDTO.setId(saveComments.getId());
        commentUpdateResponseDTO.setContent(saveComments.getContent());
        commentUpdateResponseDTO.setCreateAt(saveComments.getCreateAt());
        commentUpdateResponseDTO.setUpdateAt(saveComments.getUpdateAt());
        commentUpdateResponseDTO.setCreateBy(user.getUsername());
        return commentUpdateResponseDTO;
    }
    public void deleteComment(String commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment comment = optionalComment.orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        commentRepository.delete(comment);
    }
}

