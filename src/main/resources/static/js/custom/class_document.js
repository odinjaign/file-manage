layui.use(['form', 'element', 'layer'], function () {
    var form = layui.form;
    var element = layui.element;
    var layer = layui.layer;
    var $ = layui.$;

    function docmentNodeHtml(node) {
        var type_class = '';
        if (node.type === 'pdf') {
            type_class = 'layui-bg-orange';
        } else if (node.type === 'word') {
            type_class = 'layui-bg-blue';
        } else if (node.type === 'ppt') {
            type_class = '';
        } else if (node.type === 'excel') {
            type_class = 'layui-bg-green';
        }
        return '\
							<blockquote class="layui-elem-quote layui-quote-nm">\
								<div class="layui-row layui-col-space5" style="text-align: center;">\
									<div class="layui-col-md5" style="text-align: left;">\
										' +
            node.name + '\
									</div>\
									<div class="layui-col-md1">\
										<span class="layui-badge ' +
            type_class + '">' + node.type + '</span>\
									</div>\
									<div class="layui-col-md2">\
										' +
            node.updateTime + '\
									</div>\
									<div class="layui-col-md2">\
										' + node.size.toLocaleString() +
            ' KB\
                        </div>\
                        <div class="layui-col-md2" data-path="' + node.path + '" data-type="' + node.type +
            '">\
                            <button type="button" class="view-btn layui-btn layui-btn-primary layui-btn-xs">预览</button>\
                            <button type="button" class="delete-btn layui-btn layui-btn-danger layui-btn-xs">删除</button>\
                        </div>\
                    </div>\
                </blockquote>\
                ';
    }


    //用户登录身份的跳转
    $.post("/user/config/islogin", function (rel) {
        if (rel.code === -1) {
            //用户未登录
            layer.msg(rel.msg, function () {
                window.location = "login.html";
            })
        } else if (rel.code === 1) {
            //管理用户登录
            layer.msg(rel.msg, function () {
                window.location = "sysconfig.html";
            })
        } else if (rel.code === 0) {
            //用户正常登录
            $.post("/sys/config/get", {
                key: "typeview"
            }, function (status) {
                if (!status) {
                    layer.msg("分类浏览未启用", function () {
                        window.location = "index.html";
                    })
                } else {
                    $.ajax({
                        url: '/document/list',
                        type: 'POST',
                        success: function (rel) {
                            console.log(rel.msg);
                            if (rel.code === 0) {
                                //
                                rel.data.forEach(function (val, inx) {
                                    $("#document-list-box").append(docmentNodeHtml(val))
                                });
                                $("#document-count").html(rel.count);
                                $(".view-btn").click(function () {
                                    var _path = $(this).parent().attr("data-path");
                                    var encodePath = encodeURIComponent(_path);
                                    var type = $(this).parent().attr("data-type");
                                    if (type === 'pdf') {
                                        var index = layer.open({
                                            type: 2,
                                            content: "/document/pdf/view?path=" + encodePath,
                                            area: ['80%', '80%'],
                                            maxmin: true
                                        });
                                    } else if (type === 'word') {
                                        $.post("/file/view/office/word", {path: _path}, function (rel) {
                                            if (rel.code === 1) {
                                                layer.open({
                                                    type: 1,
                                                    title: _path,
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
                                    } else if (type === 'excel') {
                                        $.post("/file/view/office/excel", {path: _path}, function (rel) {
                                            if (rel.code === 1) {
                                                layer.open({
                                                    type: 1,
                                                    title: _path,
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
                                    } else if (type === 'ppt') {
                                        $.post("/file/view/office/ppt", {path: _path}, function (rel) {
                                            if (rel.code === 1) {
                                                layer.open({
                                                    type: 1,
                                                    title: _path,
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
                                        // layer.msg('功能未实现，将直接下载');
                                        // var url = '/opt/super/download?filepath=' + encodePath;
                                        // const link = document.createElement('a');
                                        // link.href = url;
                                        // link.click();
                                    }
                                });
                                $(".delete-btn").click(function () {
                                    var path = $(this).parent().attr("data-path");
                                    $.ajax({
                                        url: '/opt/delete',
                                        method: 'POST',
                                        data: {
                                            filepath: path
                                        },
                                        success: function (rel) {
                                            layer.msg(rel.msg);
                                            if (rel.code === 0) {
                                                location.reload();
                                            }
                                        }
                                    })
                                });
                            }
                        }
                    });
                    //刷新缓存
                    $('#update-cache').click(function () {
                        $.post('/class/manage/del/cache', function (rel) {
                            if (rel.code === 0) {
                                location.reload()
                            } else {
                                layer.apply(rel.msg)
                            }
                        })
                    })
                }
            })
        }
    })

});