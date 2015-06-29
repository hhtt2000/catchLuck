package com.l1j5.web.example.service;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.l1j5.web.common.constant.VSConstants;
import com.l1j5.web.common.mvc.model.dao.GroupMembersDao;
import com.l1j5.web.common.mvc.model.dto.GroupMember;
import com.l1j5.web.common.mvc.model.vo.ExtJsResultVO;
import com.l1j5.web.common.mvc.model.vo.ResultVO;
import com.l1j5.web.example.model.dao.JoinDao;
import com.l1j5.web.example.model.dto.JoinParam;


@Service("joinService")
public class JoinService {
	
	private Logger logger = Logger.getLogger(JoinService.class);
	
	@Autowired
	private JoinDao joinDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private GroupMembersDao groupMembersDao;
	
	@Transactional
	public ResultVO create(JoinParam param) {


		String newPwd = param.getPasswd();
		param.setPasswd(passwordEncoder.encodePassword(newPwd, null));
		int ret = joinDao.createUser(param); 
		
		if(ret > 0){
			joinDao.createUserAuthority(param);
		}
		
		ResultVO result = new ResultVO();
		return result;

	}
	
	@Transactional
	public ResultVO checkValidation(JoinParam param) {
		int countCid = joinDao.checkCid(param);

		
		JSONObject resultJson = new JSONObject();
		resultJson.accumulate("cid", countCid == 0);
		
		return new ResultVO(resultJson);
	}
}
