package edu.etu.web;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import Models.PurchaseList;
import dbTools.OrderEntity;
import dbTools.OrderService;

public class Order extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userName = (String)session.getAttribute("username");

        PurchaseList purchasesList = (PurchaseList)session.getAttribute("purchases");
        String purchases = purchasesList.getStr(purchasesList);
        OrderEntity order;

        String withCurier = request.getParameter("type-choice");
        if(withCurier!=null && withCurier.equals("on")){
            String addressee = request.getParameter("addressee");
            order = new OrderEntity(userName, purchases, (byte)1, addressee);
        } else {
            int shopId = Integer.parseInt(request.getParameter("shop-choice"));
            order = new OrderEntity(userName, purchases, (byte)0, shopId);
        }

        OrderService.saveOrder(order);
        PurchaseList list = new PurchaseList();
        session.setAttribute("purchases",list);

        String path = "/success.jsp";
        getServletContext().getRequestDispatcher(path).forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/order.jsp").forward(request,response);
    }
}