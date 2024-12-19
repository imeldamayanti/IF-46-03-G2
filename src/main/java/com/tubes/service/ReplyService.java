package com.tubes.service;

import java.time.LocalDate;
import java.util.List;

import com.tubes.entity.Forum;
import com.tubes.entity.Reply;
import com.tubes.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    public List<Reply> findAllReply() {
        return replyRepository.findAll();
    }

    public Long getNextId() {
        Long maxId = replyRepository.findMaxReplyId().orElse(0L);
        return maxId + 1;
    }

    public Reply createReply(int createdBy, String replyContent, LocalDate dateUploaded) {
        Reply reply = new Reply();
        reply.setId(getNextId());
        reply.setCreatedBy(createdBy);
        reply.setReplyContent(replyContent);
        reply.setDateUploaded(dateUploaded);

        return replyRepository.save(reply);
    }

}
