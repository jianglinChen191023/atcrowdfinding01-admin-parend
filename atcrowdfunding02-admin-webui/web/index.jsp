<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn1").click(function () {
                $.ajax({
                    url: "send/array.html",      // 请求目标资源的地址
                    type: "post",           // 请求方式
                    data: {
                        array: [5, 8, 12]
                    },       // 要发送的请求参数
                    dataType: "text",       // 如何对待服务器返回的数据
                    success: function (response) {  // 服务器端成功处理请求后调用的回调函数, response 是响应体数据
                        alert(response);
                    },
                    error: function () {    // 服务器端处理请求失败后调用的回调函数

                    }
                })
            });

            $("#btn2").click(function () {
                $.ajax({
                    url: "send/array/two.html",      // 请求目标资源的地址
                    type: "post",           // 请求方式
                    data: {
                        "array[0]": 5,
                        "array[1]": 8,
                        "array[2]": 12,
                    },       // 要发送的请求参数
                    dataType: "text",       // 如何对待服务器返回的数据
                    success: function (response) {  // 服务器端成功处理请求后调用的回调函数, response 是响应体数据
                        alert(response);
                    },
                    error: function () {    // 服务器端处理请求失败后调用的回调函数

                    }
                })
            });

            $("#btn3").click(function () {

                // 准备好要发送到服务器端的数组
                var array = [5, 8, 12];

                // 将 JSON 数组转换为 JSON 字符串
                var requestBody = JSON.stringify(array);

                $.ajax({
                    url: "send/array/three.html",      // 请求目标资源的地址
                    type: "post",           // 请求方式
                    data: requestBody,       // 请求体
                    contentType: "application/json;charset=UTF-8",       // 请求体的内容类型, 告诉服务器本次请求的请求体是 JSON 数据
                    dataType: "text",       // 如何对待服务器返回的数据
                    success: function (response) {  // 服务器端成功处理请求后调用的回调函数, response 是响应体数据
                        alert(response);
                    },
                    error: function () {    // 服务器端处理请求失败后调用的回调函数

                    }
                })
            });

            $("#btn4").click(function () {
                // 准备好要发送到服务器端的数组
                var student = {
                    stuId: 5,
                    stuName: "tom",
                    address: {
                        province: "广东",
                        city: "深圳",
                        street: "后端"
                    },
                    subjectList: [
                        {
                            subjectName: "javaSE",
                            subjectScore: 100
                        },
                        {
                            subjectName: "SSM",
                            subjectScore: 99
                        }
                    ],
                    map: {
                        k1: "v1",
                        k2: "v2"
                    }
                }

                // 将 JSON 数组转换为 JSON 字符串
                var requestBody = JSON.stringify(student);

                $.ajax({
                    url: "send/compose/object.html",      // 请求目标资源的地址
                    type: "post",           // 请求方式
                    data: requestBody,       // 请求体
                    contentType: "application/json;charset=UTF-8",       // 请求体的内容类型, 告诉服务器本次请求的请求体是 JSON 数据
                    dataType: "text",       // 如何对待服务器返回的数据
                    success: function (response) {  // 服务器端成功处理请求后调用的回调函数, response 是响应体数据
                        alert(response);
                    },
                    error: function () {    // 服务器端处理请求失败后调用的回调函数

                    }
                })
            });

            $("#btn5").click(function () {
                // 准备好要发送到服务器端的数组
                var student = {
                    stuId: 5,
                    stuName: "tom",
                    address: {
                        province: "广东",
                        city: "深圳",
                        street: "后端"
                    },
                    subjectList: [
                        {
                            subjectName: "javaSE",
                            subjectScore: 100
                        },
                        {
                            subjectName: "SSM",
                            subjectScore: 99
                        }
                    ],
                    map: {
                        k1: "v1",
                        k2: "v2"
                    }
                }

                // 将 JSON 数组转换为 JSON 字符串
                var requestBody = JSON.stringify(student);

                $.ajax({
                    url: "send/compose/object2.json",      // 请求目标资源的地址
                    type: "post",           // 请求方式
                    data: requestBody,       // 请求体
                    contentType: "application/json;charset=UTF-8",       // 请求体的内容类型, 告诉服务器本次请求的请求体是 JSON 数据
                    dataType: "JSON",       // 如何对待服务器返回的数据
                    success: function (response) {  // 服务器端成功处理请求后调用的回调函数, response 是响应体数据
                        alert(response);
                    },
                    error: function () {    // 服务器端处理请求失败后调用的回调函数

                    }
                })
            });

        })
    </script>
</head>
<body>

<%--<a href="${pageContext.request.contextPath}/test/ssm.html">测试 SMM 配置环境</a>--%>
<a href="test/ssm.html">测试 SMM 配置环境</a>

<br/>
<br/>

<button id="btn1">Send [5, 8, 12] One</button>

<br/>
<br/>

<button id="btn2">Send [5, 8, 12] One</button>

<br/>
<br/>

<button id="btn3">Send [5, 8, 12] One</button>

<br/>
<br/>

<button id="btn4">Send 4</button>

<br/>
<br/>

<button id="btn5">Send 5</button>

</body>
</html>
