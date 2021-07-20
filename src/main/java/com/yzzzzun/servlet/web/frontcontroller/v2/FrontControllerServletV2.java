package com.yzzzzun.servlet.web.frontcontroller.v2;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yzzzzun.servlet.web.frontcontroller.MyView;
import com.yzzzzun.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import com.yzzzzun.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import com.yzzzzun.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

	private final Map<String, ControllerV2> controllerMap = new ConcurrentHashMap<>();

	public FrontControllerServletV2() {
		controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
		controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
		controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws
		ServletException,
		IOException {
		String requestURI = request.getRequestURI();

		ControllerV2 controller = controllerMap.get(requestURI);
		if (controller == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		MyView myView = controller.process(request, response);
		myView.render(request, response);
	}
}
