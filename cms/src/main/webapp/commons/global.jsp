<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ page trimDirectiveWhitespaces="true" session="false"%>
<%--标签 --%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--自定义标签 --%>
<%@ taglib prefix="layout" uri="http://www.hr-soft.cn/tags/jsp-layout.tld" %>
<%@ taglib prefix="assets" uri="http://www.hr-soft.cn/tags/assets.tld" %>
<%@ taglib prefix="x" uri="http://www.hr-soft.cn/tags/jsp-tags.tld" %>
<%@ taglib prefix="db" uri="http://www.hr-soft.cn/tags/db-tags.tld" %>
<%-- ctxPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />