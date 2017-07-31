package cn.bonoon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("s/login/test/activity")
public class TestActivityController {
	
	@ResponseBody
	@RequestMapping
	public Object json(){
		return null;
	}
}
