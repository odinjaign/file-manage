layui.use(['layer'], function() {
    var layer = layui.layer;
    //layer.msg("hello");
    var $ = layui.$;

    function pathHideTmpHtml(node) {
        return '\
					<blockquote class="layui-elem-quote layui-quote-nm">\
						<div class="layui-row layui-col-space5" style="text-align: center;">\
							<div class="layui-col-md6" style="text-align: left;">\
								' +
            node.path +
            '\
                </div>\
                <div class="layui-col-md2">\
                    <span class="layui-badge layui-bg-warm">' + node.type +
            '</span>\
                </div>\
                <div class="layui-col-md2">\
                    <span class="status layui-badge layui-bg-green">路径</span>\
                </div>\
                <div class="layui-col-md2">\
                    <button type="button" class="remove-btn layui-btn layui-btn-danger layui-btn-xs" data-node-id="' +
            node.id + '">移除记录</button>\
							</div>\
						</div>\
					</blockquote>\
					';
    }
    function regHideTmpHtml(node) {
        return '\
					<blockquote class="layui-elem-quote layui-quote-nm">\
						<div class="layui-row layui-col-space5" style="text-align: center;">\
							<div class="layui-col-md6" style="text-align: left;">\
								' +
            node.reg +
            '\
                </div>\
                <div class="layui-col-md2">\
                    <span class="layui-badge layui-bg-warm">' + node.type +
            '</span>\
                </div>\
                <div class="layui-col-md2">\
                    <span class="status layui-badge layui-bg-blue">正则</span>\
                </div>\
                <div class="layui-col-md2">\
                    <button type="button" class="remove-btn layui-btn layui-btn-danger layui-btn-xs" data-node-id="' +
            node.id + '">移除记录</button>\
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
            $.post("/sys/config/get", {
                key: "filehide"
            }, function(status) {
                if (!status) {
                    layer.msg("文件隐藏未启用", function() {
                        window.location = "index.html";
                    })
                } else {

                    $.post('/hide/manage/list', function(rel) {
                        console.log(rel);
                        if (rel.code === 0) {
                            //渲染
                            rel.pathdata.forEach(function(val, inx) {
                                $("#hide-box").append(pathHideTmpHtml(val))
                            });
                            rel.regdata.forEach(function(val, inx) {
                                $("#hide-box").append(regHideTmpHtml(val))
                            });
                            $(".remove-btn").click(function() {
                                var _id = $(this).attr("data-node-id");
                                $.post('/hide/manage/del', {
                                    id: _id
                                }, function(rel) {
                                    console.log(rel);
                                    if (rel.code === 0) {
                                        //删除成功
                                        location.reload();
                                    }
                                })
                            })
                        } else {
                            layer.msg(rel.msg);
                        }
                    });
                    $("#add-path-btn").click(function() {
                        layer.open({
                            title: '添加',
                            content: '<input type="text" name="path" autocomplete="off" placeholder="文件夹路径" class="layui-input">',
                            yes: function(index, layero) {
                                var _path = $(layero.find('.layui-input')).val();
                                console.log(_path);
                                $.post('/hide/manage/add', {
                                    content: _path,
                                    type: 1
                                }, function(rel) {
                                    layer.msg(rel.msg);
                                    if (rel.code === 0) {
                                        location.reload();
                                    }
                                })
                                layer.close(index);
                            }
                        })
                    });
                    $("#add-reg-btn").click(function() {
                        layer.open({
                            title: '添加正则',
                            content: '<input type="text" name="path" autocomplete="off" placeholder="文件夹路径" class="layui-input">',
                            yes: function(index, layero) {
                                var _reg = $(layero.find('.layui-input')).val();
                                console.log(_reg);
                                $.post('/hide/manage/add', {
                                    content: _reg,
                                    type: 2
                                }, function(rel) {
                                    layer.msg(rel.msg);
                                    if (rel.code === 0) {
                                        location.reload();
                                    }
                                })
                                layer.close(index);
                            }
                        })
                    });
                }
            })
        }
    })


})