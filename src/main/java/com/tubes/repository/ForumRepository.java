package com.tubes.repository;

import com.tubes.entity.Forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForumRepository extends JpaRepository<Forum, Long> {
    // findAll provided by JpaRepository

	// 

    @Query("SELECT MAX(f.id) FROM Forum f")
    Optional<Long> findMaxForumId();
    
} 