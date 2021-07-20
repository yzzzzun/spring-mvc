<%@ page import="com.yzzzzun.servlet.domain.Member" %>
<%@ page import="com.yzzzzun.servlet.domain.MemberRepository" %><%--
  Created by IntelliJ IDEA.
  User: youngjun.jin
  Date: 2021/07/20
  Time: 3:31 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //request, response 사용 가능
    MemberRepository memberRepository = MemberRepository.getInstance();

    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));

    Member member = new Member(username, age);
    memberRepository.save(member);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id=<%=member.getId()%>
    </li>
    <li>username=<%=member.getUsername()%>
    </li>
    <li>age=<%=member.getAge()%>
    </li>
</ul>
<a href="/index.html">메인으로</a>
</body>
</html>
