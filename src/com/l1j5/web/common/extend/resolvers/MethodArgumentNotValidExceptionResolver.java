package com.l1j5.web.common.extend.resolvers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.core.Ordered;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.l1j5.web.common.constant.VSConstants;
import com.l1j5.web.common.mvc.model.vo.ExtJsResultVO;
import com.l1j5.web.common.mvc.model.vo.ResultVO;

public class MethodArgumentNotValidExceptionResolver implements
		HandlerExceptionResolver, Ordered {

	private int order;
	
	private Logger logger = Logger.getLogger(MethodArgumentNotValidExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mav = new ModelAndView();
		String contentType = request.getContentType();
		if (ex instanceof MethodArgumentNotValidException && "application/json".equals(contentType)) {
			BindingResult bindingResult = ((MethodArgumentNotValidException)ex).getBindingResult();
			ResultVO ret = new ResultVO();
			ret.put("error",bindingResult.getFieldErrors());
			response.setContentType(contentType);
			logger.error(ex.getMessage());
			try {
				response.getWriter().print(ret.toString());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		} else if (ex instanceof MultipartException) {
			if (ex.getCause().getCause() instanceof FileSizeLimitExceededException) {
				FileSizeLimitExceededException fsle = (FileSizeLimitExceededException)ex.getCause().getCause();
				
				FieldError fieldError = new FieldError(fsle.getFileName(), fsle.getFieldName(), String.valueOf(fsle.getPermittedSize()));
				List<FieldError> fieldErrors = new ArrayList<FieldError>(1);
				fieldErrors.add(fieldError);
				
				ExtJsResultVO ret = new ExtJsResultVO(false);
				ret.setErrors(fieldErrors);
				response.setContentType(VSConstants.FILE_UPLOAD_RESPONSE_HEADER);
				
				try {
					response.getWriter().print(ret.toString());
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		} else {
			try {
				response.sendError(500, ex.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return mav;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
