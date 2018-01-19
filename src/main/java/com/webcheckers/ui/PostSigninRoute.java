package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import javax.jws.WebService;
import javax.servlet.http.HttpSession;

/**
 * @author Cole Hornbeck - cdh6380
 *         Date: 10/4/2017.
 */
public class PostSigninRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    static final String SIGNIN_PARAM = "signinName";

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET / signin} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostSigninRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        LOG.config("PostSigninRoute is initialized.");
    }

    /**
     * Checks the validity of an entered name.
     * If the name contains a " it is invalid
     * If the name already exists it is invalid
     * @param name the name to check
     * @return The String error message to push.
     */
    private String validNameEntered(String name) {
        if (name.contains("\"") || name.isEmpty()) {
            return WebServer.SIGNIN_INVALID_NAME;
        } else if (playerLobby.playerAlreadySignedIn(new Player(name)) || playerLobby.profileExists(name)) {
            return WebServer.SIGNIN_NAME_ALREADY_IN_USE;
        } else {
            return WebServer.SIGNIN_VALID_NAME;
        }
    }

    /**
     * Render the WebCheckers Sign-In page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Sign-In page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSigninRoute is invoked");
        Map<String, Object> vm = new HashMap<>();
        //Grabs the sign-in name from signin.ftl
        String newName = request.queryParams(SIGNIN_PARAM);
        Player newPlayer = new Player(newName);
        Session session = request.session();


        //If its a new name, add to the list and render the home.
        String nameValidityResult;
        nameValidityResult = validNameEntered(newName);

        //If nameValidityResult is "Valid" (SIGNIN_VALID_NAME)
        if(nameValidityResult.equals(WebServer.SIGNIN_VALID_NAME)) {
            if (session.attributes().contains(WebServer.SIGNIN_ERROR)) { //Check if there has been an error
                session.attributes().remove(WebServer.SIGNIN_ERROR); //And remove the error, because its valid now
            }
            playerLobby.addPlayer(newName);
            session.attribute(WebServer.PLAYER_KEY,newPlayer);
            response.redirect(WebServer.HOME_URL);
            return null;
        } else { //Else its invalid, and redirect back to signin page
            session.attribute(WebServer.SIGNIN_ERROR,nameValidityResult);
            response.redirect(WebServer.SIGNIN_URL);
            return null;
        }
    }
}
