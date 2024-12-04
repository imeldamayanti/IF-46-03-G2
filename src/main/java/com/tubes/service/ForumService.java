// package com.tubes.service;

// import java.util.List;
// import java.util.Optional;

// import com.tubes.entity.Forum;
// import com.tubes.repository.ForumRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// @Service
// public class ForumService {

//     @Autowired
//     private ForumRepository forumRepository;

//     // Get all forums
//     public List<Forum> getAllForums() {
//         return forumRepository.findAll();
//     }

//     // Get a forum by its ID
//     public Optional<Forum> getForumById(Long id) {
//         return forumRepository.findById(id);
//     }

//     // Save a new forum
//     public Forum createForum(Forum forum) {
//         return forumRepository.save(forum);
//     }

//     // Delete a forum by its ID
//     public void deleteForum(Long id) {
//         forumRepository.deleteById(id);
//     }

//     // Update a forum
//     public Forum updateForum(Long id, Forum forum) {
//         // Check if the forum exists
//         if (forumRepository.existsById(id)) {
//             forum.setId(id);  // Ensure the ID is set correctly
//             return forumRepository.save(forum);
//         }
//         return null;
//     }
// }
