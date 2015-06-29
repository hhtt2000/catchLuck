package com.l1j5.web.example.model.dao;

import java.util.List;

import com.l1j5.web.example.model.dto.Comment;
import com.l1j5.web.example.model.dto.CommentParam;

public interface CommentDAO {
	
//	public Comment getComment(CommentParam comment Param);
	
	public List<Comment> getCommentList(CommentParam commentParam);
	
	public int deleteCommentByBbsNum(String cmtNum);
	
	public int deleteComment(String cmtNum);
	
	public int updateComment(Comment comment);

	public int insertComment(Comment comment);

}