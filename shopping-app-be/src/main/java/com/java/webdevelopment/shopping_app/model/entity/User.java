package com.java.webdevelopment.shopping_app.model.entity;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Entity(name="User")
@Table
@Data
@AllArgsConstructor
@Builder
public class User {
    
	@Id
	private String id;

	@Column (nullable=false)
    private  String userName;
    
	@Column (nullable=false)
	private  String email;
    
	@Column (nullable=false)
	private  String passWord;
    
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "User")
	private Set<Order> orders;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "role_detail", 
        joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, 
        inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
	private final Set<Role> roles;

    
    //constructor
	public User()
	{
		orders=new HashSet<>();
		roles=new HashSet<>();
	}
	
	public void addRole(Role role)
	{
		this.roles.add(role);
	}
	
	public Set<Role> removeRole(Role role)
	{   
		this.roles.remove(role);
		return roles;
	}
	
	public Set<Role> removeRole(String id)
	{
		
		for( Role r :roles)
		{
         if(r.getId().equals(id))
		 roles.remove(r);
		}
		return roles;
	}
    
	public boolean isAdmin()
	{   // phương thức getAdmin của role không hoạt động
		// for( Role r :roles)
		// {
        //  if(r.getAdmin())
		//    return true;
		// }
		return false;
	}
  
	
	
}
