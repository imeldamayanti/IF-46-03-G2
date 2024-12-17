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
public class ReplyController {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyRepository forumRepository;

    @Autowired
    private ReplyRepository userRepository;


}
