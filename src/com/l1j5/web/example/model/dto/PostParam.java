package com.l1j5.web.example.model.dto;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.l1j5.web.common.mvc.model.vo.ParameterVO;

public class PostParam extends ParameterVO {
	
	private String b_id;
	@NotEmpty
	private String title;
	@NotEmpty
	private String content;
	
	private String name;
	
	private String b_content;
	
	private String f_id;
	
	private String file_name;
	
	private String file_path;
	
	private String upd_uid;
	
	private String u_id;
	
	private MultipartFile attachment;
	
	private String[] searchCondition;
	
	private String searchText;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getB_id() {
		return b_id;
	}

	public void setB_id(String b_id) {
		this.b_id = b_id;
	}

	public String getUpd_uid() {
		return upd_uid;
	}

	public void setUpd_uid(String upd_uid) {
		this.upd_uid = upd_uid;
	}

	public MultipartFile getAttachment() {
		return attachment;
	}

	public void setAttachment(MultipartFile attachment) {
		this.attachment = attachment;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String[] searchCondition) {
		this.searchCondition = searchCondition;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getB_content() {
		return b_content;
	}

	public void setB_content(String b_content) {
		this.b_content = b_content;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getF_id() {
		return f_id;
	}

	public void setF_id(String f_id) {
		this.f_id = f_id;
	}	
	
}
