package com.l1j5.web.example.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.l1j5.web.common.mvc.model.vo.ResultVO;
import com.l1j5.web.example.model.dto.CommentParam;
import com.l1j5.web.example.service.CommentService;

@Controller
@RequestMapping("/cmt")
public class CommentController {

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private CommentService commentService;

	// 조회 기능 /comment/{bbsNum} GET 메소드
	@RequestMapping(value="/comment/{bbsNum}", method=RequestMethod.GET)
	public @ResponseBody
	ResultVO list(CommentParam commentParam) {
		return commentService.getCommentList(commentParam);
	}

	// 삭제 기능 /comment/{cmt_num} DELETE 메소드
	@RequestMapping(value="/comment/{cmtNum}", method=RequestMethod.DELETE)
	public @ResponseBody
	ResultVO delete(@PathVariable String cmtNum) {
		return commentService.deleteComment(cmtNum);
	}

	// 수정 기능 /comment PUT 메소드
	@RequestMapping(value="/comment", method=RequestMethod.PUT)
	public @ResponseBody
	ResultVO update(@Valid CommentParam commentParam,
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			ResultVO resultVO = new ResultVO();
			resultVO.put("error", bindingResult.getFieldErrors());
			return resultVO;
		} else {			
			return commentService.updateComment(commentParam);
		}	
	}

	// 등록 기능 /comment POST 메소드
	@RequestMapping(value="/comment", method=RequestMethod.POST)
	public @ResponseBody
	ResultVO insert(@Valid CommentParam commentParam, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			System.out.println("에러진입");
			ResultVO resultVO = new ResultVO();
			resultVO.put("error", bindingResult.getFieldErrors());
			return resultVO;
		} else {
			System.out.println("성공진입");
			ResultVO resultVO = commentService.insertComment(commentParam);
//			logger.debug(resultVO.get("crtCnt"));
//			if(resultVO.get("crtCnt").equals("1")) {
//				logger.debug("조회전");
//				Object commentList = commentService.getCommentList(commentParam).get("commentList");
//				resultVO.put("commentList", commentList);
//				logger.debug(commentList);
//			}			
			return resultVO;
		}
	}
}