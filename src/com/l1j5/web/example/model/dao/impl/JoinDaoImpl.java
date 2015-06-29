package com.l1j5.web.example.model.dao.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.l1j5.web.common.constant.VSConstants;
import com.l1j5.web.example.model.dao.JoinDao;
import com.l1j5.web.example.model.dto.JoinParam;

@Repository//repository가 있어야 spring에서 dao로 인식

/*
public class JoinDaoImpl extends SqlSessionDaoSupport implements JoinDao{

	@Override
	public int checkCid(JoinParam param) {
		return (Integer)this.getSqlSession().selectOne("com.l1j5.web.example.model.mapper.Join.checkCid", param);
	}
	
	@Override
	public int checkWorkNo(JoinParam param) {
		return (Integer)this.getSqlSession().selectOne("com.l1j5.web.example.model.mapper.Join.checkWorkNo", param);
	}

	@Override
	public int createCompany(JoinParam param) {
		return this.getSqlSession().insert("com.l1j5.web.example.model.mapper.Join.createCompany", param);
	}
	
	
	@Override
	public int createUser(JoinParam param) {
		return this.getSqlSession().insert("com.l1j5.web.example.model.mapper.Join.createUser", param);
	}

}
*/

public class JoinDaoImpl extends SqlSessionDaoSupport implements JoinDao{



	@Override
	public int createUser(JoinParam param) {
		
		return getSqlSession().insert("com.l1j5.web.example.model.mapper.Join.createUser", param);
	}

	@Override
	public int checkCid(JoinParam param) {
		// TODO Auto-generated method stub
		
		return (Integer)getSqlSession().selectOne("com.l1j5.web.example.model.mapper.Join.checkCid", param);//카운트를 얻기 위해 쓰는 메소드
		/*
		getJdbcTemplate().query(sql, new Object[]{param.getCid()}, new RowMapper<Integer>(){

			@Override
			public Integer mapRow(ResultSet arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				
				return arg0.getInt(1);
			}
			
		});*/
	}


	@Override
	public int createUserAuthority(JoinParam param) {
		param.setRole(VSConstants.AUTH_ROLE_USER);
		return getSqlSession().insert("com.l1j5.web.example.model.mapper.Join.createUserAuthority", param);
	}

}
