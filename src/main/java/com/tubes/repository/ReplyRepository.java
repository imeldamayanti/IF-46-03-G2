package com.tubes.repository;

import java.util.List;
import java.util.Optional;

import com.tubes.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    // findAll provided by JpaRepository

	// 
    List<Reply> findByForumId(Integer forumId);
    
    @Query("SELECT MAX(r.id) FROM Reply r")
    Optional<Long> findMaxReplyId();
    
} 