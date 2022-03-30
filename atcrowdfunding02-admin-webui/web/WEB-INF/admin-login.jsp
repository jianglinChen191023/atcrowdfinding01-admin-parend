<%--
  Created by IntelliJ IDEA.
  User: chenjianglin
  Date: 2022/3/30
  Time: 08:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">

    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link rel="stylesheet"
          href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/login.css">

    <script src="jquery/jquery-2.1.1.min.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>

    <style>
        #toReg {
            margin-top: -20px;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div>
                <a class="navbar-brand" href="/index.htm" style="font-size: 32px;">尚筹网-创意产品众筹平台</a>
            </div>
        </div>
    </div>
</nav>

<div class="container">

    <form id="loginForm" action="admin/do/login.html" method="POST"
          class="form-signin" role="form">
        <h2 class="form-signin-heading">
            <i class="glyphicon glyphicon-log-in"></i> 管理员登录
        </h2>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="floginacct"
                   name="loginAcct" value="tom" placeholder="请输入登录账号"
                   onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                   onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'') "
                   style="ime-mode: disabled"> <span
                class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="password" class="form-control"
                   onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                   id="fuserpswd" name="userPswd" value="123456" placeholder="请输入登录密码"
                   style="margin-top: 10px;"> <span
                class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <button type="submit" class="btn btn-lg btn-success btn-block">登录</button>
    </form>
</div>
</body>
</html>
