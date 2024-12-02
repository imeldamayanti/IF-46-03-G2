package com.tubes.seeder;

import com.opencsv.exceptions.CsvException;
import com.tubes.entity.Forum;
import com.tubes.repository.ForumRepository;
import com.tubes.utils.csvUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ForumSeeder {
    
    @Autowired ForumRepository forumRepository;

    @PostConstruct
    public void seedForum() throws CsvException {
        forumRepository.deleteAll();

        try {
            List<String[]> rows = csvUtils.readCsv("seeds/Forum.csv");
            List<Forum> forums = new ArrayList<>();

            for (String[] row : rows) {
                Forum forum = new Forum();

                forum.setCreatedBy(Integer.parseInt(row[1]));
                forum.setTitle(row[2]);
                forum.setForumContent(row[3]);
                forum.setDateUploaded(row[4]);
                forum.addReply(null);
                forum.setRepliesCount(Integer.parseInt(row[6]));

                forums.add(forum);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
