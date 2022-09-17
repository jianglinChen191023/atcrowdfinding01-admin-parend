<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zn-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css"/>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript">
    $(function () {
        // 调用后面声明的函数 - 对
        initPagination();
    });

    // 初始化分页函数
    // 生成页码导航条的函数
    const initPagination = () => {
        // 获取总记录数
        const totalRecord = ${ requestScope.pageInfo.total };
        // 声明一个 JSON 对象存储 Pagination 的配置
        const properties = {
            // 边缘页数
            num_edge_entries: 3,
            // 主体页数
            num_display_entries: 5,
            // 监听"翻页"事件, 跳转页面时触发的函数
            callback: pageSelectCallback,
            // 每页显示的数据的数量
            items_per_page: ${ requestScope.pageInfo.pageSize },
            // Pagination 内部使用 pageIndex 来管理页码, pageIndex 从 0 开始, pageNum 从 1 开始, 所以要减1
            current_page: ${ requestScope.pageInfo.pageNum - 1 },
            // 上一页按钮上显示的文本
            prev_text: "上一页",
            // 下一页按钮上显示的文本
            next_text: "下一页"
        }

        // 生成分页
        $("#Pagination").pagination(totalRecord, properties);
    }

    // 监听点击"翻页"事件
    // 回调函数的含义: 声明出来以后不是自己调用, 而是交给系统或框架调用
    // 用户点击 "1,2,3" 这样的页码时调用这个函数实现跳转
    // pageIndex: 从 0 开始的当前页码
    const pageSelectCallback = (pageIndex, jQuery) => {
        // 根据 pageIndex 计算得到 pageNum
        const pageNum = pageIndex + 1;

        // 从当前请求把请求参数取出
        const keyword = '${ param.keyword }';

        // 跳转页面
        window.location.href = 'admin/get/page.html?pageNum=' + pageNum + '&keyword=' + keyword;

        // 由于每一个页码按钮都是超链接, 所以在这个函数最后取消超链接的默认行为
        return false;
    }
</script>
<body>

<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form action="admin/get/page.html" class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input value="${ param.keyword }" name="keyword" class="form-control has-success"
                                       type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <security:authorize access="hasAuthority('user:delete')">
                        <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                                class=" glyphicon glyphicon-remove"></i> 删除
                        </button>
                    </security:authorize>
                    <!-- 旧代码
                    <button type="button" class="btn btn-primary" style="float:right;"
                            onclick="window.location.href='add.html'">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    -->
                    <!-- 新代码 -->
                    <%-- 对新增按钮进行权限控制 --%>
                    <security:authorize access="hasAuthority('user:save')">
                        <a style="float:right;" href="admin/to/add/page.html" class="btn btn-primary">
                            <i class="glyphicon glyphicon-plus"></i>
                            新增
                        </a>
                    </security:authorize>

                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${ empty requestScope.pageInfo.list }">
                                <tr>
                                    <td colspan="6" align="center">抱歉!没有查询到数据!</td>
                                </tr>
                            </c:if>
                            <c:if test="${ !empty requestScope.pageInfo.list }">
                                <c:forEach items="${ requestScope.pageInfo.list }" var="admin" varStatus="myStatus">
                                    <tr>
                                        <td>${ myStatus.count }</td>
                                        <td><input type="checkbox"></td>
                                        <td>${ admin.loginAcct }</td>
                                        <td>${ admin.userName }</td>
                                        <td>${ admin.email }</td>
                                        <td>
                                                <%-- 旧代码
                                                <button type="button" class="btn btn-success btn-xs"><i
                                                        class=" glyphicon glyphicon-check"></i></button>--%>
                                            <a href="assign/to/assign/role/page.html?adminId=${ admin.id }&pageNum=${ requestScope.pageInfo.pageNum }&keyword=${ param.keyword }"
                                               class="btn btn-success btn-xs"><i class="glyphicon glyphicon-check"></i></a>
                                            <!-- 旧代码 - 编辑
                                            <button type="button" class="btn btn-primary btn-xs">
                                                <i class=" glyphicon glyphicon-pencil"></i>
                                            </button>
                                            -->
                                            <a href="admin/to/edit/page.html?adminId=${ admin.id }&pageNum=${ requestScope.pageInfo.pageNum }&keyword=${ param.keyword }"
                                               class="btn btn-primary btn-xs">
                                                <i class=" glyphicon glyphicon-pencil"></i>
                                            </a>
                                            <!-- 旧代码
                                                <button type="button" class="btn btn-danger btn-xs">
                                                  <i class=" glyphicon glyphicon-remove"></i>
                                                </button>
                                            -->
                                            <a href="admin/remove/${ admin.id }/${ requestScope.pageInfo.pageNum }/${ param.keyword }.html"
                                               class="btn btn-danger btn-xs">
                                                <i class="glyphicon glyphicon-remove"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                            <tfoot>
                            <!-- 旧代码
                            <tr>
                                <td colspan="6" align="center">
                                    <ul class="pagination">
                                        <li class="disabled"><a href="#">上一页</a></li>
                                        <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
                                        <li><a href="#">2</a></li>
                                        <li><a href="#">3</a></li>
                                        <li><a href="#">4</a></li>
                                        <li><a href="#">5</a></li>
                                        <li><a href="#">下一页</a></li>
                                    </ul>
                                </td>
                            </tr>
                            -->
                            <!-- 新代码 pagination -->
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"> <!-- 这里显示分页 --> </div>
                                </td>
                            </tr>
                            </tfoot>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
