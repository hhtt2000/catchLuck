package com.l1j5.web.example.model.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.l1j5.web.common.mvc.model.vo.ParameterVO;

public class CommentParam extends ParameterVO{
	@NotEmpty
	private String bbsNum;
	@NotEmpty
	@Length(max=200, min=10)
	private String comment;
	
	private String cmtNum;

	public String getBbsNum() {
		return bbsNum;
	}

	public void setBbsNum(String bbsNum) {
		this.bbsNum = bbsNum;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCmtNum() {
		return cmtNum;
	}

	public void setCmtNum(String cmtNum) {
		this.cmtNum = cmtNum;
	}
	
}