package com.tubes.controllers;

import com.tubes.repository.UserRepository;
import com.tubes.repository.ForumRepository;
import com.tubes.repository.ReplyRepository;
import com.tubes.entity.Forum;
import com.tubes.entity.Reply;
import com.tubes.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


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
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        Model model,
        Principal principal
    ) {
        Forum forum = forumRepository.findById(forumId.longValue()).orElse(null);
        if (forum == null) {
            model.addAttribute("error", "Forum not found");
            return "error";
        }
    
        List<Reply> allReplies = replyRepository.findByForumId(forumId);
        int totalReplies = allReplies.size();
        int totalPages = (int) Math.ceil((double) totalReplies / size);
        if (totalPages == 0) totalPages = 1;
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;
    
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, totalReplies);
        if (startIndex < 0) startIndex = 0;
    
        List<Reply> paginatedReplies = allReplies.subList(startIndex, endIndex);
    
        Map<Integer, String> userMap = userRepository.findAll()
                                                    .stream()
                                                    .collect(Collectors.toMap(
                                                        user -> user.getId().intValue(),
                                                        User::getUsername
                                                    ));
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        List<Map<String, Object>> formattedReplies = paginatedReplies.stream().map(reply -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", reply.getId());
            map.put("createdBy", reply.getCreatedBy());
            map.put("replyContent", reply.getReplyContent());
            map.put("dateUploaded", reply.getDateUploaded().format(formatter));
            return map;
        }).collect(Collectors.toList());
    
        // Mendapatkan pengguna yang sedang login
        User currentUser = null;
        boolean isAdmin = false;
        if (principal != null) {
            currentUser = userRepository.findByUsername(principal.getName());
            if (currentUser != null && "ROLE_Admin".equals(currentUser.getRole())) {
                isAdmin = true;
            }
        }
    
        // Menambahkan atribut ke model
        model.addAttribute("user", currentUser);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("replies", formattedReplies);
        model.addAttribute("userMap", userMap);
        model.addAttribute("forum", forum);
        model.addAttribute("formattedForumDate", forum.getDateUploaded().format(formatter));
        model.addAttribute("isAuthenticated", principal != null);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalReplies", totalReplies);
    
        return "discuss";
    }
    
    @GetMapping("/forum")
    public String showForumPage(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue =  "latest") String sort,
        Model model
    ) {

        // Get paginated forums
        Page<Forum> forumPage;
        if ("popular".equalsIgnoreCase(sort)) {
            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "replyCount"));
            forumPage = forumRepository.findAll(pageable);
        } else { // Default to "latest"
            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "dateUploaded"));
            forumPage = forumRepository.findAll(pageable);
        }
        // Map user IDs to usernames
        Map<Integer, String> userMap = userRepository.findAll()
                                                    .stream()
                                                    .collect(Collectors.toMap(
                                                        user -> user.getId().intValue(),
                                                        User::getUsername
                                                    ));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

        // Format paginated forums
        List<Map<String, Object>> formattedForums = forumPage.getContent().stream().map(forum -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", forum.getId());
            map.put("createdBy", forum.getCreatedBy());
            map.put("title", forum.getTitle());
            map.put("forumContent", forum.getForumContent());
            map.put("dateUploaded", forum.getDateUploaded().format(formatter));
            map.put("replyCount", forum.getRepliesCount());
            return map;
        }).collect(Collectors.toList());

        // Add pagination and forum data to the model
        model.addAttribute("forums", formattedForums);
        model.addAttribute("userMap", userMap);
        model.addAttribute("currentPage", Math.max(1, forumPage.getNumber() + 1));
        model.addAttribute("totalPages", forumPage.getTotalPages());
        model.addAttribute("pageSize", size); 
        model.addAttribute("currentSort", sort);           

        // Handle user login securely using userDetails
        if (userDetails != null) {
            User user = userRepository.findByUsername(userDetails.getUsername());
            model.addAttribute("user", user);

            // Check if the logged-in user has ROLE_ADMIN and add that to the model
            boolean isAdmin = user.getRole().equals("ROLE_Admin");
            model.addAttribute("isAdmin", isAdmin);
        } else {
            model.addAttribute("user", null);
            model.addAttribute("isAdmin", false);
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

    @GetMapping("/delete/{forumId}/{replyId}")
    public String deleteReply(
        @PathVariable("forumId") Long forumId,
        @PathVariable("replyId") Long replyId,
        Model model,
        Principal principal
    ) {
        // Find the forum and reply by their IDs
        Forum forum = forumRepository.findById(forumId).orElse(null);
        Reply reply = replyRepository.findById(replyId).orElse(null);

        if (forum == null || reply == null) {
            model.addAttribute("error", "Forum or reply not found");
            return "error";
        }

        // Ensure that the user has admin role before deleting
        User currentUser = userRepository.findByUsername(principal.getName());
        if (currentUser == null || !currentUser.getRole().equals("ROLE_Admin")) {
            model.addAttribute("error", "You do not have permission to delete this reply.");
            return "error";
        }

        // Delete the reply and update the forum
        forum.removeReply(reply);
        replyRepository.delete(reply);

        // Redirect back to the discussion page
        return "redirect:/discuss?forumId=" + forumId;
    }

    @GetMapping("/delete/forum/{forumId}")
    public String deleteForumAndReplies(
        @PathVariable("forumId") Long forumId,
        Model model,
        Principal principal
    ) {
        // Find the forum by its ID
        Forum forum = forumRepository.findById(forumId).orElse(null);
    
        if (forum == null) {
            model.addAttribute("error", "Forum not found");
            return "error"; 
        }
    
        // Ensure that the user has admin role before deleting
        User currentUser = userRepository.findByUsername(principal.getName());
        if (currentUser == null || !currentUser.getRole().equals("ROLE_Admin")) {
            model.addAttribute("error", "You do not have permission to delete this forum.");
            return "error";
        }
    
        // First, delete all replies associated with the forum
        List<Reply> replies = forum.getReplies();
        if (replies != null) {
            for (Reply reply : replies) {
                replyRepository.delete(reply);
            }
        }
    
        // Delete the forum
        forumRepository.delete(forum);
    
        // Redirect to the forum
        return "redirect:/forum";
    }
    
}

