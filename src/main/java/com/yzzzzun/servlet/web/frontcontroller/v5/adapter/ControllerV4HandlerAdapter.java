package com.yzzzzun.servlet.web.frontcontroller.v5.adapter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yzzzzun.servlet.web.frontcontroller.ModelView;
import com.yzzzzun.servlet.web.frontcontroller.MyView;
import com.yzzzzun.servlet.web.frontcontroller.v4.ControllerV4;
import com.yzzzzun.servlet.web.frontcontroller.v5.MyHandlerAdapter;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {

	@Override
	public boolean supports(Object handler) {
		return handler instanceof ControllerV4;
	}

	@Override
	public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		ServletException,
		IOException {

		ControllerV4 controller = (ControllerV4)handler;

		Map<String, String> paramMap = createParamMap(request);
		Map<String, Object> model = new ConcurrentHashMap<>();

		String viewName = controller.process(paramMap, model);

		ModelView modelView = new ModelView(viewName);
		modelView.setModel(model);
		return modelView;
	}

	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}

	private Map<String, String> createParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new ConcurrentHashMap<>();
		request.getParameterNames()
			.asIterator()
			.forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
		return paramMap;
	}
}
