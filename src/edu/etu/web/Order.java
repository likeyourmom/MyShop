package edu.etu.web;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import Models.PurchaseList;
import dbTools.OrdersEntity;
import dbTools.OrderService;

public class Order extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userName = (String)session.getAttribute("username");

        PurchaseList purchasesList = (PurchaseList)session.getAttribute("purchases");
        String purchases = purchasesList.getStr(purchasesList);

        OrdersEntity order;

        String withCurier = request.getParameter("type-choice");
        if(withCurier!=null && withCurier.equals("on")){
            String addressee = request.getParameter("addressee");
            order = new OrdersEntity(userName, purchases, (byte)1, addressee);
        } else {
            int shopId = Integer.parseInt(request.getParameter("shop-choice"));
            order = new OrdersEntity(userName, purchases, (byte)0, shopId);
        }

        OrderService.saveOrder(order);

        session.setAttribute("purchases",null);
        session.setAttribute("order_success", true);

        response.addCookie(new Cookie("one", null));
        response.addCookie(new Cookie("two", null));
        response.addCookie(new Cookie("three", null));

        response.sendRedirect(".");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/order.jsp").forward(request,response);
    }
}