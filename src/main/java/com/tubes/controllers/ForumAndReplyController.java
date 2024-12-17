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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;

@Controller
public class ForumAndReplyController {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ForumRepository forumRepository;

    @GetMapping("/discuss")
    public String showDiscussionPage(
        @RequestParam("forumId") Integer forumId,
        Model model,
        Principal principal // Mendapatkan informasi pengguna yang login
    ) {
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

        // Pass authentication status to Thymeleaf
        model.addAttribute("isAuthenticated", principal != null);

        return "discuss";
    }

    

    @GetMapping("/forum")
    public String showForumPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Forum> forums = forumRepository.findAll();
        
        Map<Integer, String> userMap = userRepository.findAll()
                                                    .stream()
                                                    .collect(Collectors.toMap(
                                                        user -> user.getId().intValue(),
                                                        User::getUsername
                                                    ));
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
    
        List<Map<String, Object>> formattedForums = forums.stream().map(forum -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", forum.getId());
            map.put("createdBy", forum.getCreatedBy());
            map.put("title", forum.getTitle());
            map.put("forumContent", forum.getForumContent());
            map.put("dateUploaded", forum.getDateUploaded().format(formatter));
            map.put("replyCount", forum.getRepliesCount());
            return map;
        }).collect(Collectors.toList());
    
        model.addAttribute("forums", formattedForums);
        model.addAttribute("userMap", userMap);
    
        // Tangani user login dengan aman menggunakan userDetails
        if (userDetails != null) {
            User user = userRepository.findByUsername(userDetails.getUsername());
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null);
        }
    
        return "forum";
    }

    @GetMapping("/newForum")
    public String showNewForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            // Jika user tidak login, arahkan ke halaman login
            return "redirect:/signin";
        }

        User user = userRepository.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "newForum";
    }

    @PostMapping("/submit-forum")
    public String submitForum(
            @RequestParam("forumTitle") String forumTitle,
            @RequestParam("forumContent") String forumContent,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
    
        if (userDetails == null) {
            return "redirect:/signin";
        }
    
        // Fetch logged-in user
        User user = userRepository.findByUsername(userDetails.getUsername());
    
        // Cari ID maksimum di database
        Optional<Long> maxId = forumRepository.findMaxForumId();
        Long nextId = maxId.map(id -> id + 1).orElse(1L); // Jika tidak ada ID, mulai dari 1
    
        // Buat Forum instance dengan ID yang dihitung
        Forum forum = new Forum();
        forum.setId(nextId);
        forum.setTitle(forumTitle);
        forum.setForumContent(forumContent);
        forum.setCreatedBy(user.getId().intValue());
        forum.setDateUploaded(LocalDate.now());
    
        forumRepository.save(forum);
    
        return "redirect:/forum";
    }
    
    @PostMapping("/submit-reply")
    public String submitReply(
        @RequestParam("replyContent") String replyContent,
        @RequestParam("forumId") Long forumId,
        @AuthenticationPrincipal UserDetails userDetails,
        Model model
    ) {
        // Periksa apakah pengguna sudah login
        if (userDetails == null) {
            return "redirect:/signin";
        }

        // Cari user berdasarkan username
        User user = userRepository.findByUsername(userDetails.getUsername());

        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "error";
        }

        Optional<Long> maxId = replyRepository.findMaxReplyId();
        Long nextId = maxId.map(id -> id + 1).orElse(1L); // Jika tidak ada ID, mulai dari 1

        // Simpan reply melalui ReplyService
        Reply reply = new Reply();
        reply.setId(nextId);
        reply.setCreatedBy(user.getId().intValue());
        reply.setReplyContent(replyContent);
        reply.setDateUploaded(LocalDate.now());

        // Atur forum yang menjadi referensi balasan
        Forum forum = forumRepository.findById(forumId).orElse(null);

        if (forum == null) {
            model.addAttribute("error", "Forum not found.");
            return "error";
        }

        reply.setForum(forum);
        replyRepository.save(reply);

        forum.addReply(reply);
        forumRepository.save(forum);

        return "redirect:/discuss?forumId=" + forumId;
    }

}

