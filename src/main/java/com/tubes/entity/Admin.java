package com.tubes.entity;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("User")
public class Admin extends User {
	
    @Override
    public void login(){

    }
}
