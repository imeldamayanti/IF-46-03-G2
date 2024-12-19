package com.tubes.entity;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ROLE_Admin")
public class Admin extends User {
	
    @Override
    public void login(){

    }
}
