package com.tubes.entity;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ROLE_Reader")
public class Reader extends User {
	
    @Override
    public void login(){

    }
}
