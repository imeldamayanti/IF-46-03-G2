package com.tubes.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import jakarta.persistence.*;

@Entity
public class Forum implements ContentAccess {
    /**
     * Static variable to generate unique IDs
     */
    private static long nextId = 1;

    /**
     * Unique identifier for the Forum
     */
    @Id
    private Long id;

    private int createdBy;
    private String title;
    private String forumContent;
    private String dateUploaded; // date

    private String replyIds;

    private int repliesCount; // to show total replies in the comment symbol

    /**
     * Constructors
     */
    public Forum() {
        this.id = generateId();
    }

    public Forum(int createdBy, String title, String forumContent, String dateUploaded) {
        this.id = generateId();
        this.createdBy = createdBy;
        this.title = title;
        this.forumContent = forumContent;
        this.dateUploaded = dateUploaded;
    }

    /**
     * Generates a unique ID
     */
    private synchronized static Long generateId() {
        return nextId++;
    }

    // Getters and Setters
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

    public List<Long> getReplyIds() {
        if (replyIds != null && !replyIds.isEmpty()) {
            // Convert the comma-separated string back to a List<Long>
            return Arrays.stream(replyIds.split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
        }
        return new ArrayList<>(); // Return empty list if replyIds is null or empty
    }

    public void setReplyIds(List<Long> replyIds) {
        // Convert List<Long> to a comma-separated string
        this.replyIds = replyIds.stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(","));
    }

    public void addReplyId(Long replyId) {
        // Get the current list of reply IDs
        List<Long> currentReplyIds = getReplyIds();
        currentReplyIds.add(replyId);
        
        // Update the replyIds field with the new list
        setReplyIds(currentReplyIds);

        // Update the repliesCount
        repliesCount = currentReplyIds.size();
    }

    public void removeReplyId(Long replyId) {
        // Get the current list of reply IDs
        List<Long> currentReplyIds = getReplyIds();

        // Remove the specified reply ID if it exists
        currentReplyIds.remove(replyId);
            
        // Update the replyIds field with the new list
        setReplyIds(currentReplyIds);

        // Update the repliesCount
        repliesCount = currentReplyIds.size();
    }

    public int getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(int repliesCount) {
        this.repliesCount = repliesCount;
    }

    // Other Methods
    public void createContent() {}

    public void editContent() {}

    public void deleteContent() {}

    public void displayReply() {}

    // toString for debugging purposes
    @Override
    public String toString() {
        return "Forum{id=" + id + ", createdBy=" + createdBy + ", title='" + title + "', forumContent="
                + forumContent + ", dateUploaded=" + dateUploaded + ", repliesCount=" + repliesCount + "}";
    }
}
