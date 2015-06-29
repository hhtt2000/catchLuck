package com.l1j5.web.common.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This controller is used to provide functionality for the login page.
 * 
 * @author jrha
 */
@Controller
public class LoginLogoutController {

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public void login() {
	}
}
