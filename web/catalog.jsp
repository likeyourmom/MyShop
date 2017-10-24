<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="./main.css" type="text/css">
    <script src="./js/sort_type.js"></script>
    <script src="./js/login_worker.js"></script>
    <title>Serjik</title>

    <jsp:useBean id="kumissBean" class="Models.Kumiss" scope="session"/>
</head>
<body onload="sort()">
<div class="wrapper">
    <jsp:include page="header.jsp"/>
        <div id='container'>
            <span id="sort"><a onclick="sort(0)" id="a0">*</a> | <a onclick="sort(1)" id="a1">0.5</a> | <a onclick="sort(2)" id="a2">1.0</a> | <a onclick="sort(3)" id="a3">10</a></span>

            <%
                String img;
                for(int i = 1; i < 4; i++){
                    img = "./img/items/" + Integer.toString(i) + ".png";
            %>
                    <jsp:setProperty name="kumissBean" property="id" value="<%= i%>"/>
                    <jsp:setProperty name="kumissBean" property="imageUrl" value="<%=img%>"/>
                    <jsp:include page="item.jsp"/>
            <%
                }
            %>
        </div>
    <jsp:include page="footer.jsp"/>
    <div id="popup1">
        <span class="info">Товар добавлен в корзину!<br><a href="/cart">Оформить заказ</a></span>
    </div>
</div>
</body>
</html>
