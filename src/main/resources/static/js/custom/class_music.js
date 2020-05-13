layui.use(['form', 'element', 'layer'], function() {
    var form = layui.form;
    var element = layui.element;
    var layer = layui.layer;
    var $ = layui.$;

    //格式化时间
    function fmtSecond(time) {
        var m = Math.floor(time / 60);
        var s = time - 60 * m;
        if (s < 10) {
            return m + ":0" + s;
        } else {
            return m + ":" + s;
        }

    }

    //音乐节点函数
    function musicNodeHtml(nodedata) {
        return '\
					<blockquote class="layui-elem-quote layui-quote-nm">\
					  <div class="layui-row layui-col-space5" style="text-align: center;">\
					  	<div class="layui-col-md3" style="text-align: left;">\
					  		' +
            nodedata.name + '\
					  	</div>\
					  	<div class="layui-col-md2">\
					  		' + nodedata.title +
            '\
              </div>\
              <div class="layui-col-md2">\
                  ' + nodedata.singer +
            '\
              </div>\
              <div class="layui-col-md1">\
                  <span class="layui-badge layui-bg-orange">' +
            nodedata.ext.toLocaleUpperCase() + '</span>\
					  	<span class="layui-badge layui-bg-green">' + fmtSecond(
                nodedata.length) + '</span>\
						</div>\
					  	<div class="layui-col-md2">\
					  		' +
            nodedata.sheet +
            '\
              </div>\
            <div class="layui-col-md2" data-path="' + nodedata.path +
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
                        url: '/music/list',
                        type: 'POST',
                        success: function(rel) {
                            console.log(rel.msg);
                            if (rel.code === 0) {
                                console.log(rel.data);
                                rel.data.forEach(function(val, inx) {
                                    $("#music-list-box").append(musicNodeHtml(val))

                                })
                                $("#music-count").html(rel.count);
                                //音乐播放
                                $(".play-btn").click(function() {
                                    // layer.msg();
                                    var path = $(this).parent().attr("data-path");
                                    // var realpath = path.replace(/\\/g, "/");
                                    var realpath = encodeURIComponent(path);
                                    layer.open({
                                        type: 1,
                                        title: false,
                                        shade: 0.8,
                                        closeBtn: 0,
                                        shadeClose: true,
                                        content: '<audio src="/music/file?path=' + realpath +
                                            '" controls="controls" loop="loop" autoplay="autoplay"></audio>'
                                    });
                                })
                                //删除音乐文件
                                $(".delete-btn").click(function() {
                                    layer.msg("删除文件");
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