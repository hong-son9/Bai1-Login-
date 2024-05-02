package com.example.Login.repository;

import com.example.Login.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String > {
    Optional<Comment> findByContent(String content);
    List<Comment> findByPostId(String postId);
}
