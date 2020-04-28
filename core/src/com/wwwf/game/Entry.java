package com.wwwf.game;

import com.badlogic.gdx.Game;
import com.wwwf.game.client.ClientScreen;


public class Entry extends Game {

	/** Temporary way to debug game. Creates a server and attaches a client. The client will eventually use the Internet
	 *  to send messages to the server, for now use Utils.message(...) to send message to server. If the client needs
	 *  information from server (e.g to render entities) you can looks at them without modifying them for now. Later
	 *  will the client and server will be separate programs so we will have to send objects to the client to render.
	 */

	@Override
	public void create() {
		Server server = new Server();
		this.setScreen(new ClientScreen(server));
	}
}
