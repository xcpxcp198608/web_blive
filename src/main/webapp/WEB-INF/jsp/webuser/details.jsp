<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<rapid:override name="title">Details</rapid:override>
<rapid:override name="css_js">
    <script>
        $(function(){
            var baseUrl = "http://"+location.host+"/blive";
            $('#title').blur(function(){
                update()
            });

            $('#message').blur(function(){
                update()
            });

            function update(){
                var title = $('#title').val();
                var message = $('#message').val();
                if(title.length <= 0){
                    title = ${userInfo.username}
                }
                console.log(title);
                console.log(message);
                $.ajax({
                    type: "PUT",
                    url: baseUrl + "/channel/title",
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify({"userId": ${userInfo.id}, "title": title, "message": message}),
                    dataType: "json",
                    beforeSend: function () {
                    },
                    success: function (response) {
                        console.log(response);
                        showNotice(response.message);
                    },
                    error: function () {
                        showNotice('update failure')
                    }
                });
            }

            function showNotice(message){
                $('#notice_message').html(message);
                $('#notice').css('display', 'block');
                setTimeout(function(){
                    $('#notice_message').html("");
                    $('#notice').css('display', 'none');
                }, 3000)
            }

            $('#btStart').click(function(){
                var currentHtm = $(this).html();
                var activate = 0;
                if('Start' === currentHtm){
                    activate = 1;
                }else if ('Stop' === currentHtm){
                    activate = 0
                }
                console.log(currentHtm);
                console.log(activate);
                $.ajax({
                    type:'PUT',
                    url: baseUrl + "/channel/status/" + activate + "/" + ${userInfo.id},
                    dataType:'json',
                    success: function (response) {
                        if('Stop' === $('#btStart').html()){
                            $('#btStart').html('Start')
                        }else {
                            $('#btStart').html('Stop')
                        }
                    },
                    error: function () {
                        showNotice('failure')
                    }
                })
            });


            $('#upload').click(function(){
                var file = new FormData($('#img_form')[0]);
                if(file === null){
                    showNotice('file no choose');
                    return
                }
                $.ajax({
                    type: 'POST',
                    url: baseUrl + '/channel/upload/${userInfo.id}',
                    cache: false,
                    data: file,
                    processData: false,
                    contentType: false,
                    dataType:"json",
                    beforeSend: function () {
                    },
                    success: function (response) {
                        if(response.code == 200){
                            $('#imgPreview').attr('src', response.t.preview);
                        }else{
                            showNotice(response.message)
                        }
                    },
                    error: function () {
                        console.log("ajax error");
                        showNotice('update failure')
                    }
                })
            })

        })
    </script>
</rapid:override>
<rapid:override name="content">
    <div>
        <table class="table table-hover table-striped">
            <thead>
                <tr>
                    <th>Item</th>
                    <th>Value</th>
                </tr>
            </thead>
            <tbody>
                <tr><td>username</td><td>${userInfo.username}</td></tr>
                <tr><td>email</td><td>${userInfo.email}</td></tr>
                <tr><td>url</td><td>${userInfo.channelInfo.url.substring(0, 40)}</td></tr>
                <tr><td>key</td><td>${userInfo.channelInfo.url.substring(40)}</td></tr>
                <tr><td>title</td>
                    <td>
                        <input type="text" class="form-control" id="title"
                               value="${userInfo.channelInfo.title}"/>
                    </td>
                </tr>
                <tr><td>content</td>
                    <td>
                        <input type="text" class="form-control" id="message"
                               value="${userInfo.channelInfo.message}"/>
                    </td>
                </tr>
                <tr>
                    <td>cover</td>
                    <td>
                        <div style="display: block; float: left; width: 50%">
                            <img id="imgPreview" style="width: 300px; max-height: 200px"
                                 src="${userInfo.channelInfo.preview}"
                                 onerror="this.src='Resource/img/img_error_preview.jpg'"/>
                        </div>
                        <div style="display: block; float: left; width: 50%">
                            <form id="img_form" method="post" enctype="multipart/form-data">
                                <input type="file" id="file" name="file" accept="image/png, image/jpeg"/>
                            </form>
                            <br/>
                            <button class="btn btn-default" id="upload">Upload</button>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
        <div style="margin: auto">
            <button id="btStart" class="btn btn-primary">Start</button>
        </div>
    </div>

    <div id="notice" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: 1001; display: none">
        <div style="width:50%; margin: 530px auto">
            <h4 id="notice_message" style="font-size: 20px; width: 100%; text-align: center;"></h4>
        </div>
    </div>
</rapid:override>

<%@ include file="../base.jsp"%>