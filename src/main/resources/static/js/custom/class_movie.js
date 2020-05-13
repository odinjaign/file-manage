layui.use(['element', 'layer'], function() {
    var element = layui.element;
    var $ = layui.$;

    function movieNodeHtml(node) {
        return '\
					<blockquote class="layui-elem-quote layui-quote-nm">\
						<div class="layui-row layui-col-space5" style="text-align: center;">\
							<div class="layui-col-md5" style="text-align: left;">\
								' +
            node.name + '\
							</div>\
							<div class="layui-col-md2">\
								' + node.time +
            '\
                </div>\
                <div class="layui-col-md1">\
                    <span class="layui-badge layui-bg-orange">' +
            node.type +
            '</span>\
                </div>\
                <div class="layui-col-md1">\
                    <span class="layui-badge layui-bg-blue">' +
            parseFloat(node.size).toLocaleString() +
            'KB</span>\
                </div>\
                <div class="layui-col-md1">\
                    ' + node.length +
            '\
                </div>\
                <div class="layui-col-md2" data-path="' + node.path +
            '">\
                    <button type="button" class="play-btn layui-btn layui-btn-primary layui-btn-xs">播放</button>\
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
            $.post("/sys/config/get", {
                key: "typeview"
            }, function(status) {
                if (!status) {
                    layer.msg("分类浏览未启用", function() {
                        window.location = "index.html";
                    })
                } else {
                    $.ajax({
                        url: '/movie/list',
                        type: 'POST',
                        success: function(rel) {
                            console.log(rel.msg);
                            if (rel.code === 0) {
                                rel.data.forEach(function(val, inx) {
                                    $("#movie-list-box").append(movieNodeHtml(val))
                                });
                                $("#movie-count").html(rel.count);
                                $(".play-btn").click(function() {
                                    var path = $(this).parent().attr("data-path");
                                    var realpath = encodeURIComponent(path);
                                    layer.open({
                                        type: 1,
                                        title: false,
                                        shade: 0.8,
                                        closeBtn: 0,
                                        shadeClose: true,
                                        area: ['80%'],
                                        content: '<video controls="" width="100%" height="100%" preload="" src="/movie/file?path=' +
                                            realpath + '"></video>'
                                    });
                                });
                                //删除视频文件
                                $(".delete-btn").click(function() {

                                    var path = $(this).parent().attr("data-path");
                                    $.ajax({
                                        url: '/opt/delete',
                                        method: 'POST',
                                        data: {
                                            filepath: path
                                        },
                                        success: function(rel) {
                                            if (rel.code === 0) {
                                                //location.reload();
                                                $("#update-cache").click();
                                            }else {
                                                layer.msg(rel.msg);
                                            }
                                        }
                                    })
                                })
                            }
                        }
                    });
                    //刷新缓存
                    $('#update-cache').click(function () {
                        $.post('/class/manage/del/cache',function (rel) {
                            if (rel.code === 0) {
                                location.reload()
                            }else {
                                layer.apply(rel.msg)
                            }
                        })
                    })
                }
            })
        }
    })

});