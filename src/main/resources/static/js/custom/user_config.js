layui.use(['element', 'form', 'layer', 'table'], function() {
    var form = layui.form;
    var element = layui.element;
    var $ = layui.$;
    var layer = layui.layer;
    var table = layui.table;

    function userInfoNodeTmp(key, value, isEdit, editkey) {
        return '\
					<br>\
					<div class="layui-row">\
						<div class="layui-col-md2" style="text-align: center;">\
							' +
            key + '\
						</div>\
						<div class="layui-col-md2">\
							' + value +
            '\
            </div>\
            <div class="layui-col-md7">\
                <button data-editkey="' + editkey +
            '" data-editname="' + key + '" data-oldvalue="' + value + '" class="edit-btn layui-btn layui-btn-sm' + (!isEdit ?
                ' layui-btn-disabled' : '') +
            '">修改</button>\
            </div>\
        </div>\
        ';
    }

    //用户未登录的跳转
    $.post("/user/config/islogin", function(rel) {
        if (rel.code === -1) {
            layer.msg(rel.msg, function() {
                window.location = "login.html";
            })
        } else if (rel.code === 1) {
            layer.msg(rel.msg, function() {
                window.location = "sysconfig.html";
            })
        } else if (rel.code === 0) {
            $("#logout-btn").click(function() {
                $.post('/logout', function(rel) {
                    if (rel) {
                        window.location = "login.html";
                    }
                })
            })
            //获取文件夹密码状态
            $.post("/user/config/folder/password/status", function(data) {
                form.val('folder-password-manage', {
                    'status': data
                });
            })
            $("#change-password-status").click(function() {

                var status = form.val('folder-password-manage').status;
                $.post("/user/config/folder/password/changestatus", {
                    status: status
                }, function(rel) {
                    if (rel.code === 0) {
                        layer.msg(rel.msg);
                    }
                })
            });
            $("#get-password-status").click(function() {
                layer.msg('刷新当前状态');
                $.post("/user/config/folder/password/status", function(data) {
                    form.val('folder-password-manage', {
                        'status': data
                    });
                })
            })
            $("#to-passworddb-manage").click(function() {
                var status = form.val('folder-password-manage').status;
                console.log(status);
                if (status == 2) {
                    window.location = "folderdbpassword.html";
                } else {
                    layer.msg("请先将文件夹密码配置方式修改为数据库。")
                }

            })
            $.post('/user/now', function(rel) {
                console.log(rel);
                if (rel.code === 0) {
                    $("#userInfo").append(userInfoNodeTmp("用户名", rel.info.username, false, 'username'));
                    $("#userInfo").append(userInfoNodeTmp("昵称", rel.info.nickname, true, 'nickname'));
                    $("#userInfo").append(userInfoNodeTmp("性别", rel.info.gender, true, 'gender'));
                    $("#userInfo").append(userInfoNodeTmp("邮箱", rel.info.email, true, 'email'));
                    $("#userInfo").append(userInfoNodeTmp("用户类型", rel.info.usertype, false, 'usertype'));
                    $("#userInfo").append(userInfoNodeTmp("密码", '禁止读取', true, 'password'));
                    $(".edit-btn").click(function() {
                        if ($(this).hasClass("layui-btn-disabled")) {
                            console.log("按钮禁用！！！");
                            return;
                        }
                        var _key = $(this).attr("data-editkey");
                        var _editname = $(this).attr("data-editname");
                        var _oldvalue = $(this).attr("data-oldvalue");
                        if (_key !== 'password') {
                            layer.open({
                                title: '修改' + _editname + ':',
                                content: '<input type="text" name="path" autocomplete="off" placeholder="' + _oldvalue +
                                    '" class="layui-input">',
                                yes: function(index, layero) {
                                    var _value = $(layero.find('.layui-input')).val();
                                    console.log(_value);
                                    $.post('/user/update', {
                                        key: _key,
                                        value: _value
                                    }, function(rel) {
                                        layer.msg(rel.msg);
                                        if (rel.code === 0) {
                                            location.reload();
                                        }
                                    })
                                    layer.close(index);
                                }
                            })
                        } else {
                            layer.open({
                                title: '修改' + _editname + ':',
                                content: '<input type="password" name="oldpassword" autocomplete="off" placeholder="原密码" class="layui-input"><br><input type="password" name="newpassword" autocomplete="off" placeholder="新密码" class="layui-input"><br><input type="password" name="newpassword2" autocomplete="off" placeholder="重复输入新密码" class="layui-input">',
                                yes: function(index, layero) {
                                    var _oldpasswd = $(layero.find('.layui-input[name=oldpassword]')).val();
                                    var _newpasswd = $(layero.find('.layui-input[name=newpassword]')).val();
                                    var _newpasswd2 = $(layero.find('.layui-input[name=newpassword2]')).val();
                                    if (_newpasswd !== _newpasswd2) {
                                        layer.msg("密码输入不一致！");
                                    } else {
                                        $.post('/user/updatepasswd', {
                                            oldpassword: _oldpasswd,
                                            newpassword: _newpasswd
                                        }, function(rel) {
                                            layer.msg(rel.msg);
                                        })
                                    }
                                    layer.close(index);
                                }
                            })
                        }

                    })
                } else {
                    $("#userInfo").html(rel.msg)
                }
            })
            table.render({
                elem: '#log-table',
                url: '/opt/log/list',
                method: 'post',
                cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
                ,
                cols: [
                    [{
                        field: 'index',
                        title: '索引',
                        sort: true
                    }, {
                        field: 'type',
                        title: '类型'
                    } //width 支持：数字、百分比和不填写。你还可以通过 minWidth 参数局部定义当前单元格的最小宽度，layui 2.2.1 新增
                        , {
                        field: 'src',
                        title: '源路径'
                    }, {
                        field: 'dst',
                        title: '目标路径'
                    }, {
                        field: 'note',
                        title: '记录'
                    }, {
                        field: 'time',
                        title: '时间'
                    } //单元格内容水平居中
                    ]
                ],
                initSort: {
                    field: 'index' //排序字段，对应 cols 设定的各字段名
                    ,
                    type: 'asc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
                },
                done: function(rel) {
                    if (rel.code !== 0) {
                        layer.msg(rel.msg);
                    }
                }
            });
        }
    })


});