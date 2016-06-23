<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ page trimDirectiveWhitespaces="true" session="false"%>
<%--标签 --%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--自定义标签 --%>
<%@ taglib prefix="layout" uri="/WEB-INF/tag/layout.tld" %>
<%-- ctxPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />