package com.yzzzzun.servlet.web.frontcontroller.v5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yzzzzun.servlet.web.frontcontroller.ModelView;
import com.yzzzzun.servlet.web.frontcontroller.MyView;
import com.yzzzzun.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import com.yzzzzun.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import com.yzzzzun.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import com.yzzzzun.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import com.yzzzzun.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import com.yzzzzun.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import com.yzzzzun.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import com.yzzzzun.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

	private final Map<String, Object> handlerMappingMap = new ConcurrentHashMap<>();
	private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

	public FrontControllerServletV5() {
		initHandlerMappingMap();
		initHandlerAdapters();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws
		ServletException,
		IOException {

		Object handler = getHandler(request);

		if (handler == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		MyHandlerAdapter adapter = getHandlerAdapter(handler);
		ModelView modelView = adapter.handle(request, response, handler);

		MyView myView = viewResolver(modelView.getViewName());
		myView.render(modelView.getModel(), request, response);
	}

	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}

	private MyHandlerAdapter getHandlerAdapter(Object handler) {

		for (MyHandlerAdapter adapter : handlerAdapters) {
			if (adapter.supports(handler)) {
				return adapter;
			}
		}
		throw new IllegalArgumentException("handler adapter not found");
	}

	private Object getHandler(HttpServletRequest request) {
		return handlerMappingMap.get(request.getRequestURI());
	}

	private void initHandlerAdapters() {
		handlerAdapters.add(new ControllerV3HandlerAdapter());
		handlerAdapters.add(new ControllerV4HandlerAdapter());
	}

	private void initHandlerMappingMap() {
		handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

		handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
		handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
		handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
	}
}
