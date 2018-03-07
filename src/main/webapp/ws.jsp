<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>Test--BLive</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=1, minimum-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="format-detection" content="telephone=no"/>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.0.0/css/bootstrap.min.css"
    integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">

    <script src="https://code.jquery.com/jquery-3.2.1.js"></script>
    <script type="application/javascript" language="JavaScript" src="Resource/js/base.js"></script>
    <link rel="stylesheet" type="text/css" href="Resource/css/webuser/base.css">
    <link rel="shortcut icon" href="Resource/img/btv.ico">

</head>

<body>
    <div id="content" class="panel">
        <div class="input-group mb-2 mr-sm-2">
            <input type="number" class="form-control" id="type" aria-describedby="titleHelp"
                   placeholder="type" value="1">
        </div>
        <div class="input-group mb-2 mr-sm-2">
            <input type="number" class="form-control" id="group" aria-describedby="titleHelp"
                   placeholder="group" value="0">
        </div>
        <button type="button" class="btn btn-sm btn-primary" id="btConnect">Connect</button>
        <div class="input-group mb-2 mr-sm-2">
            <input type="text" class="form-control" id="comment" aria-describedby="titleHelp"
                   placeholder="comment" >
        </div>
        <button type="button" class="btn btn-sm btn-primary" id="btSend">Send</button>
    </div>
    <script type="text/javascript">
        $(function () {
            var ws = null;

            $('#btConnect').click(function () {
                var group = $('#group').val();
                ws = wsConnect(group)
            });

            function wsConnect(group) {
                var url = "ws://" + location.hostname + ":8080/blive/live/" + group + "/99999";
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
            
            $('#btSend').click(function () {
                var type = $('#type').val();
                var group = $('#group').val();
                var comment = $('#comment').val();
                if(comment.length <= 0){
                    return;
                }
                if(ws) {
                    ws.send(type + "/" + group + "/" + comment)
                }
            });

        })
    </script>











    <script src="https://cdn.bootcss.com/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://cdn.bootcss.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
</body>
</html>
