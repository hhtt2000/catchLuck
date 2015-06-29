package com.l1j5.web.example.model.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.l1j5.web.example.model.dao.QnaBbsDAO;
import com.l1j5.web.example.model.dto.Post;
import com.l1j5.web.example.model.dto.PostParam;

@Repository
public class QnaBbsDAOImpl extends SqlSessionDaoSupport implements QnaBbsDAO {

	@SuppressWarnings("unchecked")
	public List<Post> getPostList(PostParam param) {
		return getSqlSession().selectList(
				"com.l1j5.web.example.model.mapper.QnaBbs.getPostList", param);
	}

	public Post getPost(String b_id) {
		return (Post) getSqlSession().selectOne(
				"com.l1j5.web.example.model.mapper.QnaBbs.getPost", b_id);
	}

	@Override
	public int deletePost(PostParam postParam) {
		return getSqlSession().delete(
				"com.l1j5.web.example.model.mapper.QnaBbs.deletePost", postParam);
	}

	@Override
	public int updatePost(PostParam postParam) {
		return getSqlSession().update(
				"com.l1j5.web.example.model.mapper.QnaBbs.updatePost", postParam);
	}

	@Override
	public int insertPost(Post post) {
		return getSqlSession().insert(
				"com.l1j5.web.example.model.mapper.QnaBbs.insertPost", post);
	}

	@Override
	public int insertAttachFile(Post post) {
		return getSqlSession().insert(
				"com.l1j5.web.example.model.mapper.QnaBbs.insertAttachFile", post);
	}

	@Override
	public int getCountPostList(PostParam param) {
		return (Integer) getSqlSession()
				.selectOne(
						"com.l1j5.web.example.model.mapper.QnaBbs.getCountPostList",
						param);
	}

}