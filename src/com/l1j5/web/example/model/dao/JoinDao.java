package com.l1j5.web.example.model.dao;

import com.l1j5.web.example.model.dto.JoinParam;

public interface JoinDao {
	
	public int createUser(JoinParam param);
	
	public int checkCid(JoinParam param);
	
	public int createUserAuthority(JoinParam param);
	
}
