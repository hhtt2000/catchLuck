package com.l1j5.web.example.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.l1j5.web.common.mvc.model.vo.ExtJsResultVO;
import com.l1j5.web.common.mvc.model.vo.ResultVO;
import com.l1j5.web.example.model.dto.JoinParam;
import com.l1j5.web.example.service.JoinService;
//import com.l1j5.web.example.model.dto.MfmManufacturerParam;


@Controller
@RequestMapping("/join")//context root 다음에 join이면 이쪽으로 들어옴
public class JoinController {
	
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(JoinController.class);
	
	@Autowired
	private JoinService joinService;

	@RequestMapping(value="/index", method=RequestMethod.GET)//get방식으로 index면 이쪽으로 들어옴
	public String join() {
		
		return "join";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody ResultVO create(@Valid JoinParam join, BindingResult bindingResult) {
		ResultVO result = null;
		if(bindingResult.hasErrors()){
			result = new ResultVO();
			result.put("error", bindingResult.getAllErrors());
		} else{
			result = joinService.create(join);
		}
		return result;
	}
	
	@RequestMapping(value="/checkValidation", method=RequestMethod.GET)
	public @ResponseBody ResultVO checkValidation(JoinParam param) {
		ResultVO result = joinService.checkValidation(param);
		return result;
	}
}
