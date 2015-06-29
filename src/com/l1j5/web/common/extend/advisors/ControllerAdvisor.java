package com.l1j5.web.common.extend.advisors;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.l1j5.web.common.constant.VSConstants;
import com.l1j5.web.common.mvc.model.vo.ExtJsResultVO;
import com.l1j5.web.common.mvc.model.vo.ResultVO;

@Aspect
public class ControllerAdvisor {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Around("execution(com.l1j5.web.common.mvc.model.vo.ResultVO+ com.l1j5.web..controller.*Controller.*(*,org.springframework.validation.BindingResult,..)) && args(*,bindingResult,..)")//시작되고 끝날 때 호출됨
	public ResultVO aroundAdviceForResultVO(ProceedingJoinPoint joinPoint, BindingResult bindingResult) throws Throwable {
		if(bindingResult.hasErrors()) {
			ResultVO rvo = new ResultVO();
			rvo.put("error", bindingResult.getFieldErrors());
			
			if (logger.isDebugEnabled()) {
				logger.debug("ResultVO Validation Error");//log4j.xml  sysout대신에 보통 사용됨
				logger.debug(bindingResult.getFieldErrors());
			}
			
			return rvo;
		}
		return (ResultVO)joinPoint.proceed();//error가 없다면 create 메소드로 들어간다.
	}
}
