package com.tubes.entity;

import jakarta.persistence.*;

@Entity
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	protected String username;
	protected String email;
	private String password;
	protected String firstName;
	protected String lastName;
	protected int profilePic;
	protected String dateJoined;

	public User() {
	}

	// public String getPassword() {
	// 	return this.password;
	// }

	// /**
	//  * 
	//  * @param password
	//  */
	// public void setPassword(String password) {
	// 	this.password = password;
	// }

	// public void searchBook() {
	// 	throw new UnsupportedOperationException();
	// }

    public abstract void login();

}
