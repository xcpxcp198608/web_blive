<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<rapid:override name="title">Error</rapid:override>
<rapid:override name="content">

    <h2 style="width: 100%; text-align: center; margin-top: 100px"><%=exception.getCause().getMessage() %></h2>

</rapid:override>

<%@ include file="WEB-INF/jsp/base.jsp"%>