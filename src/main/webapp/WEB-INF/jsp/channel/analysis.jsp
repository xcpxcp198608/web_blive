<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>Channel</title>

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

    <script type="application/javascript" src="https://cdn.bootcss.com/echarts/4.0.2/echarts.min.js" ></script>
    <script src="https://code.jquery.com/jquery-3.2.1.js"></script>
    <script type="application/javascript" language="JavaScript" src="Resource/js/base.js"></script>
    <link rel="stylesheet" type="text/css" href="Resource/css/base.css">
    <link rel="stylesheet" type="text/css" href="Resource/css/channel/analysis.css">
    <link rel="shortcut icon" href="Resource/img/btv.ico">
    <script type="application/javascript" src="Resource/js/channel/analysis.js"></script>

</head>

<body style="background-color: #242527">


    <div style="display: none">
        <span id="userId" >${userId}</span>
    </div>


    <div class="container">
        <div class="row">
            <div class="col-6 text-center">
                <div>
                    <span class="text-center" style="color: #10c6ff; font-size: 32px; font-style: revert" >${views}</span><br>
                    <span class="text-center" style="color: #bababa">Views</span>
                </div>
            </div>
            <div class="col-6 text-center">
                <div>
                    <span class="text-center" style="color: #56ffab; font-size: 32px; font-style: revert" >${viewers}</span><br>
                    <span class="text-center" style="color: #bababa">Viewers</span>
                </div>
            </div>
        </div>
    </div>

    <div class="container" style="margin-bottom: 20px; margin-bottom: 20px">
        <div id="chart_days" style="background-color: #151617">
        </div>
    </div>

    <div class="container" style="margin-bottom: 20px; margin-bottom: 20px">
        <div id="chart_hours" style="background-color: #151617">
        </div>
    </div>

    <div class="container">
        <div id="list_views" style="background-color: #151617">
            <table class="table table-sm table-hover table-dark" id="tbAllDevices" style="margin-top: 5px; font-size: 12px">
                <thead>
                <tr class="text-center">
                    <th>#</th>
                    <th>Username</th>
                    <th>Views</th>
                    <th>Duration</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${liveViewersInfoList}" var="liveViewersInfo" varStatus="status">
                        <tr class="text-center">
                            <td>${status.index + 1}</td>
                            <td>${liveViewersInfo.username}</td>
                            <td>${liveViewersInfo.count}</td>
                            <td>${liveViewersInfo.duration}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>





    <script src="https://cdn.bootcss.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
</body>
</html>
