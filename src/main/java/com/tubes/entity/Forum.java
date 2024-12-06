package com.tubes.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Forum implements ContentAccess {
    /**
     * Migration
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int createdBy;
    private String title;
    private String forumContent;
    private String dateUploaded; //date

    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();  // Using List instead of ArrayList for better JPA support

    private int repliesCount; // biar di symbol comment ada total replies

    /**
        * Constructor
    */
    public Forum() {
        
    }

    public Forum(int createdBy, String title, String forumContent, String dateUploaded) {
        this.createdBy = createdBy;
        this.title = title;
        this.forumContent = forumContent;
        this.dateUploaded = dateUploaded;
        this.replies = new ArrayList<>();
    }

    /**
        * Getter And Setter
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForumContent() {
        return forumContent;
    }

    public void setForumContent(String forumContent) {
        this.forumContent = forumContent;
    }

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
        this.repliesCount = replies.size();  // Update replies count when the list is set
    }

    public void addReply(Reply reply) {
        replies.add(reply);
        reply.setForum(this); // Set the bidirectional relationship
        repliesCount = replies.size(); // Update replies count
    }

    public void removeReply(Reply reply) {
        replies.remove(reply);
        repliesCount = replies.size(); // Update replies count
    }

    public int getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(int repliesCount) {
        this.repliesCount = repliesCount;
    }

    // Other Methods
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
        return "Forum{id=" + id + ", createdBy=" + createdBy + ", title='" + title + "', forumContent=" 
                + forumContent + ", dateUploaded=" + dateUploaded + ", repliesCount=" + repliesCount + "}";
    }
}
