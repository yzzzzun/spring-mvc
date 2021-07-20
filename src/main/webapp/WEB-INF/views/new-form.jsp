<%--
  Created by IntelliJ IDEA.
  User: youngjun.jin
  Date: 2021/07/20
  Time: 4:05 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%-- 상대경로 현재디렉토리 기준 save.jsp--%>
<form action="save" method="post">
    username: <input type="text" name="username"/>
    age: <input type="text" name="age"/>
    <button type="submit">전송</button>
</form>
</body>
</html>
