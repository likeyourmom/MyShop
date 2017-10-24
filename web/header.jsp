<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String lang = (String)request.getSession().getAttribute("locale");

    //System.out.println("header: " + lang);

    Locale locale;
    if ("en".equals(lang)) {
        locale = new Locale("en", "GB");
    } else if ("kz".equals(lang)) {
        locale = new Locale("kz", "KZ");
    } else if ("ru".equals(lang)) {
        locale = new Locale("ru", "RU");
    }else {
        locale = Locale.getDefault();
    }
    ResourceBundle res = ResourceBundle.getBundle("res", locale);
%>

<div class='header'>
    <ul id='lang'>
        <li><a href='?lang=en'>ENG</a></li>
        <li><a href='?lang=ru'>RUS</a></li>
        <li><a href='?lang=kz'>KAZ</a></li>
    </ul>

    <ul id='menu'>
        <li><a href='.'><%= res.getString("main")%></a></li>
        <li><a href='#'><%= res.getString("about")%></a></li>
        <li><a href='#'><%= res.getString("contacts")%></a></li>
    </ul>

    <jsp:include page="user-info.jsp"/>
</div>