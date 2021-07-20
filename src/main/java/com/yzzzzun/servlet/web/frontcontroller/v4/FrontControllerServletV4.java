package com.yzzzzun.servlet.web.frontcontroller.v4;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yzzzzun.servlet.web.frontcontroller.MyView;
import com.yzzzzun.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import com.yzzzzun.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import com.yzzzzun.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV4 extends HttpServlet {
	private final Map<String, ControllerV4> controllerMap = new ConcurrentHashMap<>();

	public FrontControllerServletV4() {
		controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
		controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
		controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws
		ServletException,
		IOException {

		String requestURI = request.getRequestURI();

		ControllerV4 controller = controllerMap.get(requestURI);
		if (controller == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		Map<String, String> paramMap = createParamMap(request);
		Map<String, Object> model = new ConcurrentHashMap<>();

		String viewName = controller.process(paramMap, model);

		MyView view = viewResolver(viewName);
		view.render(model, request, response);
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
