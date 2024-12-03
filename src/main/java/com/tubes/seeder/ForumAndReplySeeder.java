package com.tubes.seeder;

import com.opencsv.exceptions.CsvException;
import com.tubes.entity.Forum;
import com.tubes.entity.Reply;
import com.tubes.repository.ForumRepository;
import com.tubes.repository.ReplyRepository;
import com.tubes.utils.csvUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ForumAndReplySeeder {
    
    @Autowired
    private ForumRepository forumRepository;
    
    @Autowired
    private ReplyRepository replyRepository;

    @PostConstruct
    public void seedForumAndReply() throws CsvException {

        forumRepository.deleteAll();  // Clear existing forums
        replyRepository.deleteAll();  // Clear existing replies

        try {
            // Read CSV files for forums and replies
            List<String[]> forumRows = csvUtils.readCsv("seeds/Forum.csv");
            List<String[]> replyRows = csvUtils.readCsv("seeds/Reply.csv");

            // Lists to store entities before saving
            List<Forum> forums = new ArrayList<>();
            List<Reply> replies = new ArrayList<>();

            // Create Forum entities and manually set the ID for seed data
            for (String[] row : forumRows) {
                Forum forum = new Forum();
                forum.setId(Long.parseLong(row[0]));  // Manually set the ID from CSV
                forum.setCreatedBy(Integer.parseInt(row[1]));
                forum.setTitle(row[2]);
                forum.setForumContent(row[3]);
                forum.setDateUploaded(row[4]);

                forums.add(forum);
            }

            // Save all forums first so they get generated IDs
            forumRepository.saveAll(forums);

            // Create Reply entities and manually set the ID and associate with forums
            for (String[] row : replyRows) {
                Reply reply = new Reply();
                reply.setId(Long.parseLong(row[0]));  // Manually set the ID from CSV
                reply.setCreatedBy(Integer.parseInt(row[1]));
                reply.setReplyContent(row[2]);
                reply.setDateUploaded(row[3]);

                // Get the Forum based on the idForum (5th column in CSV)
                Long forumId = Long.parseLong(row[4]);
                Forum forum = forumRepository.findById(forumId).orElse(null);

                // If the forum is found, associate the reply with it
                if (forum != null) {
                    // Add the reply to the forum's replies list
                    forum.getReplies().add(reply);
                    reply.setForum(forum);  // Set the forum for the reply
                }

                replies.add(reply);
            }

            // Save all replies
            replyRepository.saveAll(replies);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
