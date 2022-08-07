$(function () {
    // 1. 为分页操作准备初始化数据
    window.pageNum = 1;
    window.pageSize = 5;
    window.keyword = "";

    // 调用执行分页函数, 显示分页效果
    generatePage();

    // 给查询按钮绑定点击响应函数
    $("#searchBtn").click(function () {
        // 获取关键词数据赋值给对应的全局变量
        window.keyword = $("#keywordInput").val();
        // 刷新数据
        generatePage();
    })

    // 点击新增按钮打开模态框
    $('#showAddModalBtn').click(function () {
        $('#addModal').modal('show');
    });

    // 监听单击事件 - 保存模态框中的保存按钮
    $('#saveRoleBtn').click(function () {
        // 获取用户在输入框中输入的角色名称
        // #addModal: 找到添加模态框
        // 空格表示在后代元素中继续查找
        // [name=roleName] 表示匹配 name 属性名为 roleName 的
        var roleName = $.trim($('#addModal [name=roleName]').val());

        // 发送 ajax 请求
        $.ajax({
            url: 'role/save.json',
            type: 'post',
            data: {
                name: roleName
            },
            dateType: 'json',
            success: function (response) {
                if (response.result === 'SUCCESS') {
                    layer.msg('操作成功!');
                    // 关闭模态框
                    $('#addModal').modal('hide');
                    // 清理模态框
                    $('#addModal [name=roleName]').val('');
                    // 重新加载分页
                    window.pageNum = 99999999;
                    generatePage();
                } else if (response.result === 'FAILED') {
                    layer.msg('操作失败! ' + response.message);
                }
            },
            error: function (response) {
                layer.msg(response.status + ' ' + response.statusText)
            }
        })
    });

    // 使用 JQuery对象的 on() 函数`动态`绑定点击事件
    $('#rolePageBody').on('click', '.pencilBtn', function () {
        // 打开模态框
        $('#editModal').modal('show');
        // 获取表格中当前行中的角色名称
        var roleName = $(this).parent().prev().text();
        // 获取当前元素的id - 角色id
        window.roleId = this.id;
        // 给模态框中的文本框赋值角色名称
        $('#editModal input[name=roleName]').val(roleName);
    });

    $('#updateRoleBtn').click(function () {
        var roleName = $('#editModal input[name=roleName]').val();

        // 发送 ajax 请求
        $.ajax({
            url: 'role/update.json',
            type: 'post',
            data: {
                id: window.roleId,
                name: roleName
            },
            dateType: 'json',
            success: function (response) {
                if (response.result === 'SUCCESS') {
                    layer.msg('操作成功!');
                    // 关闭模态框
                    $('#editModal').modal('hide');
                    // 重新加载分页
                    generatePage();
                } else if (response.result === 'FAILED') {
                    layer.msg('操作失败! ' + response.message);
                }
            },
            error: function (response) {
                layer.msg(response.status + ' ' + response.statusText)
            }
        })
    });

    // showConfirmModal([{roleName:'role1'},{roleName:'role2'}]);

    // 声明专门的函数显示确认模态框
    function showConfirmModal(roleArray) {
        // 打开模态框
        $('#confirmModal').modal('show');
        // 清除旧数据
        $('#roleNameDiv').empty();
        // 在全局变量范围创建数组用来存放角色 id
        window.roleIdArray = [];
        // 遍历 roleArray 数组
        for (var i = 0; i < roleArray.length; i++) {
            var role = roleArray[i];
            var roleName = role.roleName;
            $('#roleNameDiv').append(roleName + '</br>')

            var roleId = role.roleId;
            // 调用数组对象的 push 方法存入新元素
            window.roleIdArray.push(roleId);
        }
    }

    $('#removeRoleBtn').click(function () {
        // 从全局本来那个范围获取 roleIdArray, 转换 JSON 字符串
        var requestBody = JSON.stringify(window.roleIdArray);
        $.ajax({
            url: 'role/remove/by/role/id/array.json',
            type: 'post',
            data: requestBody,
            contentType: 'application/json;charset=UTF-8',
            dataType: 'json',
            success: function (response) {
                if (response.result === 'SUCCESS') {
                    layer.msg('操作成功!');
                    // 关闭模态框
                    $('#confirmModal').modal('hide');
                    // 重新加载分页
                    generatePage();
                } else if (response.result === 'FAILED') {
                    layer.msg('操作失败! ' + response.message);
                }
            },
            error: function (response) {
                layer.msg(response.status + ' ' + response.statusText)
            }
        })
    })

    // 单条删除
    $('#rolePageBody').on('click', '.removeBtn', function () {
        // 从当前按钮出发获取角色名称
        var roleName = $(this).parent().prev().text();
        // 创建 role 对象传入数组
        var roleArray = [{
            roleId: this.id,
            roleName: roleName
        }]

        // 调用确认删除模态框函数
        showConfirmModal(roleArray);
    })

    // 给总的 checkbox 绑定单击响应函数
    $('#summaryBox').click(function () {
        // 获取当前多选框自身的状态
        var currentStatus = this.checked;

        // 调用当前多选框的状态设置其他多选框
        $('.itemBox').prop('checked', currentStatus);
    })

    // 全选和不全选的反向操作
    $('#rolePageBody').on('click', '.itemBox', function () {
        // 获取当前已经选中的 .itemBox 的数量
        var checkedBoxCount = $('.itemBox:checked').length;

        // 获取全部 .itemBox 的数量
        var totalBoxCount = $('.itemBox').length;

        // 使用二者的比较结果设置总的 checkbox
        $('#summaryBox').prop('checked', checkedBoxCount === totalBoxCount);
    })

    // 给批量删除按钮绑定单击响应函数
    $('#batchRemoveBtn').click(function () {
        // 创建一个数组用来存放后面获取到的角色对象
        var roleArray = [];

        // 遍历当前选中的多选框
        $('.itemBox:checked').each(function () {
            // 使用 this 引用当前遍历得到的多选框
            var roleId = this.id;

            // 遍历 DOM 操作获取角色名称
            var roleName = $(this).parent().next().text();

            roleArray.push({
                roleId: roleId,
                roleName: roleName
            })
        })

        // 检查 roleArray 的长度是否为 0
        if (roleArray.length === 0) {
            layer.msg('请至少选择一条数据!')
            return false;
        }

        // 调用确认删除模态框函数
        showConfirmModal(roleArray);
    })

    // 给分配权限按钮绑定点击响应函数
    $("#rolePageBody").on("click", ".checkBtn", function () {
        // 把当前角色 id 存入全局变量
        window.roleId = this.id;
        // 打开模态框
        $("#assignModal").modal("show");
        // 在模态框中装载树形结构
        fillAuthTree();
    });

    // 给分配权限模态框中的 "分配" 按钮绑定点击响应函数
    $("#assignBtn").click(function () {
        // 存储被勾选的节点
        var authIdArray = [];

        // 获取 zTressObj 对象
        var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");

        // 获取被勾选的节点
        var checkedNodes = zTreeObj.getCheckedNodes();
        checkedNodes.forEach(checkNode => {
            var authId = checkNode.id;
            authIdArray.push(authId);
        })

        var data = {
            authIdArray: authIdArray,
            roleId: [window.roleId],
        }

        // 发送请求执行分配
        $.ajax({
            url: 'assign/do/role/assign/auth.json',
            data: JSON.stringify(data),
            type: 'post',
            contentType: 'application/json;charset=UTF-8',
            dataType: 'json',
            success: function (response) {
                if (response.result === 'SUCCESS') {
                    layer.msg('操作成功!');
                } else if (response.result === 'FAILED') {
                    layer.msg('操作失败! ' + response.message);
                }
            },
            error: function (response) {
                layer.msg(response.status + ' ' + response.statusText)
            }
        });

        $("#assignModal").modal("hide");
    });
})

// 声明专门的函数用于在分配 Auth 的模态框中显示 Auth 的树形结构数据
function fillAuthTree() {
    // 发送 Ajax 请求查询 Auth 数据
    var ajaxReturn = $.ajax({
        url: 'assign/get/all/auth.json',
        type: 'get',
        dataType: 'json',
        async: false,
        success: function (response) {
            if (response.result === 'SUCCESS') {
            } else if (response.result === 'FAILED') {
                layer.msg('操作失败! ' + response.message);
            }
        },
        error: function (response) {
            layer.msg(response.status + ' ' + response.statusText)
        }
    })

    // 判断响应状态码
    if (ajaxReturn.status !== 200) {
        layer.msg("请求处理出错!响应状态码是: " + ajaxReturn.status + "声明是: " + ajaxReturn.statusText);
        return false;
    }

    // 从响应结果中获取 Auth 的 JSON 数据
    var authList = ajaxReturn.responseJSON.data;
    var setting = {
        data: {
            simpleData: {
                // zTree 使用简单 JSON {id,pId,name} 数据自动组装成 zTree 的数据
                enable: true,
                // 父节点
                pIdKey: "categoryId"
            },
            key: {
                // 节点名称
                name: "title"
            }
        },
        check: {
            // 复选框
            enable: true
        }
    }

    // 生成树形结构
    // <ul id="authTreeDemo" class="ztree"></ul>
    $.fn.zTree.init($("#authTreeDemo"), setting, authList);

    // 展开节点
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
    zTreeObj.expandAll(true);

    // 查询已分配的 Auth 的 id 组成的数组
    var ajaxReturn2 = $.ajax({
        url: 'assign/get/assigned/auth/id/by/role/id.json',
        type: 'get',
        data: {
            roleId: window.roleId
        },
        dataType: 'json',
        async: false,
    })

    // 判断响应状态码
    if (ajaxReturn2.status !== 200) {
        layer.msg("请求处理出错!响应状态码是: " + ajaxReturn.status + "声明是: " + ajaxReturn.statusText);
        return false;
    }

    // 获取响应数据
    var authIdArray = ajaxReturn2.responseJSON.data;

    // 根据 authIdArray 把树形结构中对应的节点勾选择上
    authIdArray.forEach(authId => {
        // 根据 ID 查询树形结构中对应的节点
        var treeNode = zTreeObj.getNodeByParam("id", authId);
        // 将 treeNode 设置为被勾选

        // 表示节点勾选
        var checked = true;
        // 表示不联动，避免勾选其他节点
        var checkTypeFlag = false;
        zTreeObj.checkNode(treeNode, checked, checkTypeFlag);
    });
}

// 执行分页, 生成页面效果, 如何时候调用这个函数都会重新加载页面
function generatePage() {
    // 1. 获取分页数据
    var pageInfo = getPageInfoRemote();
    // 2. 填充表格
    fillTableBody(pageInfo);
    // 3. 生成分页页码导航条
    generateNavigator(pageInfo);
}

// 获取分页数据
function getPageInfoRemote() {
    var result = '';
    $.ajax({
        url: 'role/get/page/info.json',
        // type: 'post',
        type: 'get',
        data: {
            pageNum: window.pageNum,
            pageSize: window.pageSize,
            keyword: window.keyword
        },
        dateType: 'json',
        async: false,
        success: function (response) {
            if (response.result === 'SUCCESS') {
                result = response.data;
            } else if (response.result === 'FAILED') {
                layer.msg(response.message);
            }
        },
        error: function (response) {
            layer.msg("请求失败! 状态码=" + response.status + " 声明信息=" + response.statusText);
        }
    });

    return result;
}

// 填充表格
function fillTableBody(pageInfo) {
    // 清除 tbody 中的就数据
    $("#rolePageBody").empty();
    // 判 pageInfo 是否有效
    if (pageInfo === null || pageInfo === undefined || pageInfo.list === null || pageInfo.list.length === 0) {
        $("#rolePageBody").append("<tr><td align='center' colspan='4'>抱歉! 没有查询到您搜索的数据!</td></tr>")
        return null;
    }

    // 填充 tbody
    for (var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        $("#rolePageBody").append(`
<tr>
    <td>${i + 1}</td>
    <td><input id="${roleId}" type="checkbox" class="itemBox"/></td>
    <td>${roleName}</td>
    <td>
         <button id="${roleId}" type="button" class="checkBtn btn btn-success btn-xs"><i
                 class=" glyphicon glyphicon-check"></i></button>
         <button id="${roleId}" type="button" class="btn btn-primary btn-xs pencilBtn"><i
                 class=" glyphicon glyphicon-pencil"></i></button>
         <button id="${roleId}" type="button" class="btn btn-danger btn-xs removeBtn"><i
                 class=" glyphicon glyphicon-remove"></i></button>
    </td>
</tr>
`)
    }

}

// 生成分页页码导航条
function generateNavigator(pageInfo) {
    // 获取总记录数
    var totalRecord = pageInfo.total;
    // 声明一个 JSON 对象存储 Pagination 的配置
    const properties = {
        // 边缘页数
        num_edge_entries: 3,
        // 主体页数
        num_display_entries: 5,
        // 监听"翻页"事件, 跳转页面时触发的函数
        callback: pageSelectCallback,
        // 每页显示的数据的数量
        items_per_page: pageInfo.pageSize,
        // Pagination 内部使用 pageIndex 来管理页码, pageIndex 从 0 开始, pageNum 从 1 开始, 所以要减1
        current_page: pageInfo.pageNum - 1,
        // 上一页按钮上显示的文本
        prev_text: "上一页",
        // 下一页按钮上显示的文本
        next_text: "下一页"
    }

    // 生成分页
    $("#Pagination").pagination(totalRecord, properties);
}

// 翻页时的回调函数
function pageSelectCallback(pageIndex, JQuery) {
    // 修改 window 对象的 pageNum 属性
    window.pageNum = pageIndex + 1;

    // 调用分页函数
    generatePage();

    // 取消超链接的默认行为
    return false;
}

// 声明专门的函数显示确认模态框
function showConfirmModal(roleArray) {
    // 打开模态框
    $('#confirmModal').modal('show');
    // 清除旧数据
    $('#roleNameDiv').empty();
    // 在全局变量范围创建数组用来存放角色 id
    window.roleIdArray = [];
    // 遍历 roleArray 数组
    for (var i = 0; i < roleArray.length; i++) {
        var role = roleArray[i];
        var roleName = role.roleName;
        $('#roleNameDiv').append(roleName + '</br>')

        var roleId = role.roleId;
        // 调用数组对象的 push 方法存入新元素
        window.roleIdArray.push(roleId);
    }
}