layui.use(['layer', 'element'], function () {
    var layer = layui.layer,
        element = layui.element,
        $ = layui.$;

    function favoriteTmp(name, path, folder, time) {
        var folder_flag = '';
        if (folder) {
            folder_flag = '<span class="folder-flag layui-badge layui-bg-black">文件夹</span> ';
        } else {
            folder_flag = '';
        }

        return '<div class="layui-col-md4">\
						  <div class="layui-card">\
						    <div class="layui-card-header">' +
            folder_flag + name +
            '</div>\
                <div class="layui-card-body">\
                    <blockquote class="layui-elem-quote">' + path +
            '</blockquote>\
                  <button type="button" class="look-btn layui-btn layui-btn-normal layui-btn-xs">查看</button>\
                  <button type="button" class="remove-btn layui-btn layui-btn-warm layui-btn-xs">移除</button>\
                  <button type="button" class="layui-btn layui-btn-disabled layui-btn-xs">' +
            time + '</button>\
						    </div>\
						  </div>\
						</div>';
    }

    //用户未登录的跳转
    $.post("/user/config/islogin", function (rel) {
        if (rel.code === -1) {
            layer.msg(rel.msg, function () {
                window.location = "login.html";
            })
        } else if (rel.code === 1) {
            layer.msg(rel.msg, function () {
                window.location = "sysconfig.html";
            })
        } else if (rel.code === 0) {
            //用户正常登录
            $.post("/sys/config/get", {
                key: "favorite"
            }, function (status) {
                if (!status) {
                    layer.msg("文件收藏功能未启用", function () {
                        window.location = "index.html";
                    })
                } else {
                    $.ajax({
                        url: '/favorite/list',
                        type: 'POST',
                        error: function (err) {
                            console.log(err);
                        },
                        success: function (data) {
                            data.data.forEach(function (val) {
                                var h5 = favoriteTmp(val.name, val.path, val.folder, val.time);
                                $('#favorite-list').append(h5);
                            })
                            //移除收藏
                            $(".remove-btn").click(function () {
                                var path = $(this).parent().find('blockquote').html();
                                $.ajax({
                                    url: '/favorite/remove',
                                    type: 'POST',
                                    data: {
                                        path: path
                                    },
                                    success: function (rel) {
                                        layer.msg(rel.msg);
                                        if (rel.code === 0) {
                                            location.reload();
                                        }
                                    }
                                })
                            });
                            $(".look-btn").click(function () {
                                var path = $(this).parent().find('blockquote').html();
                                var length = $(this).parent().parent().find('.folder-flag').size();
                                if (length === 0) {
                                    //为文件操作
                                    console.log('文件查看');
                                    layer.msg('文件查看未实现！')
                                } else {
                                    //文件夹
                                    console.log('文件夹查看');
                                    $.ajax({
                                        url: '/main/enterfolder',
                                        type: 'POST',
                                        data: {
                                            folder: path
                                        },
                                        error: function (err) {
                                            console.log(err);
                                        },
                                        success: function (rel) {
                                            if (rel.code === 0) {
                                                window.location = 'index.html';
                                            } else {
                                                layer.msg(rel.msg)
                                            }
                                        }
                                    })
                                }

                            });
                        }
                    })
                }
            })
        }
    })





});