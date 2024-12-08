package com.tubes.service;

import java.util.List;
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

}
