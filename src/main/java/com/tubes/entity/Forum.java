package com.tubes.entity;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Forum implements ContentAccess {

    /**
     * Static variable to generate unique IDs
     */
    private static long nextId = 1; // Manually control the ID sequence

    /**
     * Unique identifier for the Forum
     */
    @Id
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private int createdBy;

    @Column(nullable = false, length = 255)
    private String title;

    @Lob
    @Column(nullable = false)
    private String forumContent;

    @Column(name = "date_uploaded", nullable = false)
    private LocalDate dateUploaded;

    @Column
    private int replyCount;

    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    // Constructors
    public Forum() {
        this.id = generateId();
    }

    public Forum(int createdBy, String title, String forumContent, LocalDate dateUploaded) {
        this.id = generateId();
        this.createdBy = createdBy;
        this.title = title;
        this.forumContent = forumContent;
        this.dateUploaded = dateUploaded;
        this.replyCount = 0;
    }

    public synchronized static Long generateId() {
        return nextId++;
    }

    public static synchronized void resetIdCounter() {
        nextId = 1;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id){
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

    public LocalDate getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(LocalDate dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void addReply(Reply reply) {
        if (this.replies == null) {
            this.replies = new ArrayList<>();
        }
        this.replies.add(reply);
        reply.setForum(this);
        replyCount++;
    }

    public void removeReply(Reply reply) {
        replies.remove(reply);
        reply.setForum(null);
        replyCount--;
    }

    public int getRepliesCount() {
        return replies.size();
    }

    public void createContent() {}

    public void editContent() {}

    public void deleteContent() {}

    @Override
    public String toString() {
        return "Forum{" +
                "id=" + id +
                ", createdBy=" + createdBy +
                ", title='" + title + '\'' +
                ", forumContent='" + forumContent + '\'' +
                ", dateUploaded=" + dateUploaded +
                ", replyCount=" + replyCount +
                '}';
    }
}
