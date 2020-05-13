layui.use(['element', 'layer'], function() {
    var element = layui.element;
    var $ = layui.$;

    function imageNodeHtml(node) {
        var encodePath = encodeURIComponent(node.path);

        return '\
					<blockquote class="layui-elem-quote layui-quote-nm">\
						<div class="layui-row layui-col-space5" style="text-align: center;">\
							<div class="layui-col-md5" style="text-align: left;">\
								'+node.name+'\
							</div>\
							<div class="layui-col-md2">\
								<span class="layui-badge layui-bg-blue">' +
            node.ext.toUpperCase() + '</span>\
							</div>\
							<div class="layui-col-md2">\
								<span class="layui-badge layui-bg-orange">' + node.w + 'x' + node.h +
            '</span>\
                </div>\
                <div class="layui-col-md1">\
                    <span class="layui-badge layui-bg-blue">' +
            parseFloat(node.size).toLocaleString() +
            'KB</span>\
        </div>\
        <div class="layui-col-md2" data-path="' + node.path +'" data-w="' + node.w +'" data-h="' + node.h +'">\
								<button type="button" class="view-btn layui-btn layui-btn-primary layui-btn-xs">预览</button>\
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
            $.post("/sys/config/get", {
                key: "typeview"
            }, function(status) {
                if (!status) {
                    layer.msg("分类浏览未启用", function() {
                        window.location = "index.html";
                    })
                } else {
                    $.ajax({
                        url: '/image/list',
                        type: 'POST',
                        success: function(rel) {
                            console.log(rel.msg);
                            if (rel.code === 0) {
                                rel.data.forEach(function(val, inx) {
                                    $("#image-list-box").append(imageNodeHtml(val))
                                });
                                //渲染预览
                                $("#image-count").html(rel.count);
                                $(".view-btn").click(function() {

                                    var path = $(this).parent().attr("data-path");
                                    var w = $(this).parent().attr("data-w");
                                    var h = $(this).parent().attr("data-h");
                                    var _url = '/image/img?path=' + encodeURIComponent(path);
                                    w = h > 600 ? w / h * 600 : w
                                    h = h > 600 ? 600 : h
                                    layer.open({
                                        type: 1,
                                        title: false,
                                        offset: 'auto',
                                        area: [w + 'px', h + 'px'],
                                        shadeClose: true,
                                        content: '<div><img alt="'+path+'" style="max-width: 100%;max-height: 100%" src="' + _url + '"></div>'
                                    });

                                });
                                //删除文件
                                $(".delete-btn").click(function() {

                                    var path = $(this).parent().attr("data-path");
                                    $.ajax({
                                        url: '/opt/delete',
                                        method: 'POST',
                                        data: {
                                            filepath: path
                                        },
                                        success: function(rel) {
                                            layer.msg(rel.msg);
                                            if (rel.code === 0) {
                                                location.reload();
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