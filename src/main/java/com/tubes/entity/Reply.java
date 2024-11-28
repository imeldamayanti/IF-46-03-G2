package main.java.com.tubes.entity;

import java.util.ArrayList;

@Entity
public class Reply implements ContentAccess{
    /**
        * Migration
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assumes 'id' is an auto-generated primary key
    private Long id;

    private Reader createdBy;
    private String replyContent;
    private String dateUploaded;

    /**
        * Constructor
    */

    public Reply(){}

    public Reply(Reader createdBy, String replyContent, String dateUploaded) {
        this.createdBy = createdBy;
        this.replyContent = replyContent;
        this.dateUploaded = dateUploaded;
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

    public String getreplyContent() {
        return replyContent;
    }

    public void setreplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
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
