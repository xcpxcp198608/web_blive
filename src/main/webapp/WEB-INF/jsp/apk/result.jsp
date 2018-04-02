<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>B·VISION</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="format-detection" content="telephone=no"/>
    <link rel="shortcut icon" href="img/btv.ico">
    <style>
        * {
            margin: 0;
            padding: 0;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
        }
        html,body {
            width: 100%;
            height: 100%;
            background-color: #1C1C20;
        }
        .tips {
            display: none;
        }
        .tips img {
            width: 100%;
        }
    </style>


</head>

<body>

<div id="tips" class="tips">

    <img src="/Resource/img/tips.png" />

</div>

<script type="text/javascript">
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        var tips = document.getElementById('tips');
        tips.style.display = 'block';
    }else{
        if (ua.indexOf('iphone') > 0) {
            window.location.href = "https://itunes.apple.com/us/app/b-vision/id1364359526?ls=1&mt=8";
        } else if (ua.indexOf('android') > 0) {
            window.location.href = "http://blive.bvision.live:8804/upgrade/com.wiatec.blive_v1.0.2_102_20171221_release.apk";
        } else if (ua.indexOf('windows phone') > 0) {
            window.location.href = "";
        } else if (ua.indexOf('ipad') > 0) {
            window.location.href = "";
        }
    }

</script>

</body>
</html>
