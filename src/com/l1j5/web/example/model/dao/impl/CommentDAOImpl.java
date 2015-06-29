package com.l1j5.web.example.model.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.l1j5.web.example.model.dao.CommentDAO;
import com.l1j5.web.example.model.dto.Comment;
import com.l1j5.web.example.model.dto.CommentParam;

@Repository
public class CommentDAOImpl extends SqlSessionDaoSupport implements CommentDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getCommentList(CommentParam commentParam) {

		return getSqlSession().selectList(
				"com.l1j5.web.example.model.mapper.Comment.getCommentList",
				commentParam);
	}

	@Override
	public int deleteCommentByBbsNum(String cmtNum) {
		return getSqlSession().delete(
				"com.l1j5.web.example.model.mapper.Comment.deleteCommentByBbsNum",
				cmtNum);
	}
	
	@Override
	public int deleteComment(String cmtNum) {
		return getSqlSession().delete(
				"com.l1j5.web.example.model.mapper.Comment.deleteComment",
				cmtNum);
	}

	@Override
	public int updateComment(Comment comment) {
		return getSqlSession().update(
				"com.l1j5.web.example.model.mapper.Comment.updateComment",
				comment);
	}

	@Override
	public int insertComment(Comment comment) {
		return getSqlSession().insert(
				"com.l1j5.web.example.model.mapper.Comment.insertComment",
				comment);
	}

}
