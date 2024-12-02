package com.tubes.seeder;

import com.opencsv.exceptions.CsvException;
import com.tubes.entity.Reply;
import com.tubes.repository.ReplyRepository;
import com.tubes.utils.csvUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReplySeeder {
    
    @Autowired ReplyRepository replyRepository;

    @PostConstruct
    public void seedReply() throws CsvException {
        replyRepository.deleteAll();

        try {
            List<String[]> rows = csvUtils.readCsv("seeds/Reply.csv");
            List<Reply> replies = new ArrayList<>();

            for (String[] row : rows) {
                Reply reply = new Reply();

                reply.setCreatedBy(Integer.parseInt(row[1]));
                reply.setreplyContent(row[2]);
                reply.setDateUploaded(row[3]);
            
                replies.add(reply);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
