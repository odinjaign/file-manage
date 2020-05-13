layui.use(['form', 'element'], function() {
    var form = layui.form;
    var element = layui.element;
    var $ = layui.$;

    function nodeHtmlTmp(node) {
        var _status = '';
        var _status_color = '';
        var _btn_html = '';
        if (node.status === 1) {
            _status = '启用';
            _status_color = 'green';
            _btn_html = '禁用';
        } else if (node.status === 2) {
            _status = '禁用';
            _status_color = 'warm';
            _btn_html = '启用';
        }
        return '\
					<blockquote class="layui-elem-quote layui-quote-nm">\
						<div class="layui-row layui-col-space5" style="text-align: center;">\
							<div class="layui-col-md6" style="text-align: left;" >\
								' +
            node.path + '\
							</div>\
							<div class="layui-col-md2 folder-password" data-password="' + node.password +
            '">\
                    ******\
                </div>\
                <div class="layui-col-md2">\
                    <span class="status layui-badge layui-bg-' +
            _status_color + '">' + _status + '</span>\
							</div>\
							<div class="layui-col-md2" data-path="' + node
                .path +
            '">\
                    <button type="button" class="change-status play-btn layui-btn layui-btn-primary layui-btn-xs">' +
            _btn_html +
            '</button>\
                    <button type="button" class="modify-btn layui-btn layui-btn-xs">修改</button>\
                    <button type="button" class="delete-btn layui-btn layui-btn-danger layui-btn-xs">删除</button>\
                </div>\
            </div>\
        </blockquote>\
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
            //用户正常登录
            $.post('/fodler/db/password/list', function(rel) {
                if (rel.code === 0) {
                    rel.data.forEach(function(val, inx) {
                        $("#record-box").append(nodeHtmlTmp(val))
                    });
                    //注册事件
                    $(".folder-password").click(function() {
                        var passwd = $(this).attr("data-password");
                        layer.tips(passwd, this, {
                            tips: [1, '#3595CC'],
                            time: 4000
                        });
                    });
                    $(".change-status").click(function() {
                        var btn = $(this);
                        var sts = $(this).parent().parent().find(".status");
                        var path = $(this).parent().attr("data-path");

                        function disable() {
                            sts.removeClass('layui-bg-green');
                            sts.addClass('layui-bg-warm');
                            sts.html('禁用');
                            btn.html('启用');
                        }

                        function enable() {
                            sts.removeClass('layui-bg-warm');
                            sts.addClass('layui-bg-green');
                            sts.html('启用');
                            btn.html('禁用');
                        }
                        var _status_code = 0;
                        if (btn.html() === '启用') {
                            _status_code = 1;
                        } else if (btn.html() === '禁用') {
                            _status_code = 2
                        }
                        //修改状态
                        $.post('/fodler/db/password/change', {
                            path: path,
                            code: _status_code
                        }, function(rel) {
                            console.log(rel);
                            if (rel.code === 0) {
                                if (_status_code === 1) {
                                    enable();
                                } else if (_status_code === 2) {
                                    disable();
                                }
                            }
                        })

                        console.log(path, _status_code);
                    });
                    $(".modify-btn").click(function() {
                        var path = $(this).parent().attr("data-path");
                        layer.open({
                            title: '修改密码',
                            content: '<input type="password" name="password" lay-verify="title" autocomplete="off" placeholder="文件夹密码" class="layui-input">',
                            yes: function(index, layero) {
                                var _path = path;
                                var _password = $(layero.find('.layui-input[name=password]')).val();
                                $.post('/fodler/db/password/modify', {
                                    path: _path,
                                    password: _password
                                }, function(rel) {
                                    layer.msg(rel.msg);
                                    if (rel.code === 0) {
                                        location.reload();
                                    }
                                })
                                layer.close(index);
                            }
                        })
                    })
                    $(".delete-btn").click(function() {
                        var path = $(this).parent().attr("data-path");
                        $.post('/fodler/db/password/delete', {
                            path: path
                        }, function(rel) {
                            layer.msg(rel.msg);
                            if (rel.code === 0) {
                                location.reload();
                            }
                        })
                    })
                }
            })
            $("#add-btn").click(function() {
                layer.open({
                    title: '添加',
                    content: '<input type="text" name="folder" lay-verify="title" autocomplete="off" placeholder="文件夹路径" class="layui-input"><br><input type="password" name="password" lay-verify="title" autocomplete="off" placeholder="文件夹密码" class="layui-input">',
                    yes: function(index, layero) {
                        var _folder = $(layero.find('.layui-input[name=folder]')).val();
                        var _password = $(layero.find('.layui-input[name=password]')).val();
                        console.log(_folder, _password);
                        $.post('/fodler/db/password/add', {
                            folder: _folder,
                            password: _password
                        }, function(rel) {
                            layer.msg(rel.msg);
                        })
                        layer.close(index);
                    }
                })
            })

        }
    })

});