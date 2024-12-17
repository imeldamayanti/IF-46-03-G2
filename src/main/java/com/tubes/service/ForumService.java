package com.tubes.service;

import java.time.LocalDate;
import com.tubes.entity.Forum;
import com.tubes.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForumService {

    @Autowired
    private ForumRepository forumRepository;

    public Long getNextId() {
        Long maxId = forumRepository.findMaxForumId().orElse(0L);
        return maxId + 1;
    }

    public Forum createForum(int createdBy, String title, String forumContent, LocalDate dateUploaded) {
        Forum forum = new Forum();
        forum.setId(getNextId());
        forum.setCreatedBy(createdBy);
        forum.setTitle(title);
        forum.setForumContent(forumContent);
        forum.setDateUploaded(dateUploaded);

        return forumRepository.save(forum);
    }

    
}
