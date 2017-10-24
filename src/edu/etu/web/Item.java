package edu.etu.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class Item extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lang = request.getParameter("lang");
        HttpSession ss = request.getSession();
        Cookie[] cookies = request.getCookies();

        if(lang == null) {
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("lang".equals(c.getName()))
                        lang = c.getValue();

                    if ("user".equals(c.getName()))
                        ss.setAttribute("username", c.getValue());
                }
            }else{
                lang = getInitParameter("lang");
            }
        }
        ss.setAttribute("locale", lang);
        response.addCookie(new Cookie("lang",lang));

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

        int parameter = -1;
        if(cookies != null) {
            for (Cookie c : cookies) {
                if ("choise".equals(c.getName()))
                    parameter = Integer.parseInt(c.getValue());
            }
        }

        if(parameter == -1)
            parameter = Integer.parseInt(getInitParameter("choise"));

        ResourceBundle bundle = ResourceBundle.getBundle("res", locale);
        String id = request.getParameter("id");
        int _id;
        try {
            _id = Integer.parseInt(id);
        } catch (Exception ex){
            _id = 0;
        }

        if(id == null || _id < 1 || _id > 3) {
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("id".equals(c.getName()))
                        id = c.getValue();
                }
            }else{
                id = "1";
            }
        }
        response.addCookie(new Cookie("id", id));


        String description;


        switch (parameter){
            case 1: description = bundle.getString("discr" + id); break;
            case 2: description = bundle.getString("cal" + id); break;
            case 3: description = "Отзывов нет..."; break;
            default: description = bundle.getString("discr" + id);
        }

        String userinfo = "<a onclick='showform()'>" + bundle.getString("enter") + "</a>";
        String user = (String)ss.getAttribute("username");
        if(user != null && user != "")
            userinfo = "<a href='./cabinet' id='lll'>" + bundle.getString("loginas") + " <b>" + user + "</b></a>";

        StringBuilder sb = new StringBuilder();
        sb.append("<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<title>" + bundle.getString("title") + "</title>" +
                "<link rel=\"stylesheet\" href=\"main.css\">" +
                "<script src='./js/cart_worker.js'></script>" +
                "<script src='./js/login_worker.js'></script>" +
                "<script type='text/javascript'>" +
                "function changeText(param){" +
                "    document.getElementById('plus_discr').innerHTML = param;" +
                "}" +
                "</script>");
        sb.append("</head>" + "<body onLoad=\"changeText('" + description + "')\">");

        //Wrapper
        sb.append("<div class='wrapper'>");
        //Header
        sb.append("	<div class='header'>");
        sb.append("<ul id='lang'>");
        sb.append("<li><a href='?lang=en'>ENG</a></li>");
        sb.append("<li><a href='?lang=ru'>RUS</a></li>");
        sb.append("<li><a href='?lang=kz'>KAZ</a></li>");
        sb.append("</ul>");

        sb.append("<ul id='menu'>");
        sb.append("<li><a href='.'>" + bundle.getString("main") + "</a></li>");
        sb.append("<li><a href='#'>" + bundle.getString("about") + "</a></li>");
        sb.append("<li><a href='#'>" + bundle.getString("contacts")+ "</a></li>");
        sb.append("</ul>");
        sb.append("<ul id='menu' class='user_info' style='width: auto'>" +
        "<li>" + userinfo + "</li>" +
        "<li><a href='/cart'>" + bundle.getString("cart") + "</a></li>" +
        "<li><a>" + bundle.getString("history") + "</a></li>" +
        "</ul>");
        sb.append("<div id='overlay' onclick='closeform()'></div>" +
                "<div id='login-form'>" +
                "<h1>" + bundle.getString("enter") + "</h1>" +
                "<input type='text' id='name' placeholder='Name'/>" +
                "<a class='button10' onclick='enter()'>OK</a>" +
                "</div>");
        sb.append("	</div>");
        //Header end

        //Container
        sb.append("	<div id='container'>");

        sb.append(" <div class='box'>");
        sb.append("          <div class='image'><img src='img/items/" + id + ".png'></div>");
        sb.append("          <div style=\"width: 890px;\">" +
                "<h1>" + bundle.getString("name" + id) + "</h1><hr  noshade color='#D27B43'>");
        sb.append("<div id='plus_discr' class='cal'></div>");
        sb.append("<span class='discr'>");
        sb.append("  <a onclick=\"to_cart(" + id + ")\">" +  bundle.getString("to_cart") + " | <span class='price'>" +  bundle.getString("cost" + id) + " $</span></a>");
        sb.append("  <a onclick=\"changeText('" + bundle.getString("discr" + id) + "')\">" + bundle.getString("discr_full") + "</a>");
        sb.append("  <a onclick=\"changeText('" + bundle.getString("cal" + id) + "')\">" + bundle.getString("discr") + "</a>");
        sb.append("  <a onclick=\"changeText('Отзывов нет...')\">" +  bundle.getString("reviews") + "</a>");
        sb.append("</span>");
        sb.append("</div>");
        sb.append("       </div>");

        sb.append("</div>");
        //Container end
        sb.append("<div id='popup1'>" +
        "<span class='info'>Товар добавлен в корзину!<br><a href='/cart'>Оформить заказ</a></span>" +
        "</div>)");
        sb.append("</div>");
        //Wrapper end

        sb.append("</body></html>");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println(sb.toString());
        out.close();
    }
}