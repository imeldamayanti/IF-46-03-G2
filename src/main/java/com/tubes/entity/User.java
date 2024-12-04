package com.tubes.entity;
import jakarta.persistence.*;
// import org.springframework.security.crypto.bcrypt.BCrypt;

// @MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public abstract class User {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String dateJoined;


    @Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = true)
	private int profilePic;

    
    public abstract void login();
	
	public User() {}
    
    public User(String username, String email, String rawPassword, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        // this.password = hashPassword(rawPassword); // Hash password saat inisialisasi
        this.firstName = firstName;
        this.lastName = lastName;
        // this.dateJoined = LocalDateTime.now();
    }

	// private String hashPassword(String rawPassword) {
    //     return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    // }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    // public LocalDateTime getDateJoined() {
    //     return dateJoined;
    // }

    public String getUsername() {
        return username;
    }

    public int getProfilePic() {
        return profilePic;
    }



    
    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

	
}
