/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ashish
 */
public class MainServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MainServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MainServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            // This check prevents the "/" handler from handling all requests by default
            if (!"/".equals(request.getServletPath())) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            response.setContentType("text/html");
            try {
                // Create a state token to prevent request forgery.
                // Store it in the session for later validation.
                String state = new BigInteger(130, new SecureRandom()).toString(32);
                request.getSession().setAttribute("state", state);
                // Fancy way to read index.html into memory, and set the client ID
                // and state values in the HTML before serving it.
      /*          response.getWriter().print(new Scanner(new File("/login.xhtml"), "UTF-8")
                        .useDelimiter("\\A").next()
                        .replaceAll("[{]{2}\\s*CLIENT_ID\\s*[}]{2}", controllers.SignIn.CLIENT_ID)
                        .replaceAll("[{]{2}\\s*STATE\\s*[}]{2}", state)
                        .replaceAll("[{]{2}\\s*APPLICATION_NAME\\s*[}]{2}",
                        controllers.SignIn.APPLICATION_NAME)
                        .toString());
                response.setStatus(HttpServletResponse.SC_OK); */
            } catch (Exception e) {
                // When running the quickstart, there was some path issue in finding
                // index.html.  Double check the quickstart guide.
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().print(e.toString());
            }
        }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
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
