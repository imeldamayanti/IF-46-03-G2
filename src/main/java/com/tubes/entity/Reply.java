package com.tubes.entity;

import jakarta.persistence.*;

@Entity
public class Reply implements ContentAccess {
    /**
        * Migration
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int createdBy;
    private String replyContent;
    private String dateUploaded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forum_id") // This will create a foreign key in the Reply table
    private Forum forum;

    /**
        * Constructor
    */
    public Reply() {}

    public Reply(int createdBy, String replyContent, String dateUploaded) {
        this.createdBy = createdBy;
        this.replyContent = replyContent;
        this.dateUploaded = dateUploaded;
    }

    /**
        * Getter and Setter
    */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    /**
        * Other Methods
    */

    public void createContent(){

    }
    
    public void editContent(){

    }

    public void deleteContent(){
        
    }

    public void displayReply(){
        
    }

    // toString for debugging purposes
    @Override
    public String toString() {
        return "Reply{id=" + id + ", createdBy='" + createdBy +  "', replyContent=" + replyContent + ", dateUploaded=" + dateUploaded + "'}";
    }
    

}
