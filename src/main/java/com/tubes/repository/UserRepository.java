package com.tubes.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tubes.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
 

}
