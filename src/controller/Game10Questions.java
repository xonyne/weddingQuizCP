package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import view.GUI;
import model.Answer;
import model.Player;
import model.Question;
import model.Questionnaire;

public class Game10Questions extends Game { 
	private enum STEPS {ATTACK, DEFENSE, NEXT_TURN, NEXT_ROUND, BONUS_ATTACK, BONUS_DEFENSE, END};
	private int QUESTION_COUNT = 3;
	private int TURN_COUNT = 10;
	
	public Game10Questions(GUI gui, Questionnaire questionnaire){
		super(gui, questionnaire);
	}

	public void play(){
		super.play();

		Player attacker = null;
		Player defender = null;
		List<Question> questionChoice = null;
		List<Answer> possibleAnswers = null;
		Answer answerGiven = null;
		Answer correctAnswer = null;
		Question questionAsked = null;
		
		STEPS nextStep = STEPS.NEXT_TURN;	
		this.finished = false;
		this.activePlayer = -1;
		int currentTurn = -1;
		while (!this.finished) {
			switch (nextStep) {
			case ATTACK:
				questionChoice = this.questionnaire.getRandomQuestions(QUESTION_COUNT, attacker.getQuestionsAsked() );
				String questionStr = attacker.getName() +  " am Zug. Wähle eine Frage für " + defender.getName() + ":";
				
				List<String> questionOptions = new ArrayList<String>();
				for (int i = 0; i<questionChoice.size();i++) {
					questionOptions.add(questionChoice.get(i).getText());
				}
				
				int questionNr = gui.askQuestion(questionStr, questionOptions);
				questionAsked = questionChoice.get(questionNr);
				attacker.addQuestionsAsked(questionNr);
				nextStep = STEPS.DEFENSE;
				break;
			case DEFENSE:
				String answerStr = defender.getName() +  ", welche Antwort stammt wohl von " + attacker.getName() + "?";
				
				possibleAnswers=questionnaire.getAllAnswersForQuestion(questionAsked.getId());
				Collections.shuffle(possibleAnswers,new Random(System.nanoTime()));
				List<String> answerOptions = new ArrayList<String>();
				for (int i = 0; i<possibleAnswers.size();i++) {
					Answer currentAnswer = possibleAnswers.get(i);
					answerOptions.add(currentAnswer.getText());
					// name of the player acts as participant id
					if (currentAnswer.getParticipantId().equals(attacker.getName())) 
					{
						correctAnswer = currentAnswer;
					}
				}
				
				int answerNr = gui.askQuestion(answerStr, answerOptions);
				answerGiven = possibleAnswers.get(answerNr);
				if (answerGiven.equals(correctAnswer)) {
					defender.raiseScore(1);
					gui.message("Dies war die korrekt Antwort!");
				} else {
					gui.message("Diese Antwort ist leider falsch.");
				}
				
				nextStep = STEPS.NEXT_TURN;
				break;
			case NEXT_ROUND:
				gui.printDelimiter();
				gui.message("Runde " + (1 + (currentTurn / 2)) + " / 10 - (" + this.players[0].getName() + ":" + this.players[0].getScore() + ", " + this.players[1].getName() + ":" + this.players[1].getScore() +")");
				gui.printDelimiter();
				
				nextStep = STEPS.ATTACK;
				break;
			case NEXT_TURN:
				currentTurn++;
				
				this.activePlayer = (this.activePlayer + 1) % 2;
				attacker = this.players[this.activePlayer];
				defender =  this.players[(this.activePlayer + 1) % 2];
				
				if (currentTurn > (TURN_COUNT * 2) - 1) {
					nextStep = STEPS.END;
				} else if (currentTurn % 2 == 0) {
					nextStep = STEPS.NEXT_ROUND;
				} else {
					nextStep = STEPS.ATTACK;
				}
				
				
				break;
			case BONUS_ATTACK:
				break;
			case BONUS_DEFENSE:
				break;
			case END:
				gui.printDelimiter();
				gui.message("Spielende ----> " +  attacker.getScore() + ":" + defender.getScore() + " (" + attacker.getName() + ", " + defender.getName() + ")"  );
				gui.printDelimiter();
				this.finished = true;
				break;
			}
		}
	}

	@Override
	protected void nextRound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void nextTurn() {
		// TODO Auto-generated method stub
		
	}

}
