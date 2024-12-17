package com.tubes.controllers;

import com.tubes.entity.Reply;
import com.tubes.repository.ReplyRepository;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReplyController {

    @Autowired
    private ReplyRepository replyRepository;

    @PostMapping("/submit-reply")
    public String submitReply(@RequestParam String replyContent, @RequestParam String dateUploaded) {
        Reply reply = new Reply();
        reply.setReplyContent(replyContent);
        reply.setDateUploaded(LocalDate.parse(dateUploaded));

        replyRepository.save(reply);

        return "redirect:/discuss";
    }
}
