package com.l1j5.web.example.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.l1j5.web.common.mvc.model.dto.L1j5User;
import com.l1j5.web.common.mvc.model.vo.ResultVO;
import com.l1j5.web.example.model.dto.Post;
import com.l1j5.web.example.model.dto.PostParam;
import com.l1j5.web.example.service.BbsService;

@Controller
@RequestMapping("/bbs")
public class BbsController {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private BbsService bbsService;

	@RequestMapping("/list")
	public String list(HttpServletRequest request) {
		HttpSession session = request.getSession();
		SecurityContextImpl obj = (SecurityContextImpl) session
				.getAttribute("SPRING_SECURITY_CONTEXT");
		obj.getAuthentication().getPrincipal();

		System.out.println(obj.getAuthentication().getPrincipal());

		L1j5User user = (L1j5User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		System.out.println(user.toString());

		return "bbs/list";
	}

	@RequestMapping("/search")
	public @ResponseBody
	JSONObject search(PostParam postParam) {
		JSONObject json = bbsService.getPostList(postParam);
		
		System.out.println(json);
		
		return json;
	}

	@RequestMapping(value = "/form/{b_id}", method = RequestMethod.GET)
	public ModelAndView form(ModelAndView mav, @PathVariable String b_id) {
		if (!"new".equals(b_id)) {
			Post post = bbsService.getPost(b_id);
			mav.addObject("post", post);
		}
		mav.setViewName("/bbs/form");
		return mav;
	}

	@RequestMapping(value = "/detail/{b_id}", method = RequestMethod.GET)
	public ModelAndView detail(ModelAndView mav, @PathVariable String b_id) {
		Post post = bbsService.getPost(b_id);
		mav.addObject("post", post);
		mav.setViewName("/bbs/detail");
		return mav;
	}

	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ModelAndView insert(ModelAndView mav, @Valid PostParam postParam,
			BindingResult bindingResult) throws IOException {
		if (bindingResult.hasErrors()) {
			ResultVO rvo = new ResultVO();
			rvo.put("error", bindingResult.getFieldError());
			mav.addObject("err", rvo.toString());
		} else {
			int result = bbsService.insertPost(postParam);
			mav.addObject("crtCnt", result);
		}

		mav.addObject("postParam", postParam);
		mav.setViewName("/bbs/form");
		return mav;
	}

	@RequestMapping(value = "/detail", method = RequestMethod.PUT)
	public @ResponseBody
	ResultVO update(@Valid PostParam postParam, BindingResult bindingResult) {
		
		int result = bbsService.updatePost(postParam);		

		ResultVO resultVO = new ResultVO();
		resultVO.put("updCnt", result);

		return resultVO;
	}

	@RequestMapping(value = "/detail/{b_id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ResultVO delete(PostParam postParam) {
		int result = bbsService.deletePost(postParam);
		ResultVO resultVO = new ResultVO();
		resultVO.put("delCnt", result);

		return resultVO;
	}
}