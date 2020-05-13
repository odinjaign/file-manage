layui.use(['layer', 'element'], function () {
    var layer = layui.layer;
    var element = layui.element;
    var $ = layui.$;

    // 模板节点函数
    function taskNodeTmp(node) {

        var _taskid = node.taskid;
        var _taskname = node.taskname;
        var _tasktype = '未知';
        var _optsrc = '';
        var _optdst = node.target;
        var _status = node.enabled;

        if (node.tasktype === 1) {
            _tasktype = '复制';
        } else if (node.tasktype === 2) {
            _tasktype = '移动';
        } else if (node.tasktype === 3) {
            _tasktype = '重命名';
        } else if (node.tasktype === 4) {
            _tasktype = '删除';
        }
        _tasktype = '<span class="layui-badge layui-bg-blue">' + _tasktype + '</span>';


        _optsrc = '<span class="layui-badge layui-bg-orange">' + node.folder + '</span>' +
            '<span class="layui-badge-rim">' + node.regfile + '</span>';


        return '\
					<blockquote class="layui-elem-quote layui-quote-nm">\
						<div class="layui-row layui-col-space5" style="text-align: center;">\
							<div class="layui-col-md1">\
								' + _taskid + '\
							</div>\
							<div class="layui-col-md2" style="text-align: left;">\
								' + _taskname + '\
							</div>\
							<div class="layui-col-md1">\
								' + _tasktype + '\
							</div>\
							<div class="layui-col-md1">\
								<span class="layui-badge layui-bg-' + (_status ? 'orange">' : 'gray">未') + '启用</span>\
							</div>\
							<div class="layui-col-md3">\
								' + _optsrc + '\
							</div>\
							<div class="layui-col-md2">\
								' + _optdst + '\
							</div>\
							<div class="layui-col-md2" >\
							<div class="layui-btn-group" data-node-taskid="' + _taskid + '" data-node-cron="' + node.cron + '" data-node-status="' + _status + '">\
                                <button type="button" class="info-cron-btn layui-btn layui-btn-primary layui-btn-xs">CRON</button>\
                                <button type="button" class="task-enable-btn layui-btn layui-btn-xs ' + (_status ? 'layui-hide' : '') + '">启动</button>\
                                <button type="button" class="task-disable-btn layui-btn layui-btn-xs ' + (_status ? '' : 'layui-hide') + '">停止</button>\
                                <button type="button" class="task-delete-btn layui-btn layui-btn-danger layui-btn-xs">删除</button>\
                            </div>\
							</div>\
						</div>\
					</blockquote>\
					';
    }

    function fucNodeTmp(name, value, key) {

        return '\
					<div class="layui-row" style="margin-top: 10px;">\
						<div class="layui-col-md2" style="text-align: center;">\
							' + name + '\
						</div>\
						<div class="layui-col-md2">\
							<span class="layui-badge layui-bg-' + (value ? 'orange">' : 'gray">未') + '启用</span>\
						</div>\
						<div class="layui-col-md7">\
							<button data-key="' + key + '" data-value="' + !value + '" class="modify-btn layui-btn layui-btn-xs">' + (value ? '禁用' : '启用') + '</button>\
						</div>\
					</div>\
					';

    }

    function userNodeTmp(node) {
        return '\
					<div class="layui-col-md4">\
						<blockquote class="layui-elem-quote">\
							<div class="layui-row">\
								<div class="layui-col-xs8">\
									<i class="layui-icon layui-icon-' + (node.gender === 1 ? '' : 'fe') + 'male"></i>\
									<span>' + node.nickname + '</span><br>\
									<i class="layui-icon layui-icon-username"></i>\
									<span>' + node.username + '</span>' + (node.nowuser ? '<span class="layui-badge-dot layui-bg-orange"></span>' : '') + '<br>\
									<i class="layui-icon layui-icon-email"></i>\
									<span>' + node.email + '</span>\
								</div>\
								<div class="layui-col-xs4" style="text-align: right;" data-username="' + node.username + '">\
									<button type="button" class="del-user-btn layui-btn layui-bg-red layui-btn-xs">删除用户</button>\
									<button type="button" class="reset-password-btn layui-btn layui-btn-xs">重置密码</button>\
								</div>\
							</div>\
						</blockquote>\
					</div>\
					';
    }

    function addTaskTmp(type) {
        return '\
					<div class="layui-form-item" style="margin-top: 20px;margin-left: 80px;">\
						<label class="layui-form-label">任务名</label>\
						<div class="layui-input-inline">\
							<input type="text" name="taskname" required lay-verify="required" placeholder="请输入要创建的任务名称" autocomplete="off" class="layui-input">\
						</div>\
					</div>\
                    <div class="layui-form-item" style="margin-top: 20px;margin-left: 80px;">\
						<label class="layui-form-label">CRON</label>\
						<div class="layui-input-inline">\
							<input type="text" name="cron" required lay-verify="required" placeholder="请输入CRON表达式" autocomplete="off" class="layui-input">\
						</div>\
					</div>\
					<div class="layui-form-item" style="margin-left: 80px;">\
						<label class="layui-form-label">目录</label>\
						<div class="layui-input-inline">\
							<input type="text" name="optfolder" required lay-verify="required" placeholder="请输入操作的目录" autocomplete="off" class="layui-input">\
						</div>\
					</div>\
					<div class="layui-form-item" style="margin-left: 80px;">\
						<label class="layui-form-label">' + (type === '3' ? '原文件名' : '文件正则') + '</label>\
						<div class="layui-input-inline">\
							<input type="text" name="optregfile" required lay-verify="required" placeholder="文件正则或者文件名" autocomplete="off" class="layui-input">\
						</div>\
					</div>\
					<div class="layui-form-item ' + (type === '4' ? 'layui-hide' : '') + '" style="margin-left: 80px;">\
                        <label class="layui-form-label">' + (type === '3' ? '新文件名' : '目标路径') + '</label>\
                        <div class="layui-input-inline">\
                            <input type="text" name="targetpath" required lay-verify="required" placeholder="目标路径或者新文件名" autocomplete="off" class="layui-input">\
                        </div>\
                    </div>\
					<div class="layui-form-item" style="margin-left: 80px;">\
						<div class="layui-input-block">\
							<button type="button" class="add-in-btn layui-btn layui-btn-normal">确认添加</button>\
						</div>\
					</div>\
					';
    }

    // 登出按钮
    $("#logout-btn").click(function () {
        $.post('/logout', function (rel) {
            if (rel) {
                window.location = "login.html";
            }
        })
    })

    //判断用户身份
    $.post("/user/config/islogin", function (rel) {
        if (rel.code === -1) {
            //用户未登录
            layer.msg(rel.msg, function () {
                window.location = "login.html";
            })
        }

        //管理用户身份标识
        var _isadmin = rel.code === 1;


        //配置信息管理
        $.post('/sys/config/list', function (rel) {
            if (rel.code === 0) {
                $("#func-box")
                    .append(fucNodeTmp("文件隐藏", rel.data.filehide, "filehide"))
                    .append(fucNodeTmp("分类浏览", rel.data.typeview, "typeview"))
                    .append(fucNodeTmp("文件收藏", rel.data.favorite, "favorite"))
                    .append(fucNodeTmp("操作日志", rel.data.optlogs, "optlogs"))
                    .append(fucNodeTmp("文件上传", rel.data.upload, "upload"))
                    .append(fucNodeTmp("系统隐藏文件", rel.data.syshide, "syshide"));
                //渲染操作
                if (_isadmin) {
                    $('.modify-btn').click(function () {
                        var _key = $(this).attr("data-key");
                        var _val = $(this).attr("data-value");

                        $.post('/sys/config/modify', {key: _key, value: _val}, function (rel) {
                            if (rel.code === 0) {
                                location.reload();
                            }
                        });
                    })
                } else {
                    $(".modify-btn").remove()
                }

            } else {
                layer.msg(rel.msg);
            }
        });
        //用户管理
        $.post('/user/list', function (rel) {
            if (rel.code === 0) {
                console.log(rel.data);
                rel.data.forEach(function (val) {
                    $('#userInfo').append(userNodeTmp(val))
                })

                //渲染操作
                if (_isadmin) {
                    $('.del-user-btn').click(function () {
                        var _username = $(this).parent().attr('data-username');
                        $.post('/user/del', {username: _username}, function (rel) {
                            layer.msg(rel.msg);
                        })
                    })
                    $('.reset-password-btn').click(function () {
                        var _username = $(this).parent().attr('data-username');
                        layer.open({
                            title: '重置密码:',
                            content: '<input type="text" name="path" autocomplete="off" placeholder="请输入密码" class="layui-input">',
                            yes: function (index, layero) {
                                var _password = $(layero.find('.layui-input')).val();
                                console.log(_username, _password);
                                $.post('/user/resetpasswd', {
                                    username: _username,
                                    password: _password
                                }, function (rel) {
                                    layer.msg(rel.msg);
                                })
                                layer.close(index);
                            }
                        })
                    })
                } else {
                    $('.del-user-btn').remove()
                    $('.reset-password-btn').remove()
                }


            }
        })

        //非管理用户没有任务管理
        if (_isadmin) {
            $.post('/task/list', function (rel) {
                if (rel.code === 0) {
                    rel.data.forEach(function (val) {
                        $("#task-list-box").append(taskNodeTmp(val))
                    })
                    //注册相关事件
                    $(".info-cron-btn").click(function () {
                        var _nodeCron = $(this).parent().attr("data-node-cron")
                        layer.tips(_nodeCron, this);
                    })
                    $(".task-enable-btn").click(function () {
                        var _taskid = $(this).parent().attr("data-node-taskid")
                        //layer.tips(_taskid, this);
                        $.post('/task/startTask', {taskid: _taskid}, function (rel) {
                            if (rel.code === 0) {
                                //location.reload()
                            } else {
                                layer.msg(rel.msg)
                            }
                        })
                    })
                    $(".task-disable-btn").click(function () {
                        var _taskid = $(this).parent().attr("data-node-taskid")
                        //layer.tips(_taskid, this);
                        $.post('/task/stopTask', {taskid: _taskid}, function (rel) {
                            if (rel.code === 0) {
                                location.reload()
                            } else {
                                layer.msg(rel.msg)
                            }
                        })
                    })
                    $(".task-delete-btn").click(function () {
                        var _taskid = $(this).parent().attr("data-node-taskid")
                        // todo
                        // layer.msg("功能暂时关闭");
                        // layer.tips(_taskid, this);
                        $.post('/task/delTask', {taskid: _taskid}, function (rel) {
                            if (rel.code === 0) {
                                location.reload()
                            } else {
                                layer.msg(rel.msg)
                            }
                        })
                    })
                }
            })
        } else {
            $("#task-manage-div").remove()
        }

        //添加用户按钮
        $("#add-user-btn").click(function () {
            layer.msg("请前往注册页面注册用户", function () {
                window.location = "register.html";
            })
        })
        //添加任务按钮
        $(".add-task-btn").click(function () {
            var _type = $(this).attr("data-task-type")
            console.log(_type)
            var _typename
            if (_type === '1') {
                _typename = "复制";
            } else if (_type === '2') {
                _typename = "移动";
            } else if (_type === '3') {
                _typename = "重命名";
            } else if (_type === '4') {
                _typename = "删除";
            }

            //todo 添加任务
            layer.open({
                type: 1,
                title: '添加' + _typename + '任务',
                closeBtn: 0,
                shadeClose: true,
                area: ['500px', (_type === '4' ? '350px' : '400px')],
                content: addTaskTmp(_type),
                success: function (layero, index) {
                    $('.add-in-btn').click(function () {
                        var _taskname = layero.find(" input[ name='taskname' ] ").val();
                        var _optsrc = layero.find(" input[ name='optfolder' ] ").val();
                        var _optregfile = layero.find(" input[ name='optregfile' ] ").val();
                        var _cron = layero.find(" input[ name='cron' ] ").val();
                        var _targetpath = layero.find(" input[ name='targetpath' ] ").val();

                        if (_taskname === ''
                            || _optsrc === ''
                            || _optregfile === ''
                            || _cron === ''
                        ) {
                            layer.msg("任务名，CRON表达式，操作路径，操作文件不可为空")
                            return
                        }

                        var _reqdata = {
                            taskname: _taskname,
                            cron: _cron,
                            folder: _optsrc,
                            regfile: _optregfile,
                            opt: _type,
                            targetpath: _targetpath
                        }
                        console.log(_reqdata);
                        //发送ajax请求
                        //添加收藏
                        $.post('/task/addTask', _reqdata, function (rel) {
                            // console.log(rel)
                            if (rel.code === 0) {
                                location.reload()
                            } else {
                                layer.msg(rel.msg)
                            }
                        })

                        layer.close(index);
                    })
                }
            })


        })
        //关于CRON的使用方式
        $("#get-cron-info").click(function () {
            $.post("/file/view/text/md", {path: "file/cron.md"}, function (rel) {
                if (rel.code === 1) {
                    layer.open({
                        type: 1,
                        title: "CRON表达式的使用方法",
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['1100px', '600px'],
                        content: rel.data
                    });
                } else {
                    layer.msg(rel.msg)
                }
            })
        })

    })

});