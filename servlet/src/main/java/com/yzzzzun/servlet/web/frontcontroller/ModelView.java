package com.yzzzzun.servlet.web.frontcontroller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ModelView {
	private String viewName;
	private Map<String, Object> model = new ConcurrentHashMap<>();

	public ModelView(String viewName) {
		this.viewName = viewName;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}
}
