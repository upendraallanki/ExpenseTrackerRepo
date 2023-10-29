package com.jsp.et.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class ControllerProject 
{
	@RequestMapping("/msg")
	public String message()
	{
		return "<h1>Rest Controller Annotation</h1>";
	}
}
