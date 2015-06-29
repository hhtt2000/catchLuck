package com.l1j5.web.example.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rsc")
public class ResourceController {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping(value="/i18n", method={RequestMethod.GET})
	public @ResponseBody JSONObject i18n(Locale locale){
		JSONObject messageObj = new JSONObject();
		
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader(
					(this.getClass()
							.getResourceAsStream(String.format("/messages_%s.properties", locale.getLanguage())))));
			
			String line = null;
			while((line = br.readLine()) != null){
				line = line.trim();
				//공백이거나 주석문자 #으로 시작하는거는 제외시킨다.
				if(line.length() > 0 && line.indexOf("#") != 0){
					String[] keyVal = line.split("=");
					if(keyVal.length == 2){
						messageObj.accumulate(keyVal[0].replace(".", "_"), StringEscapeUtils.unescapeJava(keyVal[1]));
					}
				}
			}
			
		} catch(Exception e){
			logger.debug(e.getMessage());
		} finally{
			if(br != null) try{ br.close();} catch(Exception e){}
		}
		return messageObj;
	}

}
