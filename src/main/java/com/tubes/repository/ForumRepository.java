package com.tubes.repository;

import com.tubes.entity.Forum;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Long> {
    // findAll provided by JpaRepository

	// 
} 