package com.l1j5.web.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.l1j5.web.common.mvc.model.vo.ResultVO;
import com.l1j5.web.example.model.dao.CommentDAO;
import com.l1j5.web.example.model.dto.Comment;
import com.l1j5.web.example.model.dto.CommentParam;

@Service
public class CommentService {

	@Autowired
	private CommentDAO commentDAO;

	public ResultVO getCommentList(CommentParam commentParam) {
		List<Comment> list = commentDAO.getCommentList(commentParam);
		ResultVO result = new ResultVO();
		result.put("commentList", list);
		return result;
	}

	public ResultVO deleteComment(String cmtNum) {
		int cnt = commentDAO.deleteComment(cmtNum);
		ResultVO result = new ResultVO();
		result.put("delCnt", cnt);
		return result;
	}

	public ResultVO updateComment(CommentParam commentParam) {
		Comment comment = new Comment();
		comment.setUpdCid(commentParam.getUpdId());
		comment.setCmtNum(commentParam.getCmtNum());;
		comment.setComments(commentParam.getComment());
		int cnt = commentDAO.updateComment(comment);
		
		ResultVO result = new ResultVO();
		result.put("updCnt", cnt);
		return result;
	}

	public ResultVO insertComment(CommentParam commentParam) {
		Comment comment = new Comment();
		comment.setCid(commentParam.getLoginUserId());
		comment.setCrtCid(commentParam.getCrtId());;
		comment.setBbsNum(commentParam.getBbsNum());
		comment.setComments(commentParam.getComment());
		int cnt = commentDAO.insertComment(comment);
		
		ResultVO result = new ResultVO();
		result.put("crtCnt", cnt);
		return result;
	}

}