layui.use(['layer', 'code','upload'], function () {
    var layer = layui.layer;
    var $ = layui.$;
    var upload = layui.upload;

    //文件节点函数
    function fileNode(node) {


        var _isfile = !node.folder;
        var _name = node.name;
        var _path = node.path;
        var _time = node.updatetime.substring(0, 10) + ' ' + node.updatetime.substring(11, 16);
        var _type = '';
        var _size = '';
        var _isfavorite = node.favorite;

        if (_isfile) {
            _type = "TXT";
            var _idx = _name.lastIndexOf(".");
            if (_idx === -1) {
                _type = "类型未知";
            } else {
                _type = _name.substring(_idx + 1, _name.length).toUpperCase();
            }

            _size = parseFloat(node.size).toLocaleString() + ' KB';
        } else {
            _type = "文件夹";
            _size = '';
        }


        return '\
					<blockquote class="layui-elem-quote  layui-quote-nm">\
						<div class="layui-row layui-col-space5" style="text-align: center;">\
							<div class="layui-col-md4"  style="text-align: left;">\
								<span class="file-name-span" data-path="' +
            _path + '" data-file-type="' + _type + '">' + _name +
            '</span>\
                </div>\
                <div class="layui-col-md2">\
                    ' + _time +
            '\
                </div>\
                <div class="layui-col-md1">\
                    <span class="layui-badge layui-bg-' + (_isfile ?
                'orange' : 'gray') + '">' + _type + '</span>\
							</div>\
							<div class="layui-col-md2">\
								' +
            _size +
            '\
                </div>\
                <div class="layui-col-md3">\
                    <div class="layui-btn-group btn-group-1" data-path="' + _path + '">\
									<button title="' + (_isfavorite ? '取消' : '') + '收藏" type="button" class="add-favorite-btn play-btn layui-btn layui-bg-red layui-btn-xs" data-is-favorite="' +
            _isfavorite + '"><i class="layui-icon">' + (_isfavorite ? '&#xe658;' : '&#xe600;') +
            '</i></button>\
                        <button type="button" class="file-move-btn layui-btn layui-btn-xs">移动</button>\
                        <button type="button" class="file-copy-btn layui-btn layui-btn-xs">复制</button>\
                        <button type="button" class="file-rename-btn layui-btn layui-btn-xs">重命名</button>\
                    </div>\
                    <div class="layui-btn-group btn-group-2" data-path="' + _path + '">\
									<button type="button" title="删除文件" class="file-delete-btn layui-btn layui-btn-xs"><i class="layui-icon">&#xe640;</i></button>\
									<button type="button" title="下载文件" class="file-download-btn delete-btn layui-btn layui-btn-xs"><i class="layui-icon">&#xe601;</i></button>\
									<button type="button" title="文件路径" class="file-info-btn layui-btn layui-btn-primary layui-btn-xs"><i class="layui-icon">&#xe702;</i></button>\
									<button type="button" title="压缩文件" class="file-zip-btn layui-btn layui-btn-xs"><i class="layui-icon">&#xe857;</i></button>\
								</div>\
							</div>\
						</div>\
					</blockquote>\
					';
    }

    //渲染当前目录路径
    $.post('/main/pwd', function (rel) {
        console.log(rel);
        $('#now-path-span').text(rel);
    })

    //分类浏览按钮
    $.post("/sys/config/get", {key: "typeview"}, function (status) {
        if (!status) {
            $("#typeview-btn").remove();
        }
    })

    //文件上传按钮
    $.post("/sys/config/get", {key: "upload"}, function (status) {
        if (!status) {
            $("#upload-btn").remove();
        } else {
            $('#upload-btn').click(function () {
                var fileoploadlayer = layer.open({
                    title: '文件上传',
                    type: 1,
                    skin: 'layui-layer-rim', //加上边框
                    area: ['420px', '240px'], //宽高
                    content: '<div id="upload-box" class="layui-upload" style="margin-top: 20px;text-align: center;"><button type="button" class="layui-btn layui-btn-normal" id="test8">选择文件</button><br/><br/><button type="button" class="layui-btn" id="test9">开始上传</button></div>'
                });
                //选完文件后不自动上传
                upload.render({
                    elem: '#test8',
                    url: '/opt/super/upload' //改成您自己的上传接口
                    ,
                    auto: false,
                    //multiple: true,
                    accept: 'file',
                    bindAction: '#test9',
                    done: function (res) {
                        layer.close(fileoploadlayer);
                        layer.msg(res.msg);
                        //console.log(res);
                        location.reload();
                    }
                });
            })
        }
    })

    //用户身份的跳转
    $.post("/user/config/islogin", function (rel) {
        if (rel.code === -1) {
            //用户未登录
            layer.msg(rel.msg, function () {
                window.location = "login.html";
            })
        } else if (rel.code === 1) {
            //管理用户
            layer.msg(rel.msg, function () {
                window.location = "sysconfig.html";
            })
        } else if (rel.code === 0) {
            //用户正常登录
            $('#return-last-btn').click(function () {
                $.ajax({
                    url: '/main/returnlast',
                    method: 'POST',
                    success: function (suc) {
                        //刷新页面
                        if (suc) {
                            // layer.msg('返回上一级成功', function () {
                            location.reload();
                            // })
                        } else {
                            layer.msg('返回上一级失败');
                        }
                    }
                });
            })
            // 旧版文件/文件夹添加逻辑
            $('#add-file-btn-old').click(function () {
                layer.open({
                    title: '创建文件',
                    content: '<input id="filename" type="text" name="title" lay-verify="title" autocomplete="off" placeholder="文件名" class="layui-input"><br/><input id="foldername" type="text" name="title" lay-verify="title" autocomplete="off" placeholder="文件夹名" class="layui-input">',
                    yes: function (index, layero) {
                        //do something
                        var filename = $(layero.find('#filename')).val();
                        var foldername = $(layero.find('#foldername')).val();
                        var isfolder = false;
                        var name = filename;
                        if (filename !== '' && foldername === '') {
                            console.log("创建文件");
                            name = filename;
                            isfolder = false;
                        } else if (filename === '' && foldername !== '') {
                            console.log("创建文件夹");
                            name = foldername;
                            isfolder = true;
                        } else if (filename === '' && foldername === '') {
                            layer.msg("请输入文件夹或者文件夹名")
                            return;
                        } else if (filename !== '' && foldername !== '') {
                            layer.msg("只能输入一个类型");
                            return;
                        }
                        var reqdata = {
                            filename: name,
                            isfolder: isfolder
                        };
                        //创建文件（夹）
                        $.ajax({
                            url: '/opt/super/create',
                            method: 'POST',
                            data: reqdata,
                            success: function (rel) {
                                layer.msg(rel.msg);
                                if (rel.code === 0) {
                                    location.reload();
                                }
                            }
                        })
                        //
                        layer.close(index); //如果设定了yes回调，需进行手工关闭
                    }
                })
            })

            $('#add-file-btn').click(function () {
                //添加文件注册事件
                layer.confirm('添加文件还是文件夹？', {
                    btn: ['文件', '文件夹'] //按钮
                }, function () {
                    //文件按钮
                    layer.msg('添加文件', {icon: 1});
                    layer.alert('<input id="filename" type="text" name="title"  autocomplete="off" placeholder="文件名" class="layui-input">', {
                        skin: 'layui-layer-molv' //样式类名
                        , closeBtn: 1
                    }, function (index, layero) {
                        var filename = $(layero.find('#filename')).val();
                        if (filename === ''){
                            layer.msg("文件名不能为空")
                        } else {
                            $.post('/opt/super/create', {filename: filename, isfolder: false}, function (rel) {
                                //layer.msg(rel.msg);
                                if (rel.code === 0) {
                                    location.reload();
                                }else {
                                    layer.msg(rel.msg);
                                }
                            })
                        }
                        layer.close(index)
                    });
                }, function () {
                    //文件按钮
                    layer.msg('添加文件夹', {icon: 1});
                    layer.alert('<input id="filename" type="text" name="title"  autocomplete="off" placeholder="文件夹名" class="layui-input">', {
                        skin: 'layui-layer-molv' //样式类名
                        , closeBtn: 0
                    }, function (index, layero) {
                        var filename = $(layero.find('#filename')).val();
                        if (filename === ''){
                            layer.msg("文件夹名不能为空")
                        } else {
                            $.post('/opt/super/create', {filename: filename, isfolder: true}, function (rel) {
                                //layer.msg(rel.msg);
                                if (rel.code === 0) {
                                    location.reload();
                                }else {
                                    layer.msg(rel.msg);
                                }
                            })
                        }
                        layer.close(index)
                    });
                });
            })

            $('#refresh-cache-btn').click(function () {
                $.ajax({
                    url: '/main/updatecache',
                    method: 'POST',
                    success: function (suc) {
                        //刷新页面
                        if (suc) {
                            location.reload();
                        } else {
                            layer.msg('缓存刷新失败');
                        }
                    }
                });
            })
            $('#change-btn-group').click(function () {
                var idx = $(this).attr('data-group-idx');
                if (idx === '1') {
                    $('.btn-group-2').show();
                    $('.btn-group-1').hide();
                    $(this).attr('data-group-idx', '2')
                }
                if (idx === '2') {
                    $('.btn-group-1').show();
                    $('.btn-group-2').hide();
                    $(this).attr('data-group-idx', '1')
                }
                //var btn_group_clazzname = 'btn-group-' + $(this).attr('data-group-idx')

            })
            $.get('cfg/view-config.json', function (data) {
                var view_config = data;
                $.post('/main/list', function (rel) {
                    console.log(rel);
                    if (rel.code === 0) {
                        rel.data.forEach(function (val) {
                            //文件
                            if (!val.folder) {
                                return;
                            }
                            $('#file-list-box').append(fileNode(val));
                        })
                        rel.data.forEach(function (val) {
                            //文件夹
                            if (val.folder) {
                                return;
                            }
                            $('#file-list-box').append(fileNode(val));
                        })

                        //切换一个操作项
                        $('#change-btn-group').click();
                        $('.file-name-span')
                            .hover(function () {
                                this.style.cursor = 'pointer'
                            })
                            .click(function () {
                                var _name = $(this).html();
                                var _type = $(this).attr('data-file-type');
                                var _path = $(this).attr('data-path');
                                if (_type === '文件夹') {

                                    $.ajax({
                                        url: '/main/enterfolder',
                                        method: 'POST',
                                        data: {
                                            folder: _path
                                        },
                                        success: function (rel) {
                                            //刷新页面
                                            if (rel.code === 0) {
                                                location.reload();
                                            } else if (rel.code === -1) {
                                                layer.msg(rel.msg);
                                                //文件夹被加密
                                                layer.open({
                                                    title: '输入密码',
                                                    content: '<input type="text" name="password" lay-verify="title" autocomplete="off" placeholder="文件夹密码" class="layui-input">',
                                                    yes: function (index, layero) {
                                                        //do something
                                                        var password = $(layero.find('.layui-input')).val();
                                                        var reqdata = {
                                                            password: password,
                                                            folder: _path
                                                        };
                                                        $.ajax({
                                                            url: '/main/enterfolder',
                                                            method: 'POST',
                                                            data: reqdata,
                                                            success: function (rel) {
                                                                if (rel.code === 0) {
                                                                    location.reload();
                                                                } else {
                                                                    layer.msg(rel.msg)
                                                                }
                                                            }
                                                        });
                                                    }
                                                })
                                            }
                                        }
                                    });

                                } else if (_type === '类型未知') {
                                    layer.msg("无法查看此文件")
                                } else if (_type === 'ZIP') {
                                    //询问框
                                    layer.confirm('是否解压文件' + _name + '？', {
                                        btn: ['解压', '解压到', '取消'] //按钮
                                    }, function () {
                                        //解压文件
                                        $.post('/opt/super/unzip', {path: _path}, function () {
                                            layer.msg(rel.msg);
                                            if (rel.code === 0) {
                                                location.reload();
                                            }
                                        })
                                    }, function () {
                                        layer.open({
                                            title: '解压到',
                                            content: '<input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="目标路径" class="layui-input">',
                                            yes: function (index, layero) {
                                                //do something
                                                var folder = $(layero.find('.layui-input')).val();
                                                var reqdata = {
                                                    path: _path,
                                                    folder: folder
                                                };
                                                $.post('/opt/super/unzip', reqdata, function () {
                                                    layer.msg(rel.msg);
                                                    if (rel.code === 0) {
                                                        location.reload();
                                                    }
                                                })
                                                layer.close(index); //如果设定了yes回调，需进行手工关闭
                                            }
                                        })
                                    });
                                    //['C', 'YML', 'CPP', 'JAVA', 'BAT', 'REG', 'JS', 'CSS', 'HTML', 'XML', 'PROPERTIES']
                                } else if (view_config.code.includes(_type)) {
                                    //layer.msg("查看代码类型文件")
                                    $.post('/file/view/text/code', {path: _path}, function (rel) {
                                        console.log(rel);
                                        var e_code = $("<div>").text(rel.data).html();
                                        layer.open({
                                            type: 1,
                                            title: false,
                                            closeBtn: 0,
                                            area: ['1000px', '560px'],
                                            shadeClose: true,
                                            content: '<pre lay-title="' + rel.ext + '" lay-height="500px" lay-skin="" lay-encode="true" class="layui-code">' + e_code + '</pre>'
                                        });
                                        layui.code(); //引用code方法

                                    })
                                } else if (view_config.office.word.includes(_type)) {
                                    $.post("/file/view/office/word", {path: _path}, function (rel) {
                                        if (rel.code === 1) {
                                            layer.open({
                                                type: 1,
                                                title: _name,
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
                                } else if (view_config.office.excel.includes(_type)) {
                                    $.post("/file/view/office/excel", {path: _path}, function (rel) {
                                        if (rel.code === 1) {
                                            layer.open({
                                                type: 1,
                                                title: _name,
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
                                } else if (view_config.office.ppt.includes(_type)) {
                                    $.post("/file/view/office/ppt", {path: _path}, function (rel) {
                                        if (rel.code === 1) {
                                            layer.open({
                                                type: 1,
                                                title: _name,
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
                                } else if (view_config.img.includes(_type)) {
                                    //图片预览
                                    function getImageWidth(url, callback) {
                                        var img = new Image();
                                        img.src = url;
                                        // 如果图片被缓存，则直接返回缓存数据
                                        if (img.complete) {
                                            callback(img.width, img.height);
                                        } else {
                                            img.onload = function () {
                                                callback(img.width, img.height);
                                            }
                                        }
                                    }

                                    var _url = '/image/img?path=' + encodeURIComponent(_path);
                                    getImageWidth(_url, function (w, h) {
                                        w = h > 600 ? w / h * 600 : w
                                        h = h > 600 ? 600 : h
                                        layer.open({
                                            type: 1,
                                            title: false,
                                            offset: 'auto',
                                            closeBtn: 0, //不显示关闭按钮
                                            area: [w + 'px', h + 'px'],
                                            shadeClose: true,
                                            content: '<div><img style="max-width: 100%;max-height: 100%" src="' + _url + '"></div>'
                                        });
                                    })

                                } else if (_type === 'MD') {
                                    $.post("/file/view/text/md", {path: _path}, function (rel) {
                                        if (rel.code === 1) {
                                            layer.open({
                                                type: 1,
                                                title: _name,
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
                                }  else if (_type === "PDF"){
                                    var encodePath = encodeURIComponent(_path);
                                    layer.open({
                                        type: 2,
                                        content: "/document/pdf/view?path=" + encodePath,
                                        area: ['80%', '80%'],
                                        maxmin: true
                                    });
                                } else {
                                    layer.msg("查看" + _type + "类型文件")
                                }
                            })
                        $('.file-move-btn').click(function () {
                            var _path = $(this).parent().attr('data-path');
                            console.log("移动:", _path);
                            layer.open({
                                title: '移动到',
                                content: '<input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="目标路径" class="layui-input">',
                                yes: function (index, layero) {
                                    //do something
                                    var folder = $(layero.find('.layui-input')).val();
                                    var reqdata = {
                                        filepath: _path,
                                        folder: folder
                                    };
                                    $.ajax({
                                        url: '/opt/moveto',
                                        method: 'POST',
                                        data: reqdata,
                                        success: function (rel) {
                                            layer.msg(rel.msg);
                                            if (rel.code === 0) {
                                                location.reload();
                                            }
                                        }
                                    })
                                    //发送复制事件
                                    layer.close(index); //如果设定了yes回调，需进行手工关闭
                                }
                            })
                        })
                        $('.file-copy-btn').click(function () {
                            var _path = $(this).parent().attr('data-path');
                            console.log("复制:", _path);
                            layer.open({
                                title: '复制到',
                                content: '<input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="目标路径" class="layui-input">',
                                yes: function (index, layero) {
                                    //do something
                                    var folder = $(layero.find('.layui-input')).val();
                                    var reqdata = {
                                        filepath: _path,
                                        folder: folder
                                    };
                                    $.ajax({
                                        url: '/opt/copyto',
                                        method: 'POST',
                                        data: reqdata,
                                        success: function (rel) {
                                            layer.msg(rel.msg);
                                            if (rel.code === 0) {
                                                location.reload();
                                            }
                                        }
                                    })
                                    //发送复制事件
                                    layer.close(index); //如果设定了yes回调，需进行手工关闭
                                }
                            })
                        })
                        $('.file-rename-btn').click(function () {
                            var _path = $(this).parent().attr('data-path');
                            console.log("重命名:", _path);
                            layer.open({
                                title: '重命名',
                                content: '<input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="请输入新的名字" class="layui-input">',
                                yes: function (index, layero) {
                                    //do something
                                    var newname = $(layero.find('.layui-input')).val();
                                    //发送重命名事件
                                    var reqdata = {
                                        filepath: _path,
                                        newname: newname
                                    };
                                    $.ajax({
                                        url: '/opt/rename',
                                        method: 'POST',
                                        data: reqdata,
                                        success: function (rel) {
                                            layer.msg(rel.msg);
                                            if (rel.code === 0) {
                                                location.reload();
                                            }
                                        }
                                    })
                                    layer.close(index); //如果设定了yes回调，需进行手工关闭
                                }
                            })
                        })
                        $('.file-download-btn').click(function () {
                            var _path = $(this).parent().attr('data-path');
                            console.log("下载:", _path);
                            const link = document.createElement('a');
                            link.href = '/opt/super/download?filepath=' + encodeURIComponent(_path);
                            link.click();
                        })
                        $('.file-delete-btn').click(function () {
                            var _path = $(this).parent().attr('data-path');
                            console.log("删除:", _path);
                            $.ajax({
                                url: '/opt/delete',
                                method: 'POST',
                                data: {
                                    filepath: _path
                                },
                                success: function (rel) {
                                    layer.msg(rel.msg);
                                    if (rel.code === 0) {
                                        location.reload();
                                    }
                                }
                            })
                        })
                        $('.file-info-btn').click(function () {
                            var _path = $(this).parent().attr('data-path');
                            layer.msg(_path);
                        })
                        $('.file-zip-btn').click(function () {
                            var _path = $(this).parent().attr('data-path');
                            // layer.msg(_path + '123131');
                            //询问框
                            layer.confirm('是否压缩目录？', {
                                btn: ['压缩', '压缩到', '取消'] //按钮
                            }, function () {
                                //layer.msg('压缩');
                                //解压文件
                                $.post('/opt/super/zip', {folder: _path}, function (rel) {
                                    layer.msg(rel.msg);
                                    if (rel.code === 0) {
                                        location.reload();
                                    }
                                })
                            }, function () {
                                layer.open({
                                    title: '压缩为',
                                    content: '<input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="压缩后文件路径" class="layui-input">',
                                    yes: function (index, layero) {
                                        //do something
                                        var _dst = $(layero.find('.layui-input')).val();
                                        var reqdata = {
                                            folder: _path,
                                            dst: _dst
                                        };
                                        $.post('/opt/super/zip', reqdata, function (rel) {
                                            layer.msg(rel.msg);
                                            if (rel.code === 0) {
                                                location.reload();
                                            }
                                        })
                                        layer.close(index); //如果设定了yes回调，需进行手工关闭
                                    }
                                })
                            });
                        })

                        $.post('/sys/config/get', {
                            key: 'favorite'
                        }, function (rel) {
                            // console.log(rel);
                            if (rel) { //如果收藏功能开启
                                $('.add-favorite-btn').click(function () {
                                    var _favorite = $(this).attr('data-is-favorite');
                                    var _path = $(this).parent().attr('data-path');
                                    if (_favorite === 'false') {
                                        //没有收藏
                                        // todo 收藏
                                        //console.log('添加收藏',_path);
                                        $.ajax({
                                            url: '/favorite/add',
                                            method: 'POST',
                                            data: {
                                                path: _path
                                            },
                                            success: function (rel) {
                                                layer.msg(rel.msg);
                                            }
                                        })
                                        $(this).find('i').html('&#xe658;');
                                        $(this).attr('data-is-favorite', true);
                                        $(this).attr('title', '取消收藏');
                                    } else {
                                        //取消收藏
                                        //console.log('取消收藏');
                                        $.ajax({
                                            url: '/favorite/remove',
                                            method: 'POST',
                                            data: {
                                                path: _path
                                            },
                                            success: function (rel) {
                                                layer.msg(rel.msg);
                                            }
                                        })
                                        $(this).find('i').html('&#xe600;');
                                        $(this).attr('data-is-favorite', false);
                                        $(this).attr('title', '收藏');
                                    }
                                    console.log();
                                })
                            } else {
                                $('.add-favorite-btn').remove();
                            }
                        })
                    } else {
                        layer.msg(rel.msg);
                    }
                })
            })
        }
    })

})