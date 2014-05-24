/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gson.Gson;
import static controllers.SignIn.CLIENT_ID;
import static controllers.SignIn.CLIENT_SECRET;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ashish
 */
@WebServlet(name = "DisconnectServlet", urlPatterns = {"/disconnect","/*/disconnect"})
public class DisconnectServlet extends HttpServlet {

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

        // Only disconnect a connected user.
        String tokenData = (String) request.getSession().getAttribute("token");
        if (tokenData == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(GSON.toJson("Current user not connected."));
            return;
        }
        try {
            // Build credential from stored token data.
            GoogleCredential credential = new GoogleCredential.Builder()
                    .setJsonFactory(JSON_FACTORY)
                    .setTransport(TRANSPORT)
                    .setClientSecrets(CLIENT_ID, CLIENT_SECRET).build()
                    .setFromTokenResponse(JSON_FACTORY.fromString(
                    tokenData, GoogleTokenResponse.class));
            // Execute HTTP GET request to revoke current token.
            HttpResponse revokeResponse = TRANSPORT.createRequestFactory()
                    .buildGetRequest(new GenericUrl(
                    String.format(
                    "https://accounts.google.com/o/oauth2/revoke?token=%s",
                    credential.getAccessToken()))).execute();
            // Reset the user's session.
            request.getSession().removeAttribute("token");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(GSON.toJson("Successfully disconnected."));
        } catch (IOException e) {
            // For whatever reason, the given token was invalid.
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print(GSON.toJson("Failed to revoke token for given user."));
        }
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
