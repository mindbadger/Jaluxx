package com.mindbadger.jaluxx.gamemanager;

import com.mindbadger.jaluxx.game.Dealer;
import com.mindbadger.jaluxx.game.Game;
import com.mindbadger.jaluxx.game.Pack;
import com.mindbadger.jaluxx.player.Player;

public class GameFactory {
	private Dealer dealer;
	private Pack pack;
	
	public Game createNewGameForPlayer (Player player) {
		return new Game (player, pack, dealer);
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public Pack getPack() {
		return pack;
	}

	public void setPack(Pack pack) {
		this.pack = pack;
	}
}
