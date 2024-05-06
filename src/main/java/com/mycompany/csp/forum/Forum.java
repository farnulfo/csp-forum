/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.csp.forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;

/**
 *
 * @author farnulfo
 */
public class Forum extends HttpServlet {

    private List<String> comments = new ArrayList<>();
    private String csp;
    private boolean escapeHtml = false;

    @Override
    public void init() {
        comments.add("Hello world !");
        comments.add("Hello world 2!");
        comments.add("Hello world 4!");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        
        if ("poster".equals(action)) {
            comments.add(request.getParameter("commentaire"));
        }

        if ("update_config".equals(action)) {
            csp = request.getParameter("csp");
            String escapeHtmlOnOff = request.getParameter("escapehtml");
            escapeHtml = escapeHtmlOnOff != null && escapeHtmlOnOff.equals("on");
        }

        response.setContentType("text/html;charset=UTF-8");
        if (csp != null) {
            response.setHeader("Content-Security-Policy", csp);
        }
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Forum</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Forum at " + request.getContextPath() + "</h1>");
            comments.forEach(c -> out.println("<div>Commentaire : " + (escapeHtml ?  StringEscapeUtils.escapeHtml4(c) : c) + "<div>"));

            out.println("        <form action=\"Forum\" method=\"get\">");
            out.println("            <textarea id=\"commentaire\" name=\"commentaire\" rows=\"5\" cols=\"33\"></textarea>");
            out.println("            <input type=\"submit\" value=\"Post!\" />");
            out.println("            <input type=\"hidden\" id=\"action\" name=\"action\" value=\"poster\">");
            out.println("        </form>");

            out.println("<hr>");
            out.println("<h1>Servlet Forum at " + request.getContextPath() + "</h1>");

            out.println("        <form action=\"Forum\" method=\"get\">");
            out.println("            <label for=\"name\">CSP value:</label>");
            out.print("            <input type=\"text\" id=\"csp\" name=\"csp\" value=\"");
            if (csp != null) {
                out.print(csp);
            }
            out.println("\">");
            out.println("        <fieldset>");
            out.println("          <legend>Escape HTML:</legend>");
            out.println("          <div>");
            out.println("        	<input type=\"checkbox\" id=\"on\" name=\"escapehtml\" " +
                    (escapeHtml ? "checked" : "")
                    + "/>");
            out.println("            <label for=\"scales\">on</label>");
            out.println("        </div>");
            out.println("        </fieldset>");
            
            out.println("            <input type=\"submit\" value=\"Post!\" />");
            out.println("            <input type=\"hidden\" id=\"action_csp\" name=\"action\" value=\"update_config\">");
            out.println("        </form>");


            
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
