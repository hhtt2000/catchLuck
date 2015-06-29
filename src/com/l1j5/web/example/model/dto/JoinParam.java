package com.l1j5.web.example.model.dto;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class JoinParam {
	
	@NotEmpty
	@Length(min=6, max=16)
	private String cid;
	
	@NotEmpty
	@Length(min=6, max=20)
	private String passwd;
	
	@NotEmpty
	@Length(min=2, max=20)
	private String name;
	
	@Length(max=15)
	private String cellPhone;
	
	@Email
	@Length(max=50)
	private String email;
	
	@Length(max=100)
	private String address;
	
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
