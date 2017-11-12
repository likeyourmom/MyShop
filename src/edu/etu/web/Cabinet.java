package edu.etu.web;

import dbTools.OrderService;
import dbTools.OrdersEntity;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class Cabinet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String choise = request.getParameter("choise");

        response.addCookie(new Cookie("choise", choise));
        request.getSession().setAttribute("choise", choise);

        log(new Date().toString()+": пользователь " + request.getSession().getAttribute("username") + " изменил фильтр по умолчанию");

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Cookie[] cookies = request.getCookies();
            String lang = request.getParameter("lang");
            String choise = (String)request.getSession().getAttribute("choise");

            if(lang == null) {
                for (Cookie c : cookies) {
                    if ("lang".equals(c.getName()))
                        lang = c.getValue();
                }
            }
            request.getSession().setAttribute("locale", lang);
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
            ResourceBundle bundle = ResourceBundle.getBundle("res", locale);

            StringBuilder sb = new StringBuilder();
            sb.append("<html>" +
                    "<head>" +
                    "<meta charset='UTF-8'>" +
                    "<title>" + bundle.getString("title") + "</title>" +
                    "<script src='./js/cabinet_worker.js'></script>" +
                    "<script src='./js/comments_worker.js'></script>" +
                    "<link rel=\"stylesheet\" href=\"main.css\">" +
                    "<link rel=\"stylesheet\" href=\"login_form.css\">");
            sb.append("</head>" + "<body onload='checked(" + choise + ");loadComments()'>");

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
                    "<li><a href='/cart'>" + bundle.getString("cart") + "</a></li>" +
                    //"<li><a>" + bundle.getString("history") + "</a></li>" +
                    "</ul>");
            //Header end
            sb.append("	</div>");

            //Container
            sb.append("	<div id='container' style='padding: 0px 30px;'>");

            String user = (String)request.getSession().getAttribute("username");
            ArrayList<OrdersEntity> orders = OrderService.getUserAllPurchases(user);

            sb.append("<div class='cabinet_block' style='margin: 15px 0px; overflow: hidden;'>");
            sb.append("<div id='datetime'></div>");
            sb.append("<script>enableDateTimer()</script>");
            sb.append("<span id='cabinet_name'>" + bundle.getString("loginas") + " <u>" + user + "</u></span>");

            sb.append("<form action='./cabinet' method='post' id='cabinet_form'>");
            sb.append("	<h2>" +  bundle.getString("def") + ": </h2>");
            sb.append("    <span><input id='c1' class='option-input radio' name='choise' type='radio' value='1'>" +  bundle.getString("discr_full") + "</span><br>");
            sb.append("    <span><input id='c2' class='option-input radio' name='choise' type='radio' value='2'>" +  bundle.getString("discr") + "</span><br>");
            sb.append("    <span><input id='c3' class='option-input radio' name='choise' type='radio' value='3'>" +  bundle.getString("reviews") + "</span><br>");
            sb.append("    <span><button class=\"button\" type=\"submit\">OK</button></span>");
            sb.append(" </form>");
            sb.append("    <span><a href='./exit' class=\"button\">Exit</a></span>");
            sb.append("</div>");

            sb.append("<div class='cabinet_block korpus'>");
            sb.append("<input type='radio' name='odin' checked='checked' id='vkl1'/>");
            sb.append("<label for='vkl1'>" + bundle.getString("history") + "</label>");
            sb.append("<input type='radio' name='odin' id='vkl2'/>");
            sb.append("<label for='vkl2'>" + bundle.getString("reviews") + "</label>");
            sb.append("<hr noshade color='#D27B43' style='width: 890px; margin: 0px'>");
            sb.append("<div>");
            if(orders.size() > 0){
                String curier, adr;

                //sb.append("<h1 class='c_title'>" + bundle.getString("history") + "</h1>");
                //sb.append("<hr noshade size='5' color='#D27B43'>");
                sb.append("<table id='cart_table' width='880px'>");
                sb.append("	<thead>");
                sb.append("		<td width='125px'>" + bundle.getString("date") + "</td><td>" + bundle.getString("list") + "</td><td>" + bundle.getString("curier") + "</td><td>" + bundle.getString("Addresse") + "</td>");
                sb.append("	</thead>");
                for(OrdersEntity order : orders) {
                    if(order.getWithCurier() == 0){
                        curier = bundle.getString("no");
                        adr = bundle.getString("title") + " #" + Integer.toString(order.getShopId());
                    }else{
                        curier = bundle.getString("yes");
                        adr = order.getAddressee();
                    }

                    sb.append("<tr>");
                    sb.append("<td>" + order.getOrderDate() + "</td>" +
                            "<td>" + order.getPurchases() + "</td>" +
                            "<td>" + curier + "</td>" +
                            "<td>" + adr + "</td>");
                    sb.append("</tr>");
                }
                sb.append("</table>");
            }else{
                sb.append("<span style='display: block; margin: 10px; font-style: italic; color: #de8247'>EMPTY</span>");
            }
            sb.append("</div>");

            sb.append("<div id='comments-area'>");
            //sb.append("<h1 class='c_title'>" + bundle.getString("reviews") + "</h1>");
            //sb.append("<hr noshade size='5' color='#D27B43'>");
            sb.append("<div id='comments'></div>");
            sb.append("<textarea id='message' placeholder='Оставьте ваш отзыв!' maxlength=128></textarea>");
            sb.append("<button class='button' onclick='sendComment()'>Send</button>");
            sb.append("	</div>");
            sb.append("	</div>");

            sb.append("</div>");
            //Container end
            sb.append("<footer>");
            sb.append("    <div id='about'>Еськов Артемий, 5383</div>");
            sb.append("</footer>");
            sb.append("</div>");
            //Wrapper end

            sb.append("</body></html>");
            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = response.getWriter();
            out.println(sb.toString());
            out.close();
    }
}