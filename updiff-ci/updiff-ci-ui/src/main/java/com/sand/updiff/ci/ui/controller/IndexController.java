package com.sand.updiff.ci.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : sun.mt
 * @create : 2015/9/21 16:40
 * @since : 1.0.0
 */
@Controller
public class IndexController {
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping("/")
	public ModelAndView indexRoot() {
		return new ModelAndView("/index");
	}
	@RequestMapping("/index")
	public ModelAndView index() {
		return indexRoot();
	}
}
