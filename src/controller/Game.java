package controller;

import java.util.List;
import view.GUI;
import model.Player;
import model.Questionnaire;

public abstract class Game {
	protected Questionnaire questionnaire;
	protected Player[] players;
	protected int activePlayer;
	protected boolean finished;
	protected GUI gui;
	
	public Game(GUI gui, Questionnaire questionnaire){
		this.gui = gui;
		this.questionnaire = questionnaire;
	};

	public void play(){
		initializePlayers();
	}
	
	protected abstract void nextTurn();
	protected abstract void nextRound();
	
	protected void initializePlayers() {
		int answer1;
		int answer2;
		List<String> participants;
		
		participants = this.questionnaire.getParticipants();
		
		answer1 = this.gui.askQuestion("Wähle Spieler 1", participants);
		answer2 = this.gui.askQuestion("Wähle Spieler 2", participants);

		this.players = new Player[2];
		this.players[0] = new Player(participants.get(answer1));
		this.players[1] = new Player(participants.get(answer2));
		
		gui.message("Es spielt " + this.players[0].getName() + " gegen " +  this.players[1].getName() + "." );
	};
	
	
}
