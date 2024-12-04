package com.tubes.seeder;

import com.opencsv.exceptions.CsvException;
import com.tubes.entity.Forum;
import com.tubes.entity.Reply;
import com.tubes.repository.ForumRepository;
import com.tubes.repository.ReplyRepository;
import com.tubes.utils.csvUtils;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ForumAndReplySeeder {

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @PostConstruct
    @Transactional // Ensure transactional context
    public void seedForumAndReply() throws CsvException {
        replyRepository.deleteAll();
        forumRepository.deleteAll();
        
        try {
            List<String[]> forumRows = csvUtils.readCsv("seeds/Forum.csv");
            List<String[]> replyRows = csvUtils.readCsv("seeds/Reply.csv");

            List<Forum> forums = new ArrayList<>();
            for (String[] row : forumRows) {
                Forum forum = new Forum();
                forum.setId(Long.parseLong(row[0]));
                forum.setCreatedBy(Integer.parseInt(row[1]));
                forum.setTitle(row[2]);
                forum.setForumContent(row[3]);
                forum.setDateUploaded(row[4]);
                forums.add(forum);
            }

            Map<Long, Forum> forumMap = forums.stream()
            .collect(Collectors.toMap(
                Forum::getId, 
                Function.identity(), 
                (existing, replacement) -> existing // Keep the existing entry in case of duplicates
            ));
            
            List<Reply> replies = new ArrayList<>();
            for (String[] row : replyRows) {
                Reply reply = new Reply();
                reply.setId(Long.parseLong(row[0]));
                reply.setCreatedBy(Integer.parseInt(row[1]));
                reply.setReplyContent(row[2]);
                reply.setDateUploaded(row[3]);


                Long forumId = Long.parseLong(row[4]);
                Forum forum = forumMap.get(forumId);

                if (forum == null) {
                    System.err.println("Forum with ID " + forumId + " not found. Skipping reply.");
                    continue;
                }

                reply.setForum(forum);
                forum.addReplyId(reply.getId());
                replies.add(reply);
            }

            forumRepository.saveAll(forums);
            replyRepository.saveAll(replies);
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}