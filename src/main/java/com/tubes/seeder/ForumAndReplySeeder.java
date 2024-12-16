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
import java.time.LocalDate; // Change this to LocalDate
import java.time.format.DateTimeFormatter;  
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

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

    @PostConstruct
    @Transactional
    public void seedForumAndReply() throws CsvException {
        // Clear existing data
        replyRepository.deleteAll();
        forumRepository.deleteAll();
        Forum.resetIdCounter();
        Reply.resetIdCounter();

        try {
            // Load CSV data
            List<String[]> forumRows = csvUtils.readCsv("seeds/Forum.csv");
            List<String[]> replyRows = csvUtils.readCsv("seeds/Reply.csv");

            // Parse forums
            List<Forum> forums = new ArrayList<>();
            for (String[] row : forumRows) {
                if (row.length < 5) {
                    System.err.println("Invalid forum row: " + String.join(",", row));
                    continue;
                }

                Forum forum = new Forum();
                forum.setCreatedBy(Integer.parseInt(row[1]));
                forum.setTitle(row[2]);
                forum.setForumContent(row[3]);

                LocalDate dateUploaded = LocalDate.parse(row[4], formatter); // Change to LocalDate
                forum.setDateUploaded(dateUploaded);

                forums.add(forum);
            }

            // Save forums and create a map of ID -> Forum
            forumRepository.saveAll(forums);
            Map<Long, Forum> forumMap = forums.stream()
                .collect(Collectors.toMap(Forum::getId, Function.identity()));

            // Parse replies
            List<Reply> replies = new ArrayList<>();
            for (String[] row : replyRows) {
                if (row.length < 5) {
                    System.err.println("Invalid reply row: " + String.join(",", row));
                    continue;
                }

                Long forumId = Long.parseLong(row[4]);
                Forum forum = forumMap.get(forumId);
                if (forum == null) {
                    System.err.println("Forum with ID " + forumId + " not found. Skipping reply.");
                    continue;
                }

                Reply reply = new Reply();
                reply.setCreatedBy(Integer.parseInt(row[1]));
                
                LocalDate dateUploaded = LocalDate.parse(row[2], formatter); // Change to LocalDate
                reply.setDateUploaded(dateUploaded);

                reply.setReplyContent(row[3]);
                reply.setForum(forum); // Set forum relationship
                replies.add(reply);
                forum.addReply(reply);
            }
                
            // Save replies
            forumRepository.saveAll(forums);
            replyRepository.saveAll(replies);

            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
