<%--
  Created by IntelliJ IDEA.
  User: chenjianglin
  Date: 2022/3/28
  Time: 05:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>错误</title>
</head>
<body>

<h1>出错了</h1>

<!-- 从请求域取出 Exception 对象, 再近一步访问 message 属性就能够显示错误信息 -->
${requestScope.exception.message}
</body>
</html>
