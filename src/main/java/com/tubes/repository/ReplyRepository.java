package com.tubes.repository;

import java.util.List;
import com.tubes.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    // findAll provided by JpaRepository

	// 
    List<Reply> findByForumId(Integer forumId);
} 