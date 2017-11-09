package edu.etu.web;

import dbTools.CommentService;
import dbTools.CommentEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Comments extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("text");
        String userName = (String)request.getSession().getAttribute("username");

        CommentEntity comment = new CommentEntity(text,userName);
        CommentService.saveComment(comment);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder allCommentsHtml = new StringBuilder();

        String userName = (String)request.getSession().getAttribute("username");
        ArrayList<CommentEntity> comments = CommentService.getUserAllComments(userName);

        for (CommentEntity comment: comments) {
            allCommentsHtml.append("<div class='comment'>");
            allCommentsHtml.append("	<span class='c_info'><b>" + comment.getUserName() + "</b> <i>" + comment.getCommentDate() + "</i></span>");
            allCommentsHtml.append("	<hr noshade>");
            allCommentsHtml.append("	<p>" + comment.getText() + "</p>");
            allCommentsHtml.append("</div>");
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(allCommentsHtml.toString());
    }
}
