package com.example.demo.entities;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
public class LoginEntities {

	    @NotBlank(message="Username can't be blank")
	    @Size(min=3, max = 60,message="Name should be between 3-60 character")
	    private String username;

	    @NotBlank(message="Password can't be blank")
	    @Size(min = 6, max = 40,message="Password should be between 6-40 character")
		private String password;

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }
}
