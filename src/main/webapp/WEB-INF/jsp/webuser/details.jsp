<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<rapid:override name="title">Details</rapid:override>
<rapid:override name="css_js">
    <link rel="stylesheet" href="Resource/css/details.css">
    <style>
        .message{ font-size: 30px; position: absolute}
    </style>
    <script src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <script>
        $(function(){
            var baseUrl = "http://"+location.host+"/blive";

            var video = document.getElementById("video");

            if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
                navigator.mediaDevices.getUserMedia({
                    video: true,
                    audio: false
                }).then(function(stream) {
                    console.log(stream);
                    mediaStreamTrack = typeof stream.stop === 'function' ? stream : stream.getTracks()[1];
                    video.src = (window.URL || window.webkitURL).createObjectURL(stream);
                    video.play();
                }).catch(function(err) {
                    console.log(err);
                })
            }

            function showDanMu(channel) {
                var appkey = "BC-6a9b6c468c894389881bc1df7d90cddb";
                var currentPosition = 1;
                var goEasy = new GoEasy({
                    appkey: appkey
                });
                goEasy.subscribe({
                    channel: channel,
                    onSuccess: function () {
                        console.log("onSuccess")
                    },
                    onFailed: function (error) {
                        console.log(error.content)
                    },
                    onMessage: function (message) {
                        console.log(message.content);
                        var sp = createElement(message);
                        $('#div'+currentPosition).append(sp);
                        setStartPosition(sp);
                        var elementWidth = sp.outerWidth();
                        console.log(elementWidth);
                        moveToLeft(sp, elementWidth);
                        currentPosition ++;
                        if(currentPosition > 5){
                            currentPosition = 1
                        }
                    }
                });
            }

            function createElement(message) {
                var color = ['#ff7baf', '#88d6ff', '#80ff9b', '#2831ff', '#ff873f', '#deff7c', '#21f8ff', '#cc78ff'];
                var sp = $('<span>',{'class':'message'});
                var index = 1 + Math.round(Math.random() * 6);
                console.log(index);
                sp.css('color', color[index]);
                sp.html(message.content);
                return sp
            }

            function setStartPosition(sp) {
                var right = $('#dDetails').offset().left;
                var startPosition = right - 100;
                console.log(startPosition);
                sp.css("left", startPosition)

            }

            function moveToLeft(sp, elementWidth) {
                var jMessage = sp;
                clearInterval(jMessage.timer);
                jMessage.timer = setInterval(function () {
                    var speed = 8;
                    var currentValue = parseInt(sp.css('left'));
                    console.log(currentValue);
                    if (currentValue < -elementWidth ) {
                        clearInterval(jMessage.timer);
                        sp.css("left", -elementWidth-150);
                        sp.remove();
                        delete(sp);
                    }else{
                        sp.css("left", currentValue - speed)
                    }
                }, 40);
            }

            $('#btStart').click(function(){
                $(this).attr('disabled', 'disabled');
                var currentHtm = $(this).html();
                var activate = 0;
                if('Start' === currentHtm){
                    activate = 1;
                }else if ('Stop' === currentHtm){
                    activate = 0
                }
                $.ajax({
                    type:'PUT',
                    url: baseUrl + "/channel/status/" + activate + "/" + ${userInfo.id},
                    dataType:'json',
                    success: function (response) {
                        $('#btStart').removeAttr('disabled');
                        if('Stop' === $('#btStart').html()){
                            $('#btStart').html('Start')
                        }else {
                            $('#btStart').html('Stop');
                            var channelId =response.t['id'];
                            showDanMu(channelId.toString());
                        }
                    },
                    error: function () {
                        $('#btStart').removeAttr('disabled');
                        showNotice('failure')
                    }
                })
            });

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

            $('#price').blur(function () {
               var price = $(this).val();
               if(price.length <= 0){
                   return;
               }
                $.ajax({
                    type: "PUT",
                    url: baseUrl + "/channel/price",
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify({"userId": ${userInfo.id}, "price": price}),
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
            });

            function showNotice(message){
                $('#notice_message').html(message);
                $('#notice').css('display', 'block');
                setTimeout(function(){
                    $('#notice_message').html("");
                    $('#notice').css('display', 'none');
                }, 3000)
            }

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
    <div id="dFlex">
        <div id="dCamera">
            <div>
                <video id="video" autoplay="" style='width:100%; height:600px;'></video>
            </div>
            <div id="dComment">
                <div style="height: 30px" id="div1"></div>
                <div style="height: 30px" id="div2"></div>
                <div style="height: 30px" id="div3"></div>
                <div style="height: 30px" id="div4"></div>
                <div style="height: 30px" id="div5"></div>
            </div>
            <br/>
            <div style="margin: auto">
                <button id="btStart" class="btn btn-primary">Start</button>
            </div>
        </div>

        <div id="dDetails">
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
                <tr>
                    <td>content</td>
                    <td>
                        <input type="text" class="form-control" id="message"
                               value="${userInfo.channelInfo.message}"/>
                    </td>
                </tr>
                <tr>
                    <td>price</td>
                    <td>
                        <input type="number" class="form-control" id="price"
                               value="${userInfo.channelInfo.price}"/>
                    </td>
                </tr>
                <tr>
                    <td>cover</td>
                    <td>
                        <img id="imgPreview" style="width: 300px; max-height: 200px; min-height: 200px"
                             src="${userInfo.channelInfo.preview}"
                             onerror="this.src='Resource/img/img_error_preview.jpg'"/>
                    </td>
                </tr>
                <tr>
                    <td>change</td>
                    <td>
                        <form id="img_form" method="post" enctype="multipart/form-data">
                            <input type="file" id="file" name="file" accept="image/png, image/jpeg"/>
                        </form>
                        <br/>
                        <button class="btn btn-default" id="upload">Upload</button>
                    </td>
                </tr>
                </tbody>
            </table>

        </div>

    </div>

    <div id="notice" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: 1001;
    background-color: rgba(0,0,0,0.3); display: none">
        <div style="display: flex; display: -webkit-flex; flex-direction:row; justify-content:center; align-items: center">
            <h4 id="notice_message" style="color: red ;font-size: 20px; width: 100%; text-align: center; margin-top: 240px"></h4>
        </div>
    </div>

</rapid:override>

<%@ include file="../base.jsp"%>