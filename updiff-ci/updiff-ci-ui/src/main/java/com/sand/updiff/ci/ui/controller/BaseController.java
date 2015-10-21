package com.sand.updiff.ci.ui.controller;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author : sun.mt
 * @create : 2015/9/25 9:16
 * @since : 1.0.0
 */
public class BaseController {


	@InitBinder("paging")
	public void initBinder2(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("paging.");
	}

}
