package com.example.Login.Service;

import com.example.Login.Dto.Request.CommentDTO;
import com.example.Login.Entity.Comment;
import com.example.Login.Entity.Post;
import com.example.Login.Entity.User;
import com.example.Login.Repository.CommentRepository;
import com.example.Login.Repository.PostRepository;
import com.example.Login.Repository.UserRepository;
import com.example.Login.exception.AppException;
import com.example.Login.exception.ErrorCode;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment createComment(String postId, CommentDTO commentDTO) {
        Logger logger = LoggerFactory.getLogger(CommentService.class);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            logger.error("Authentication is null or not authenticated.");
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String username = authentication.getName();
        logger.info("Username: {}", username);

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            logger.error("User with username {} not found.", username);
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            logger.error("Post with id {} not found.", postId);
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }

        Post post = optionalPost.get();
        User user = optionalUser.get();

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setCreateAt(new Timestamp(System.currentTimeMillis()));
        comment.setCreateBy(user);

        logger.info("Comment content: {}", commentDTO.getContent());

        Comment savedComment = commentRepository.save(comment);
        logger.info("Comment saved with id: {}", savedComment.getId());

        post.getComments().add(savedComment);

        return savedComment;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
//
    public Comment getCommentById(String commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        return optionalComment.orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));
    }
//
    public Comment updateComment(String commentId, CommentDTO commentDTO) {
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
        comment.setContent(commentDTO.getContent());
        comment.setUpdateAt(new Timestamp(System.currentTimeMillis()));

        return commentRepository.save(comment);
    }

    public void deleteComment(String commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment comment = optionalComment.orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        commentRepository.delete(comment);
    }
}

