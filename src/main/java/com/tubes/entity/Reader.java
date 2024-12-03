package com.tubes.entity;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Reader")
public class Reader extends User {
	
    @Override
    public void login(){

    }
}
