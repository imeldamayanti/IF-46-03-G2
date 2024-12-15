package com.tubes.controllers;

import com.tubes.repository.UserRepository;
import com.tubes.repository.ForumRepository;
import com.tubes.repository.ReplyRepository;
import com.tubes.entity.Forum;
import com.tubes.entity.Reply;
import com.tubes.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    private ForumRepository forumRepository;

    @GetMapping("/discuss")
    public String showDiscussionPage(@RequestParam("forumId") Integer forumId, Model model) {
        // Fetch replies for the specific forum
        List<Reply> replies = replyRepository.findByForumId(forumId);
    
        // Map user IDs to usernames
        Map<Integer, String> userMap = userRepository.findAll()
                                                    .stream()
                                                    .collect(Collectors.toMap(
                                                        user -> user.getId().intValue(),
                                                        User::getUsername
                                                    ));
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
    
        // Map replies to a format suitable for Thymeleaf
        List<Map<String, Object>> formattedReplies = replies.stream().map(reply -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", reply.getId());
            map.put("createdBy", reply.getCreatedBy());
            map.put("replyContent", reply.getReplyContent());
            map.put("dateUploaded", reply.getDateUploaded().format(formatter));
            return map;
        }).collect(Collectors.toList());
    
        Long forumIdAsLong = forumId.longValue();
        
        // Fetch the specific Forum details
        Forum forum = forumRepository.findById(forumIdAsLong).orElse(null);
    
        if (forum == null) {
            model.addAttribute("error", "Forum not found");
            return "error";
        }
    
        // Map the forum's date to the same formatter
        String formattedForumDate = forum.getDateUploaded().format(formatter);
    
        // Pass the forum details and the formatted date to the Thymeleaf view
        model.addAttribute("replies", formattedReplies);
        model.addAttribute("userMap", userMap);
        model.addAttribute("forum", forum);
        model.addAttribute("formattedForumDate", formattedForumDate);
    
        return "discuss";
    }
    


    @GetMapping("/forum")
    public String showForumPage(Model model){
        List<Forum> forums = forumRepository.findAll();

        Map<Integer, String> userMap = userRepository.findAll()
                                                    .stream()
                                                    .collect(Collectors.toMap(
                                                        user -> user.getId().intValue(),  // Ensure type is Integer
                                                        User::getUsername
                                                    ));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

        List<Map<String, Object>> formattedReplies = forums.stream().map(forum -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", forum.getId());
            map.put("createdBy", forum.getCreatedBy());
            map.put("title", forum.getTitle());
            map.put("forumContent", forum.getForumContent());
            map.put("dateUploaded", forum.getDateUploaded().format(formatter));
            map.put("replyCount", forum.getRepliesCount());
            return map;
        }).collect(Collectors.toList());

        model.addAttribute("forums", formattedReplies);
        model.addAttribute("userMap", userMap);

        return "forum";
    }

}

