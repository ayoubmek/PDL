package com.pdl.pdl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {
	  @Id
	  @GeneratedValue
	  private Integer roleId;
	  private String authority;

	  public Role(){
		  super();
	  }

	  public Role(String authority){
		  this.authority = authority;
	  }
	  public Role(Integer roleId, String authority) {
		this.roleId = roleId;
		this.authority = authority;
	  }

	@Override
	public String getAuthority() {
		return this.authority;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
