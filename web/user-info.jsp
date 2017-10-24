<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String lang = (String)request.getSession().getAttribute("locale");
    //System.out.println("user info: " + lang);

    HttpSession ss = request.getSession();

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

    String user = (String)ss.getAttribute("username");
%>

<ul id='menu' class="user_info" style="width: auto">
    <li><%
        if(user == "" || user == null){
        %>
        <a href="/login"><%=res.getString("enter")%></a>
        <%}else{%>
            <a href="./cabinet" id="lll"><%=res.getString("loginas")%> <b><%= user%></b></a>
        <%
        }
        %></li>
    <li><a href="/cart"><%= res.getString("cart")%></a></li>
    <li><a><%= res.getString("history")%></a></li>
</ul>

<div id="overlay" onclick="closeform()"></div>
<div id="login-form">
    <h1><%=res.getString("need_auth")%></h1>
    <%--<input type="text" id="name" placeholder="Name"/>--%>
    <a class="button10" onclick="closeform()">OK</a>
   <%--<iframe src="auth/auth.jsp" scrolling="no" width="100%"/>--%>
</div>