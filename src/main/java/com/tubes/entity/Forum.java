package main.java.com.tubes.entity;
// package com.tubes.entity Error di Yusuf

import java.util.ArrayList;
import java.util.List;

@Entity
public class Forum implements ContentAccess {
    /**
     * Migration
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assumes 'id' is an auto-generated primary key
    private Long id;

    private Reader createdBy;
    private String title;
    private String forumContent;
    private String dateUploaded;
    private ArrayList<Reply> replies;
    private int repliesCount;

    /**
     * Constructor
    */

    public Forum(){}

    public Forum(Reader createdBy, String title, String forumContent, String dateUploaded){
        this.createdBy = createdBy;
        this.title = title;
        this.forumContent = forumContent;
        this.dateUploaded = dateUploaded;
        this.replies = new ArrayList<Reply>();
    }

    /**
     * Setter and Getter
    */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reader getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Reader createdBy) {
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

    public void addReply(Reply reply) {
        this.replies.add(reply);
    }

    public void removeReply(Reply reply) {
        this.replies.remove(reply);
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setRepliesCount(int repliesCount){
        this.repliesCount = repliesCount;
    }

    public int getRepliesCount(){
        return this.repliesCount;
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

    public void displayForum(){

    }

    // toString for debugging purposes
    @Override
    public String toString() {
        return "Forum{id=" + id + ", createdBy='" + createdBy + "', title='" + title + "', forumContent=" 
        + forumContent + ", dateUploaded=" + dateUploaded + ", replies='" + replies + "', repliesCount=" + repliesCount + "'}";
    }

}