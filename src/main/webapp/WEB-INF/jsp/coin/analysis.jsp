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

    <title>Coins</title>

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
    <link rel="stylesheet" type="text/css" href="Resource/css/coin/analysis.css">
    <link rel="shortcut icon" href="Resource/img/btv.ico">
    <script type="application/javascript" src="Resource/js/coin/analysis.js"></script>

</head>

<body style="background-color: #242527">

    <div class="container">
        <div class="row">
            <div class="col-3 text-left">
                <div>
                    <button type="button" class="btn btn-primary btn-sm" id="btPrevious"><<<</button>
                </div>
            </div>
            <div class="col-6 text-center">
                <div>
                    <span class="text-warning" id="currentDate"></span>
                </div>
            </div>
            <div class="col-3 text-right">
                <div>
                    <button type="button" class="btn btn-primary btn-sm" id="btNext">>>></button>
                </div>
            </div>
        </div>
    </div>

    <div style="display: none">
        <span id="userId" >${userId}</span>
    </div>


    <div style="padding: 10px">
        <div id="chart_month">

        </div>
    </div>


    <div style="padding: 10px">
        <div id="chart_days">

        </div>
    </div>





    <script src="https://cdn.bootcss.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
</body>
</html>
