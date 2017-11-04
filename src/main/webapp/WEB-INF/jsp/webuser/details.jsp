<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<rapid:override name="title">Details</rapid:override>
<rapid:override name="content">
    <div>
        <table class="table table-hover table-striped">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Value</th>
                </tr>
            </thead>
            <tbody>
                <tr><td>username</td><td>${userInfo.username}</td></tr>
                <tr><td>email</td><td>${userInfo.email}</td></tr>
                <tr><td>stream</td><td>${userInfo.channelInfo.url}</td></tr>
            </tbody>
        </table>

    </div>
</rapid:override>

<%@ include file="../base.jsp"%>