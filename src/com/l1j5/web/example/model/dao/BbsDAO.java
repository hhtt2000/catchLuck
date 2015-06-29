package com.l1j5.web.example.model.dao;

import java.util.List;

import com.l1j5.web.example.model.dto.Post;
import com.l1j5.web.example.model.dto.PostParam;

public interface BbsDAO {
	
	public List<Post> getPostList(PostParam param);
	
	public Post getPost(String b_id);
	
	public int deletePost(PostParam postParam);

	public int updatePost(PostParam postParam);

	public int insertPost(Post post);

	public int insertAttachFile(Post post);

	public int getCountPostList(PostParam param);
	
}