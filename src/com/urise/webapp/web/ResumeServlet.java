package com.urise.webapp.web;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    public void init(ServletConfig config) throws ServletException {
        storage = new SqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table style=\"width:100%\">");
        stringBuilder.append("<tr>");
        stringBuilder.append("<th>UUID</th>");
        stringBuilder.append("<th>FullName</th>");
        stringBuilder.append("</tr>");
        for (Resume resume : storage.getAllSorted()) {
            stringBuilder.append("<tr>");
            stringBuilder.append("<td>" + resume.getUuid() + "</td>");
            stringBuilder.append("<td>" + resume.getFullName() + "</td>");
            stringBuilder.append("</tr>");
        }
        stringBuilder.append("</table>");
        response.getWriter().write(stringBuilder.toString());
    }
}
