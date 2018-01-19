package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;

  static final String PLAYERSERVICES_KEY = "playerServices";

  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /} HTTP request.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.templateEngine = templateEngine;
    this.playerLobby = playerLobby;
    //
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");
    Session session = request.session();
    Player thisPlayer;
    //Checks if there is a player signed in to this session
    if (session.attributes().contains("playerKey")) {
      thisPlayer = session.attribute("playerKey");
      //checks if the signed in player is currently in a game
      if (playerLobby.playerInGame(thisPlayer)) {
        thisPlayer = playerLobby.getPlayer(thisPlayer.getName());
        session.attribute("playerKey", thisPlayer);
        //Builds a new game view based on player, opponent, and a new board with different orientation

        //Sets the players game variable to this new game
         thisPlayer.setGame(thisPlayer.getGame(), thisPlayer.getPlayerColor(), thisPlayer.getOpponent());
        //Adds this new game to the session attributes
        session.attribute("GameKey", thisPlayer.getGame());
        //Redirects to the webserver URL for the Game
        response.redirect(WebServer.GAME_URL);
        return null;
      }
    }
    //
    Map<String, Object> vm = new HashMap<>();
    if (session.attributes().contains("playerKey")) {
      if (session.attribute("playerKey") instanceof Player) {
        thisPlayer = session.attribute("playerKey");
        vm.put("playerName", session.attribute("playerKey"));
        vm.put("lobbyList", new ArrayList<>(playerLobby.getInLobbyList().values()));
        vm.put("inGameList", new ArrayList<>(playerLobby.getGameList().values()));
        vm.put("WonGames", thisPlayer.getWon());
        vm.put("PlayedGames", thisPlayer.getPlayed());
        if(thisPlayer.getPlayed() == 0) {
          vm.put("WinAverage", 0);
        } else {
          vm.put("WinAverage", (double) (thisPlayer.getWon()) / (double) (thisPlayer.getPlayed()));
        }
      }
    }

    if (session.attributes().contains("INVALID_OPPONENT")) {
      vm.put("invalidOpponentMessage", session.attribute("INVALID_OPPONENT"));
    }
    if (session.attributes().contains(WebServer.GAME_WINNER)) {
      vm.put("gameWinner", session.attribute(WebServer.GAME_WINNER));
      session.attribute(WebServer.GAME_WINNER, null);

      vm.put("winReason", session.attribute(WebServer.GAME_WIN_MODE));
      session.attribute(WebServer.GAME_WIN_MODE, null);
    }
    vm.put("title", "Welcome!");
    vm.put("totalPlayers", playerLobby.getInLobbyList().size() + playerLobby.getGameList().size());

    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }

}