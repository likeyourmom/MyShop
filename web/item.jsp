<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="edu.etu.web.Catalog" %>

<head>
    <%
        String lang = (String)request.getSession().getAttribute("locale");

        //System.out.println("item: " + lang);

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
    <jsp:useBean id="kumissBean" class="Models.Kumiss" scope="session"/>

    <script src="./js/cart_worker.js"></script>
</head>

<%
    int id = kumissBean.getId();
    kumissBean.setName(res.getString("name" + Integer.toString(id)));
    kumissBean.setDescription(res.getString("discr" + Integer.toString(id)));
    kumissBean.setPrice(Double.parseDouble(res.getString("cost" + Integer.toString(id))));
%>

<div id="i<%= id%>" class="box">
    <div class="image">
        <img src="<jsp:getProperty name="kumissBean" property="imageUrl"/>">
    </div>
    <div style="width: 890px">
        <h1><a href="./item?id=<%=Integer.toString(id)%>"><jsp:getProperty name="kumissBean" property="name"/></a></h1>
        <hr  noshade color='#D27B43'>
        <div class="cal"><jsp:getProperty name="kumissBean" property="description"/></div>
        <span class='discr'><a onclick="to_cart(<%= id%>)"><%=res.getString("to_cart")%> | <span class="price"><jsp:getProperty name="kumissBean" property="price"/> $</span></a></span>
    </div>
</div>
