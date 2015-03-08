/**
 * 
 */
package controller;

import java.io.IOException;
import view.ConsoleGUI;
import model.Questionnaire;

/**
 * @author xonyne
 *
 */
public class Start {
	private static String QUEST_FILE = "limesurveyMarriageCP.csv";
	

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Questionnaire quest = Questionnaire.loadQuestionnaire(QUEST_FILE);
		//quest.printAnswers();
		//quest.printQuestions();
		//quest.printParticipants();
		Game game = new Game10Questions(new ConsoleGUI(), quest);
		game.play();
	}
}
	