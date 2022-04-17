<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zn-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<script type="text/javascript">
    $(function () {
        $("#asyncBtn").click(function () {
            console.log('ajax 函数之前')
            $.ajax({
                'url': 'test/ajax/async.html',
                'type': 'post',
                'dataType': 'text',
                'success': function (response) {
                    // success 是接收到服务器端响应后执行
                    console.log('ajax 函数内部的 success 是函数 ' + response);
                }
            });

            // 在 $.ajax() 执行完成后执行, 不等待 success() 函数
            console.log('ajax 函数之后');
        });
    });
</script>
<body>

<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <button id="asyncBtn">发送 Ajax 请求</button>
        </div>
    </div>
</div>

</body>
</html>
