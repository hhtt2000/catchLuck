package com.l1j5.web.example.model.dto;

public class Comment {
	
	private String cmtNum;
	
	private String bbsNum;
	
	private String comments;
	
	private String cid;
	
	private String crtCid; 

	private String crtDttm;
	
	private String updCid;
	
	private String updDttm;

	public String getCmtNum() {
		return cmtNum;
	}

	public void setCmtNum(String cmtNum) {
		this.cmtNum = cmtNum;
	}

	public String getBbsNum() {
		return bbsNum;
	}

	public void setBbsNum(String bbsNum) {
		this.bbsNum = bbsNum;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comment) {
		this.comments = comment;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCrtCid() {
		return crtCid;
	}

	public void setCrtCid(String crtCid) {
		this.crtCid = crtCid;
	}

	public String getCrtDttm() {
		return crtDttm;
	}

	public void setCrtDttm(String crtDttm) {
		this.crtDttm = crtDttm;
	}

	public String getUpdCid() {
		return updCid;
	}

	public void setUpdCid(String updCid) {
		this.updCid = updCid;
	}

	public String getUpdDttm() {
		return updDttm;
	}

	public void setUpdDttm(String updDttm) {
		this.updDttm = updDttm;
	}
	

}
