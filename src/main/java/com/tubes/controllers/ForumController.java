package com.tubes.controllers;

import com.tubes.repository.UserRepository;
import com.tubes.repository.ReplyRepository;
import com.tubes.entity.Reply;
import com.tubes.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ForumController {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/discuss")
    public String showDiscussionPage(Model model) {
        List<Reply> replies = replyRepository.findAll();

        // Map createdBy IDs to usernames (handle type mismatch by casting if needed)
        Map<Integer, String> userMap = userRepository.findAll()
                                                     .stream()
                                                     .collect(Collectors.toMap(
                                                         user -> user.getId().intValue(),  // Ensure type is Integer
                                                         User::getUsername
                                                     ));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

        List<Map<String, Object>> formattedReplies = replies.stream().map(reply -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", reply.getId());
            map.put("createdBy", reply.getCreatedBy());
            map.put("replyContent", reply.getReplyContent());
            map.put("dateUploaded", reply.getDateUploaded().format(formatter));
            return map;
        }).collect(Collectors.toList());


        model.addAttribute("replies", formattedReplies);
        model.addAttribute("userMap", userMap);

        return "discuss";
    }
}

