/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gson.Gson;
import static controllers.SignIn.CLIENT_ID;
import static controllers.SignIn.CLIENT_SECRET;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ashish
 */
@WebServlet(name = "ConnectServlet", urlPatterns = {"/connect"})
public class ConnectServlet extends HttpServlet {

    private static final Gson GSON = new Gson();
    /*
     * Default HTTP transport to use to make HTTP requests.
     */
    private static final HttpTransport TRANSPORT = new NetHttpTransport();

    /*
     * Default JSON factory to use to deserialize JSON.
     */
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">


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
        response.setContentType("application/json");

        // Only connect a user that is not already connected.
        String tokenData = (String) request.getSession().getAttribute("token");
        if (tokenData != null) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(GSON.toJson("Current user is already connected."));
            return;
        }

        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        getContent(request.getInputStream(), resultStream);
        String code = new String(resultStream.toByteArray(), "UTF-8");

        try {
            // Upgrade the authorization code into an access and refresh token.
            GoogleTokenResponse tokenResponse =
                    new GoogleAuthorizationCodeTokenRequest(TRANSPORT, JSON_FACTORY,
                    CLIENT_ID, CLIENT_SECRET, code, "postmessage").execute();

            // You can read the Google user ID in the ID token.
            // This sample does not use the user ID.
            GoogleIdToken idToken = tokenResponse.parseIdToken();
            String gplusId = idToken.getPayload().getSubject();
            System.out.println("openid : " + gplusId);
            System.out.println("domain : " + idToken.getPayload().getHostedDomain());
            System.out.println("email : " + idToken.getPayload().getEmail());
            // Store the token in the session for later use.
            Cookie token = new Cookie("token", tokenResponse.toString());
            response.addCookie(token);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(GSON.toJson("Successfully connected user."));
        } catch (TokenResponseException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print(GSON.toJson("Failed to upgrade the authorization code."));
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print(GSON.toJson("Failed to read token data from Google. "
                    + e.getMessage()));
        }
    }

    /*
     * Read the content of an InputStream.
     *
     * @param inputStream the InputStream to be read.
     * @return the content of the InputStream as a ByteArrayOutputStream.
     * @throws IOException 
     */
    static void getContent(InputStream inputStream, ByteArrayOutputStream outputStream)
            throws IOException {
        // Read the response into a buffered stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        int readChar;
        while ((readChar = reader.read()) != -1) {
            outputStream.write(readChar);
        }
        reader.close();
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
