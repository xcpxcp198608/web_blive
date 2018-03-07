<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<rapid:override name="title">
    Home
</rapid:override>
<rapid:override name="css_js">
    <script type="application/javascript" src="Resource/js/manager/home.js"></script>
</rapid:override>


<rapid:override name="content">

    <div class="row" style="padding: 0 10px 0 10px">
        <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
        <div class="col-5" style="padding: 10px; background-color: white">
            <span class="text-center text-muted" style="padding: 10px">User activation per day in a month summary:</span>
        </div>
        <div class="col-4 text-left text-darks" style="background-color: white; padding: 10px">
            <span><span id="aYear">2018</span>-<span id="aMonth">01</span></span>
        </div>
        <div class="col-3 text-right" style="padding: 10px; background-color: white">
            <a id="btPreviousMonth" data-toggle="tooltip" title="Previous Month">
                <span class="badge badge-info text-center">Previous</span>
            </a>
            <a id="btNextMonth" data-toggle="tooltip" title="Next Month">
                <span class="badge badge-info text-center">Next</span>
            </a>
        </div>
    </div>


    <div class="row" style="padding: 0 10px 0 10px">
        <div id="chartMonthVolume" style="width: 100%; height: 250px; background-color: white"></div>
    </div>

    <div class="row" style="padding: 0 10px 0 10px">
        <div style="background-color: #ffffff; padding: 10px 20px 0 20px; width: 100%; overflow: scroll">
            <table class="table table-sm table-hover table-striped table-dark" id="tbMonthVolume" style="overflow: scroll">
                <thead>
                <tr><th>MONTH</th></tr>
                </thead>
                <tbody>
                <td>0</td>
                </tbody>
            </table>
        </div>
    </div>



    <div class="row" style="padding: 10px 10px 0 10px">
        <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
        <div class="col-12" style="padding: 10px; background-color: white">
            <span class="text-center text-muted" style="padding: 10px">Detail of activated users per month in a year summary:</span>
        </div>
    </div>



    <div class="row" style="padding: 0 10px 0 10px">
        <div id="chartYearVolume" style="width: 100%; height: 250px; background-color: white"></div>
    </div>

    <div class="row" style="padding: 0 10px 0 10px">
        <div style="background-color: #ffffff; padding: 10px 20px 0 20px; width: 100%; overflow: scroll">
            <table class="table table-sm table-hover table-striped table-dark" id="tbYearVolume" style="overflow: scroll">
                <thead>
                <tr id="thYearVolume">
                    <th>YEAR</th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th>
                    <th>7</th><th>8</th><th>9</th><th>10</th><th>11</th><th>12</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>0</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td>
                    <td>0</td><td>0</td><td>0</td><td>0</td><td>0</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

</rapid:override>
<%@ include file="base_manager.jsp"%>