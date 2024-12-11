package com.tubes.entity;

import java.time.LocalDate;
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
    private LocalDate dateUploaded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forum_id", referencedColumnName = "id")
    private Forum forum;

    public Reply() {
        this.id = generateId();
    }

    public Reply(int createdBy, String replyContent, LocalDate dateUploaded) {
        this.id = generateId();
        this.createdBy = createdBy;
        this.replyContent = replyContent;
        this.dateUploaded = dateUploaded;
    }

    public synchronized static Long generateId() {
        return nextId++;
    }

    public static synchronized void resetIdCounter() {
        nextId = 1;
    }

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

    public LocalDate getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(LocalDate dateUploaded) {
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
        return "Reply{id=" + id + 
               ", createdBy='" + createdBy + 
               "', replyContent=" + replyContent + 
               ", dateUploaded=" + dateUploaded + 
               ", forumId=" + (forum != null ? forum.getId() : "null") + 
               "'}";
    }
    
}
