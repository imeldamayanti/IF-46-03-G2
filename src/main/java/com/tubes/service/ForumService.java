package com.tubes.service;

import java.util.List;
import com.tubes.entity.Forum;
import com.tubes.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForumService {

    @Autowired
    private ForumRepository forumRepository;

    public List<Forum> getAllForum () {
        return forumRepository.findAll();
    }

}
