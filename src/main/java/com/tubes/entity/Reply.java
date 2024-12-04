package com.tubes.entity;

import jakarta.persistence.*;

@Entity
public class Reply implements ContentAccess {
    /**
     * Static variable to generate unique IDs
     */
    private static long nextId = 1;

    /**
     * Unique identifier for the Reply
     */
    @Id
    private Long id;

    private int createdBy;
    private String replyContent;
    private String dateUploaded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forum_id", referencedColumnName = "id") // This will create a foreign key in the Reply table
    private Forum forum;

    /**
     * Constructors
     */
    public Reply() {
        this.id = generateId();
    }

    public Reply(int createdBy, String replyContent, String dateUploaded) {
        this.id = generateId();
        this.createdBy = createdBy;
        this.replyContent = replyContent;
        this.dateUploaded = dateUploaded;
    }

    /**
     * Generates a unique ID
     */
    private synchronized static Long generateId() {
        return nextId++;
    }

    /**
     * Getters and Setters
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
    public void createContent() {}

    public void editContent() {}

    public void deleteContent() {}

    public void displayReply() {}

    // toString for debugging purposes
    @Override
    public String toString() {
        return "Reply{id=" + id + ", createdBy='" + createdBy + "', replyContent=" + replyContent + ", dateUploaded=" + dateUploaded + "'}";
    }
}
