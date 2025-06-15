package com.pdl.pdl.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class AppUser implements UserDetails {

	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long userId ;

	  @Column(unique = true)
	  private String username;
	  private String password ;
	  private Date birthdate;

	  @ManyToMany(fetch = FetchType.EAGER)
	  @JoinTable(name ="user_role_join",
	             joinColumns = {@JoinColumn(name="userid")},
	             inverseJoinColumns = {@JoinColumn(name="roleid")})
	  private Set<Role> authorities;

	  public AppUser(){
		     super();
		     this.authorities = new HashSet<Role>();
		  }


	    public AppUser(String username, String password, Date birthdate, Set<Role> authorities) {
	        this.username = username;
	        this.password = password;
	        this.birthdate = birthdate;
	        this.authorities = authorities;
	    }

	public AppUser(Long userId, String username, String password, Set<Role> authorities, Date birthdate) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.birthdate = birthdate;
		this.authorities = authorities;
	}



	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setAuthorities(Set<Role> authorities) {
		this.authorities = authorities;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Date getBirthdate() {
		return birthdate;
	}


	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		 return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

}
