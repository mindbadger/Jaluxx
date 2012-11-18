package com.mindbadger.jaluxx.gamemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mindbadger.jaluxx.Action;
import com.mindbadger.jaluxx.ActionType;
import com.mindbadger.jaluxx.JaluxxException;
import com.mindbadger.jaluxx.Game;
import com.mindbadger.jaluxx.Pack;
import com.mindbadger.jaluxx.Player;
import com.mindbadger.jaluxx.PlayerStatus;


public class GameManager {
	Logger logger = Logger.getLogger(GameManager.class);
	
	private Map<String, Player> registeredPlayers = new HashMap<String, Player> ();
	private List<Action> actions = new ArrayList<Action> ();
	private Map<String, Game> games = new HashMap<String, Game> ();
	
	private Pack pack = new Pack ();
	
	public GameManager () {
		logger.debug("GameManager starting up");
	}

	public Player registerNewPlayer(Player player) {
		if (registeredPlayers.containsKey(player.getName())) {
			Player matchingPlayer = registeredPlayers.get(player.getName());
			
			if (matchingPlayer.getPassword().equals(player.getPassword())) {
				return matchingPlayer; 
			} else {
				logger.error("User already exists. Password wrong");
				throw new JaluxxException (player.getName() + " has already registered with a different password. Please use a unique name or the correct password.");
			}
		} else {
			registeredPlayers.put(player.getName(), player);
			addAction (ActionType.LOGGED_IN, player);
		}
		
		return player;
	}

	
	protected void addAction (ActionType type, Player player) {
		actions.add(new Action (type, player));
	}
	
	public Map<String, Player> getRegisteredPlayers() {
		return registeredPlayers;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void startNewGameForPlayer(Player player) {
		logger.debug("startNewGameForPlayer: " + player.getName());
		
		Game newGame = new Game (player, pack);
		games.put(Long.toString(newGame.getGameId()), newGame);
		player.setGame(newGame);
		
		player.setStatus(PlayerStatus.JOINED_GAME);
		
		addAction (ActionType.NEW_GAME, player);
	}

	public List<Game> getGames() {
		
		return new ArrayList<Game>(games.values());
	}

	public void joinGame(Player player, String gameId) {
		logger.debug("joinGame: " + player.getName() + ": " + gameId);
		
		Game game = games.get(gameId);
		game.addPlayer (player);
		player.setGame(game);
		
		player.setStatus(PlayerStatus.JOINED_GAME);
		
		addAction (ActionType.JOIN_GAME, player);
	}

	public void readyToPlay(Player player) {
		player.setStatus(PlayerStatus.READY_TO_PLAY);
		addAction (ActionType.READY_TO_PLAY, player);		
	}

	public void setPack(Pack pack) {
		this.pack = pack;
		logger.debug("We now have " + pack.getCardsInPack().size() + " cards in the pack");
	}

}