package com.l1j5.web.example.model.dto;

import org.json.simple.JSONObject;

public class Post {
	private String b_id;

	private String title;

	private String b_content;

	private String name;

	private String write_date;

	private String f_id;

	private String file_name;

	private String file_path;

	private String cid;
	
	private String u_id;

	public String getB_id() {
		return b_id;
	}

	public void setB_id(String b_id) {
		this.b_id = b_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getB_content() {
		return b_content;
	}

	public void setB_content(String b_content) {
		this.b_content = b_content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWrite_date() {
		return write_date;
	}

	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}

	public String getF_id() {
		return f_id;
	}

	public void setF_id(String f_id) {
		this.f_id = f_id;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("b_id", b_id);
		json.put("title", title);
		json.put("b_content", b_content);
		json.put("name", name);
		json.put("write_date", write_date);
		json.put("f_id", f_id);
		json.put("file_name", file_name);
		json.put("file_path", file_path);
		json.put("cid", cid);
		json.put("u_id", u_id);

		return json.toJSONString();
	}
}
