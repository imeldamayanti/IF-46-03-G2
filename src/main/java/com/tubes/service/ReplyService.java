// package com.tubes.service;

// import java.util.List;
// import java.util.Optional;

// import com.tubes.entity.Reply;
// import com.tubes.entity.Forum;
// import com.tubes.repository.ReplyRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// @Service
// public class ReplyService {

//     @Autowired
//     private ReplyRepository replyRepository;

//     @Autowired
//     private ForumService forumService; // Inject ForumService to handle forum-related logic

//     // Get all replies
//     public List<Reply> getAllReplies() {
//         return replyRepository.findAll();
//     }

//     // Get a reply by its ID
//     public Optional<Reply> getReplyById(Long id) {
//         return replyRepository.findById(id);
//     }

//     // Save a new reply and associate it with a forum
//     public Reply createReply(Long forumId, Reply reply) {
//         Optional<Forum> forumOptional = forumService.getForumById(forumId);
//         if (forumOptional.isPresent()) {
//             Forum forum = forumOptional.get();
//             reply.setForum(forum);  // Associate the reply with the forum
//             forum.addReply(reply);  // Add the reply to the forum's list of replies
//             forumService.createForum(forum); // Save the updated forum with new reply

//             return replyRepository.save(reply); // Save the reply in the database
//         }
//         return null;  // Return null if forum doesn't exist
//     }

//     // Delete a reply by its ID
//     public void deleteReply(Long id) {
//         replyRepository.deleteById(id);
//     }

//     // Update a reply
//     public Reply updateReply(Long id, Reply reply) {
//         if (replyRepository.existsById(id)) {
//             reply.setId(id);  // Ensure the ID is set correctly
//             return replyRepository.save(reply);
//         }
//         return null;
//     }
// }
