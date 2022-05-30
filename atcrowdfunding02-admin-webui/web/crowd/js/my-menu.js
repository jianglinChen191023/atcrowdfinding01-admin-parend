$(function () {
    // 调用专门封装好的函数初始化树形结构
    generateTree();

    // 给 "X" 按钮绑定单击响应函数
    $('#treeDemo').on('click', '.removeBtn', function () {
        window.id = this.id;
        // 获取 zTreeObj 对象
        var zTreeObj = $.fn.zTree.getZTreeObj('treeDemo');
        var currentNode = zTreeObj.getNodeByParam('id', this.id);
        $('#removeNodeSpan').html(`【<i class="${currentNode.icon}"></i> ${currentNode.name}】`);
        $('#menuConfirmModal').modal('show');
    });

    // 给确认模态框中的 OK 按钮绑定单击响应函数
    $('#confirmBtn').click(function () {
        // 发送 ajax 请求
        $.ajax({
            url: 'menu/delete.json',
            type: 'delete',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(window.id),
            dataType: 'json',
            success: function (response) {
                var result = response.result;
                if (result === 'SUCCESS') {
                    layer.msg('操作成功!');
                    // 刷新树形结构
                    generateTree();
                } else if (result === 'FAILED') {
                    layer.msg('操作失败!' + response.message);
                }
            },
            error: function (response) {
                layer.msg(' 请求失败! ' + response.status + ' ' + response.statusText);
            }
        });

        // 关闭模态框
        $('#menuConfirmModal').modal('hide');
    });

    // 给修改子节点按钮绑定单击响应函数
    $('#treeDemo').on('click', '.editBtn', function () {
        // 将当前节点的 id 保存到全局变量
        window.id = this.id;

        // 获取 zTreeObj 对象
        var zTreeObj = $.fn.zTree.getZTreeObj('treeDemo');

        // 根据 id 属性查询节点对象
        // 用来搜索节点的属性名
        var key = 'id';
        // 用来搜索节点的属性值
        var value = window.id;
        var currentNode = zTreeObj.getNodeByParam(key, value);

        // 回显表单数据
        $('#menuEditModal [name=name]').val(currentNode.name);
        $('#menuEditModal [name=url]').val(currentNode.url);
        // radio 回显的本质是把 value 属性 currentNode.icon 一致的 radio 选中
        // 回显 radio 可以这样理解: 被选中的 radio 的 value 属性可以组成一个数组, 然后再用这个数组设置回 radio, 就能够把对应的值选中
        $('#menuEditModal [name=icon]').val([currentNode.icon]);

        // 打开模态框
        $('#menuEditModal').modal('show');
    });

    // 给更新模态框中的更新按钮绑定点击响应函数
    $('#menuEditBtn').click(function () {
        // 收集表单数据
        var name = $('#menuEditModal [name=name]').val();
        var url = $('#menuEditModal [name=url]').val();
        var icon = $('#menuEditModal [name=icon]:checked').val();

        // 发送 ajax 请求
        $.ajax({
            url: 'menu/update.json',
            type: 'put',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({
                id: window.id,
                name: name,
                url: url,
                icon: icon
            }),
            dataType: 'json',
            success: function (response) {
                var result = response.result;
                if (result === 'SUCCESS') {
                    layer.msg('操作成功!');
                    // 刷新树形结构
                    generateTree();
                } else if (result === 'FAILED') {
                    layer.msg('操作失败!' + response.message);
                }
            },
            error: function (response) {
                layer.msg(' 请求失败! ' + response.status + ' ' + response.statusText);
            }
        });

        // 关闭模态框
        $('#menuEditModal').modal('hide');
    });

    // 给添加子节点按钮绑定单击响应函数
    $('#treeDemo').on('click', '.addBtn', function () {
        // 将当前节点的 id, 作为新节点的 pid 保存到全局变量
        window.pid = this.id;

        // 打开模态框
        $('#menuAddModal').modal('show');
    });

    // 给添加子节点的新增模态框的保存按钮 绑定单击响应函数
    $('#menuSaveBtn').click(function () {
        // 收集表单项中用户输入的数据
        var name = $.trim($('#menuAddModal [name=name]').val());
        var url = $.trim($('#menuAddModal [name=url]').val());
        // 单选按钮要定位到'被选中的那一个'
        var icon = $.trim($('#menuAddModal [name=icon]:checked').val());

        // 发送 ajax 请求
        $.ajax({
            url: 'menu/save.json',
            type: 'post',
            data: {
                pid: window.pid,
                name: name,
                url: url,
                icon: icon
            },
            dataType: 'json',
            success: function (response) {
                var result = response.result;
                if (result === 'SUCCESS') {
                    layer.msg('操作成功!');
                    // 刷新树形结构
                    generateTree();
                } else if (result === 'FAILED') {
                    layer.msg('操作失败!' + response.message);
                }
            },
            error: function (response) {
                layer.msg(response.status + ' ' + response.statusText);
            }
        });

        // 关闭模态框
        $('#menuAddModal').modal('hide');

        // 清空表单
        // jQuery 对象调用 click() 函数, 里面不传任何参数, 相当于用户点击了一次
        $('#menuResetBtn').click();
    });

})

// 生成树形结构的函数
function generateTree() {
    $.ajax({
        url: 'menu/get/whole/tree.json',
        type: 'get',
        dataType: 'json',
        success: function (response) {
            var result = response.result;
            if (result === 'SUCCESS') {
                // 1. 创建 JSON 对象用于存储对 zTree 所做的设置
                var setting = {
                    view: {
                        addDiyDom: myAddDiyDom,
                        addHoverDom: myAddHoverDom,
                        removeHoverDom: myRemoveHoverDom
                    },
                    data: {
                        key: {
                            url: 'no'
                        }
                    }
                };
                // 2. 准备生成树形结构的 JSON 数据
                var data = response.data;
                // 3. 初始化树形结构
                $.fn.zTree.init($('#treeDemo'), setting, data);
            } else if (result === 'FAILED') {
                layer.msg('操作失败! ' + response.message);
            }
        },
        error: function (response) {
            layer.msg(response.status + ' ' + response.statusText)
        }
    })
}

// 修改默认的图标
function myAddDiyDom(treeId, treeNode) {
    // treeId 是整个树形结构附着的 ul 标签的 id
    // console.log("treeId:" + treeId);

    // 当前树形节点的全部的数据, 包括从后端查询得到的 Menu 对象的全部属性
    // console.log(treeNode)

    // zTree 生成 id 的规则
    // 例子: treeDome_7_ico
    // 解析：ul 标签的 id_当前节点的序号_功能

    // 提示: ul 标签的id_当前节点的序号" - 这个部分可以通过 treeNode 的 tId 属性得到
    // 根据 id 的生成规则拼接出来 span 标签的 id
    var spanId = treeNode.tId + '_ico';

    // 根据控制图标的 span 标签的 id 找到整个 span 标签
    // 删除旧的 class
    // 添加新的 class
    $('#' + spanId)
        .removeClass()
        .addClass(treeNode.icon);
}

// 在鼠标移入节点范围时添加按钮组
function myAddHoverDom(treeId, treeNode) {
    // 按钮组的标签结构: <span><a><i></i></a><a><i></i></a></span>
    // 按钮组出现的位置: 节点中 treeDDemo_n_a 超链接的后面

    // 为了在需要移除按钮组的时候能够精确定位按钮组所在 span, 需要给 spna 设置有规律的 id
    var btnGroupId = treeNode.tId + '_btnGrp';

    // 判断一下以前是否已经添加了按钮组
    if ($('#' + btnGroupId).length > 0) {
        return false;
    }

    // 执行在超链接后面附加 span 元素的操作
    // 准备各个按钮的 HTML 标签
    var addBtn = `<a id="${treeNode.id}" class="addBtn btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" title="添加子节点">&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>`;
    var removeBtn = `<a id="${treeNode.id}" class="removeBtn btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" title="删除节点">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>`;
    var editBtn = `<a id="${treeNode.id}" class="editBtn btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" title="修改节点">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>`;

    // 获取当前就带你的级别数据
    var level = treeNode.level;

    // 声明变量存储拼装号的按钮代码
    var btnHtml = '';

    if (level === 0) {
        // 根节点: 添加子节点
        btnHtml = addBtn;
    } else if (level === 1) {
        // 分支节点: 添加子节点 修改 <没有子节点可以删除 有子节点不能删除>
        btnHtml = addBtn + ' ' + editBtn;

        // 获取当前节点的子节点数量
        var length = treeNode.children.length;

        (length === 0) ? btnHtml += ' ' + removeBtn : '';
    } else if (level === 2) {
        // 叶子节点: 修改 删除
        btnHtml = editBtn + ' ' + removeBtn;
    }

    // 找到附着按钮组的超链接
    var anchorId = treeNode.tId + "_a";
    $('#' + anchorId).after(`<span id="${btnGroupId}">${btnHtml}</span>`);
}

// 在鼠标离开节点范围时删除按钮组
function myRemoveHoverDom(treeId, treeNode) {
    // 拼接按钮组的 id
    var btnGroupId = treeNode.tId + '_btnGrp';
    // 移除对应的元素
    $('#' + btnGroupId).remove();
}