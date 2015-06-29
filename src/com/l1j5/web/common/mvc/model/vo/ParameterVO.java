package com.l1j5.web.common.mvc.model.vo;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

@Configurable(preConstruction=true, dependencyCheck=true)
public class ParameterVO {

	
	@Value("#{T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()}")
	private String loginUserId;

	@Value("#{T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()}")
	private String crtId;
	
	private String crtDttm;
	
	@Value("#{T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()}")
	private String updId;
	
	private String updDttm;
	
	private int currentPageNo;
	
	private int fromRowNum;
	
	private int toRowNum;
	
	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public String getCrtId() {
		return crtId;
	}

	public void setCrtId(String crtId) {
		this.crtId = crtId;
	}

	public String getCrtDttm() {
		return crtDttm;
	}

	public void setCrtDttm(String crtDttm) {
		this.crtDttm = crtDttm;
	}

	public String getUpdId() {
		return updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}

	public String getUpdDttm() {
		return updDttm;
	}

	public void setUpdDttm(String updDttm) {
		this.updDttm = updDttm;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public int getFromRowNum() {
		return fromRowNum;
	}

	public void setFromRowNum(int fromRowNum) {
		this.fromRowNum = fromRowNum;
	}

	public int getToRowNum() {
		return toRowNum;
	}

	public void setToRowNum(int toRowNum) {
		this.toRowNum = toRowNum;
	}
	
}
