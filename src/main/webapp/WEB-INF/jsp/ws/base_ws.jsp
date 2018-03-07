<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<rapid:override name="navigation">
    <div class="sidebar-sticky">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link active" href="/panel/manager/home">
                    <i class="fa fa-home fa-lg"></i>&nbsp;Home
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/manager/users">
                    <i class="fa fa-users fa-lg"></i>&nbsp;Customers
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/manager/level">
                    <i class="fa fa-sort-amount-desc fa-lg"></i>&nbsp;Level
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/manager/distribution">
                    <i class="fa fa-globe fa-lg"></i>&nbsp;Geographic
                </a>
            </li>
            <c:if test="${permission > 100}">
                <li class="nav-item">
                    <a class="nav-link" href="/panel/manager/logs">
                        <i class="fa fa-building fa-lg"></i>&nbsp;Logs
                    </a>
                </li>
            </c:if>
            <li class="nav-item">
                <a class="nav-link" href="/panel/signout1">
                    <i class="fa fa-sign-out fa-lg"></i>&nbsp;Sign out
                </a>
            </li>
        </ul>

    </div>
</rapid:override>

<%@ include file="base.jsp"%>