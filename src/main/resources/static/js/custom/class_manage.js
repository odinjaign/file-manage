layui.use(['layer', 'element'], function() {

    var layer = layui.layer;
    var element = layui.element;
    var $ = layui.$;

    function htmlTmp(path, num, exts, type) {

        var extsArr = exts.split('|');
        var html1 =
            '\
        <blockquote class="layui-elem-quote">\
            <div class="layui-row layui-col-space10">\
                <div class="layui-col-md10">\
                    <span class="data-type" style="display: none;">' +
            type + '</span><span class="data-path">' + path + '</span> <span class="data-num layui-badge">' + num +
            '</span>\
                    <br>';
        var html3 =
            '\
                </div>\
                <div class="layui-col-md1">\
                    <button type="button" class="modify-btn layui-btn layui-btn-fluid">修改</button>\
                </div>\
                <div class="layui-col-md1">\
                    <button type="button" class="remove-btn layui-btn layui-bg-red layui-btn-fluid">移除</button>\
                </div>\
            </div>\
        </blockquote>\
        ';
        var html2 = '';
        extsArr.forEach(function(val, index) {
            html2 = html2 + ' <span class="data-ext layui-badge layui-bg-cyan">' + val + '</span> ';
        })
        return html1 + html2 + html3;

    }
    function modifyTmp(num, exts) {
        return '\
					<div class="layui-form-item" style="margin-top: 50px;margin-left: 80px;">\
						<label class="layui-form-label">索引层数</label>\
						<div class="layui-input-inline">\
							<input type="text" name="num" required lay-verify="required" value="' +
            num +
            '" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item" style="margin-top: 20px;margin-left: 80px;">\
            <label class="layui-form-label">索引后缀</label>\
            <div class="layui-input-inline">\
                <input type="text" name="exts" value="' +
            exts +
            '" lay-verify="required" placeholder="使用 | 分割" autocomplete="off" class="layui-input">\
            </div>\
        </div>\
        <div class="layui-form-item" style="margin-top: 50px;margin-left: 80px;">\
            <div class="layui-input-block">\
                <button type="button" class="modify-in-btn layui-btn layui-btn-normal">确认修改</button>\
            </div>\
        </div>';
    }
    function addTmp() {
        return '\
					<div class="layui-form-item" style="margin-top: 20px;margin-left: 80px;">\
						<label class="layui-form-label">索引目录</label>\
						<div class="layui-input-inline">\
							<input type="text" name="path" required lay-verify="required" placeholder="请输入要索引的目录" autocomplete="off" class="layui-input">\
						</div>\
					</div>\
					<div class="layui-form-item" style="margin-left: 80px;">\
						<label class="layui-form-label">索引层数</label>\
						<div class="layui-input-inline">\
							<input type="text" name="num" required lay-verify="required" placeholder="请输入目录追溯的层数" autocomplete="off" class="layui-input">\
						</div>\
					</div>\
					<div class="layui-form-item" style="margin-left: 80px;">\
						<label class="layui-form-label">索引后缀</label>\
						<div class="layui-input-inline">\
							<input type="text" name="exts" required lay-verify="required" placeholder="使用 | 分割" autocomplete="off" class="layui-input">\
						</div>\
					</div>\
					<div class="layui-form-item" style="margin-left: 80px;">\
						<div class="layui-input-block">\
							<button type="button" class="add-in-btn layui-btn layui-btn-normal">确认添加</button>\
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
        } else if(rel.code === 1){
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
                    $('.add-btn').click(function() {
                        var t = $(this).html()
                        console.log();
                        layer.open({
                            type: 1,
                            title: '添加' + t,
                            closeBtn: 0,
                            shadeClose: true,
                            area: ['500px', '300px'],
                            content: addTmp(),
                            success: function(layero, index) {
                                console.log(layero, index);

                                $('.add-in-btn').click(function() {
                                    var _path = layero.find(" input[ name='path' ] ").val();
                                    var _num = layero.find(" input[ name='num' ] ").val();
                                    var _exts = layero.find(" input[ name='exts' ] ").val();
                                    var _type = t;
                                    var reqdata = {
                                        path: _path,
                                        num: _num,
                                        exts: _exts,
                                        type: _type
                                    }
                                    console.log(reqdata);
                                    //发送ajax请求
                                    //添加收藏
                                    $.ajax({
                                        url:"/class/manage/add",
                                        type:'POST',
                                        data:reqdata,
                                        success:function(rel){
                                            layer.msg(rel.msg);
                                            if (rel.code === 0) {
                                                location.reload();
                                            }
                                        }
                                    })
                                    layer.close(index);
                                })
                            }
                        })
                    })
                    //请求收藏列表：
                    $.ajax({
                        url:'/class/manage/items',
                        type:'POST',
                        success:function(data){
                            data.forEach(function(value,index){
                                if(value.classtype === 1){
                                    //音乐
                                    $('.list-item-music').append(htmlTmp(value.checkfolder, value.checklength, value.checkexts, value.classtype));
                                } else if(value.classtype === 2){
                                    //视频
                                    $('.list-item-video').append(htmlTmp(value.checkfolder, value.checklength, value.checkexts, value.classtype));
                                }else if(value.classtype === 3){
                                    //视频
                                    $('.list-item-document').append(htmlTmp(value.checkfolder, value.checklength, value.checkexts, value.classtype));
                                }else if(value.classtype === 4){
                                    //视频
                                    $('.list-item-img').append(htmlTmp(value.checkfolder, value.checklength, value.checkexts, value.classtype));
                                }
                            })
                            //渲染完成再进行事件监听
                            $('.modify-btn').click(function() {
                                //获取路径，Exts，索引层级
                                var searchDom = $(this).parent().parent();
                                //需要目录类别
                                var type = searchDom.find('.data-type').html();
                                var path = searchDom.find('.data-path').html();
                                var num = searchDom.find('.data-num').html();
                                var exts = '';
                                searchDom.find('.data-ext').each(function(index, elem) {
                                    if (index === 0) {
                                        exts += $(elem).html()
                                    } else {
                                        exts += '|' + $(elem).html()
                                    }

                                })

                                layer.open({
                                    type: 1,
                                    title: path,
                                    closeBtn: 0,
                                    shadeClose: true,
                                    area: ['500px', '300px'],
                                    content: modifyTmp(num, exts),
                                    success: function(layero, index) {

                                        $('.modify-in-btn').click(function() {
                                            //layer.msg('点击按钮')
                                            //
                                            // console.log(layero, index);
                                            var _path = path;
                                            var _num = layero.find(" input[ name='num' ] ").val();
                                            var _exts = layero.find(" input[ name='exts' ] ").val();
                                            var _type = type;
                                            var reqdata = {
                                                path: _path,
                                                num: _num,
                                                exts: _exts,
                                                type: _type
                                            }
                                            console.log(reqdata);
                                            //修改收藏
                                            $.ajax({
                                                url:"/class/manage/modify",
                                                type:'POST',
                                                data:reqdata,
                                                success:function(rel){
                                                    layer.msg(rel.msg);
                                                    if (rel.code === 0) {
                                                        location.reload();
                                                    }
                                                }
                                            })
                                            layer.close(index);

                                        })
                                    }
                                });
                            })
                            $('.remove-btn').click(function() {
                                var searchDom = $(this).parent().parent();
                                //需要目录类别
                                var type = searchDom.find('.data-type').html();
                                var path = searchDom.find('.data-path').html();
                                //移除索引
                                $.ajax({
                                    url:"/class/manage/delete",
                                    type:'POST',
                                    data:{
                                        type:type,
                                        path:path
                                    },
                                    success:function(rel){
                                        layer.msg(rel.msg);
                                        if (rel.code === 0) {
                                            location.reload();
                                        }
                                    }
                                })
                            })

                        }
                    })
                }
            })
        }
    })

});