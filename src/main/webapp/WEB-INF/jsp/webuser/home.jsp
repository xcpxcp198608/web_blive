<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<rapid:override name="title">Home</rapid:override>
<rapid:override name="css_js">
    <script src="https://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <style>
        .message { font-size: 30px; position: absolute}
        .comment { font-size: 14px; color: #15fffc
        }
    </style>
    <script>
        $(function(){
            var baseUrl = "https://"+location.host;
            var webSocket = null;
            var living = false;

            var video = document.getElementById("video");
            if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
                navigator.mediaDevices.getUserMedia({
                    video: true,
                    audio: false
                }).then(function(stream) {
                    mediaStreamTrack = typeof stream.stop === 'function' ? stream : stream.getTracks()[1];
                    video.src = (window.URL || window.webkitURL).createObjectURL(stream);
                    video.play();
                }).catch(function(err) {
                    console.log(err);
                })
            }



            $('#btStart').click(function(){
                $(this).attr('disabled', 'disabled');
                var currentHtm = $(this).html();
                var activate = 0;
                if(currentHtm.search('Start') >= 0){
                    activate = 1;
                }else if (currentHtm.search('Stop') >= 0){
                    activate = 0
                }
                $.ajax({
                    type:'PUT',
                    url: baseUrl + "/channel/status/" + activate + "/" + ${userInfo.id},
                    dataType:'json',
                    success: function (response) {
                        $('#btStart').removeAttr('disabled');
                        if('Stop' === $('#btStart').html()){
                            $('#btStart').html('Start');
                            living = false;
                            if(webSocket != null){
                                webSocket.close();
                            }
                            showNotice('live stoped')
                        }else {
                            $('#btStart').html('Stop');
                            living = true;
                            webSocket = wsConnect();
                            showNotice('live started')
                        }
                    },
                    error: function () {
                        $('#btStart').removeAttr('disabled');
                        showNotice('failure')
                    }
                })
            });

            $('#btSend').click(function () {
                if(living == false){
                    showNotice("live no start");
                    return
                }
                var comment = $('#ipComment').val();
                if(comment.length <= 0){
                    return
                }
                if(webSocket == null){
                    return
                }
                webSocket.send('1/${userInfo.id}/' + comment);
                $('#ipComment').val("");
            });

            function wsConnect() {
                var url = "wss://" + location.hostname + "/live/${userInfo.id}/${userInfo.id}";
                var webSocket = null;
                if ('WebSocket' in window) {
                    webSocket = new WebSocket(url);
                }else {
                    return
                }

                webSocket.onopen = function(evt) {
                    console.log("Connection open ...");
                };

                webSocket.onmessage = function(evt) {
                    console.log( "Received Message: " + evt.data);
                    showComment(evt.data);
                };

                webSocket.onclose = function(evt) {
                    console.log("Connection closed.");
                };

                webSocket.onerror = function(evt) {
                    console.log("onerror");
                };

                window.onbeforeunload = function () {
                    webSocket.close();
                };
                return webSocket;
            }
            
            function showComment(comment) {
                if(comment.length <= 0){
                    return
                }
                var reg = /^blive group count:/;
                if(reg.test(comment)) {
                    var count = comment.substr(18);
                    count = parseInt(count)
                    $('#oCount').html(count - 1);
                }else{
                    var span = document.createElement("span");
                    span.innerHTML = comment + "<br/>";
                    span.setAttribute('class', 'comment');
                    $('#divComment').append(span);
                    $("#divComment").scrollTop($('#divComment').prop("scrollHeight"));
                }
            }

            $('#btSetting').click(function () {
                $('#modalSetting').modal('show');
            });

            $('#btUpload').click(function(){
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
                            $('#imgPreview').attr('src', response.data.preview);
                        }else{
                            showNotice(response.message)
                        }
                    },
                    error: function () {
                        console.log("ajax error");
                        showNotice('update failure')
                    }
                })
            });

            $('#btSubmitSetting').click(function () {
                var title = $('#title').val();
                var message = $('#message').val();
                var price = $('#price').val();
                $.ajax({
                    type: "PUT",
                    url: baseUrl + "/channel/update/0",
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify({"userId": ${userInfo.id}, "title": title,
                        "message": message, "price": price}),
                    dataType: "json",
                    beforeSend: function () {

                    },
                    success: function (response) {
                        console.log(response);
                        if(response.code == 200) {
                            $('#modalSetting').modal('hide');
                            showNotice(response.message);
                        }else{
                            $('#modalSetting').modal('show');
                            $('#errorSetting').html(response.message)
                        }
                    },
                    error: function () {
                        $('#modalSetting').modal('hide');
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
            
            
            $('#btLogout').click(function () {
                window.open('/', '_self')
            });
        })
    </script>
</rapid:override>


<rapid:override name="content">

    <div id="bg" style="background-image: url(Resource/img/lights.jpg); width: 100%; height: 100%;">
        <div style="width: 800px; height: 630px; margin: auto; position: relative">
            <div style="width: 800px; height: 30px; ">
                <div class="row">
                    <div class="col-6">
                        <span class="text-muted">URL:&nbsp;${channelInfo.rtmpUrl}</span>
                    </div>
                    <div class="col-6">
                        <span class="text-muted">KEY:&nbsp;${channelInfo.rtmpKey}</span>
                    </div>
                </div>
            </div>

            <div style="width: 800px; height: 600px; background-color: black;" >
                <div style="width: 800px; height: 600px;">
                    <video id="video" autoplay="" style='padding: 0; margin: auto; width: 100%;'></video>
                </div>

                <div style="position: relative; left: 0; top: -600px;
                    z-index: 10; margin-left: 5px; color: #ff0d42; height: 30px">
                    <span>viewers:</span><span id="oCount">0</span>
                </div>

                <div style="width: 585px; height:200px; position: relative; left: 0;top: -230px;
                    z-index: 10; overflow: scroll; overflow-y:hidden; overflow-x:hidden;  margin-left: 5px" id="divComment">

                </div>

                <div style="width: 205px; height: 40px; margin-right: 5px; position: relative; left: 73%; top: -270px; z-index: 10">
                    <div class=" text-right" style="margin: 5px 0 0 5px;">
                        <button type="button" class="btn btn-sm btn-warning" id="btSetting">
                            Setting
                        </button>
                        <button type="button" class="btn btn-sm btn-primary" id="btStart">
                            Start
                        </button>
                        <button type="button" class="btn btn-sm btn-danger" id="btLogout">
                            Logout
                        </button>
                    </div>
                </div>
            </div>
        </div><br/>

        <div style="margin: auto; width: 800px;">
            <div class="form-row">
                <div class="col-11">
                    <div class="input-group input-group-sm mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon1">Comment:</span>
                        </div>
                        <input type="text" id="ipComment" class="form-control form-control-sm" placeholder="">
                    </div>

                </div>
                <div class="col-1 text-right">
                    <button type="button" class="btn btn-sm btn-info" id="btSend">Send</button>
                </div>
            </div>
        </div>

    </div>




    <div class="modal fade" id="modalSetting" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h6 class="modal-title" >Upload</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div style="padding: 10px">
                        <div class="row">
                            <div class="col-10">
                                <form id="img_form" method="post" enctype="multipart/form-data" class="form-inline"
                                    action="/blive/channel/upload/${userInfo.id}">
                                    <div class="input-group mb-2 mr-sm-2">
                                        <input type="file" class="form-control-file" accept="image/png, image/jpeg"
                                               id="file" name="file" >
                                    </div>
                                </form>
                            </div>
                            <div class="col-2 text-right">
                                <button type="button" class="btn btn-sm btn-info" id="btUpload">Upload</button>
                            </div>
                        </div>

                        <div style="margin-bottom: 10px" >
                            <img id="imgPreview" style="width: 100%; margin: auto; max-height: 297px; min-height: 297px"
                                 src="${channelInfo.preview}"
                                 onerror="this.src='Resource/img/img_error_preview.jpg'"/>
                        </div>

                        <div class="input-group mb-2 mr-sm-2">
                            <input type="text" class="form-control" id="title" aria-describedby="titleHelp"
                                   placeholder="title" value="${channelInfo.title}">
                        </div>

                        <div class="input-group mb-2 mr-sm-2">
                            <textarea class="form-control" id="message" aria-describedby="messageHelp"
                                      placeholder="message" rows="2">${channelInfo.message}</textarea>
                        </div>


                        <div class="input-group mb-2 mr-sm-2">
                            <div class="input-group-prepend">
                                <div class="input-group-text">＄</div>
                            </div>
                            <input type="number" class="form-control" id="price" aria-describedby="priceHelp"
                                   placeholder=" " value="${channelInfo.price}">
                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <span id="errorSetting" class="badge badge-danger"></span>
                    <button type="button" class="btn btn-sm btn-primary" id="btSubmitSetting">Confirm</button>
                </div>
            </div>
        </div>
    </div>


    <div id="notice" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: 1001;
        background-color: rgba(0,0,0,0.3); display: none">
        <div style="display: flex; display: -webkit-flex; flex-direction:row; justify-content:center; align-items: center">
            <h4 id="notice_message" style="color: red ;font-size: 20px; width: 100%; text-align: center; margin-top: 240px"></h4>
        </div>
    </div>

</rapid:override>

<%@ include file="base.jsp"%>