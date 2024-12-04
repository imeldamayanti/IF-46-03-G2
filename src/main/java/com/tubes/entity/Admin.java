package com.tubes.entity;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends User {
	
    @Override
    public void login(){

    }
}
