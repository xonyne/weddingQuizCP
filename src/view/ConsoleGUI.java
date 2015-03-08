package view;

import java.util.List;
import java.util.Scanner;

public class ConsoleGUI implements GUI {
	private static String DELIMITER = "******************************";
	
	public int askQuestion(String question, List<String> options) { 
		boolean optionsPresent = options != null && options.size() >= 2; 
		String optionString = optionsPresent ? "(1-" + String.valueOf(options.size())  + ")": "(j/n)";;

		this.message(question + " " + optionString + ":");
		if (options != null){
			for (int i = 0; i < options.size(); i++) {
				this.message(String.valueOf(i + 1) + ") " + options.get(i));
			}
		}
		
		int answer = 0;
		boolean validAnswer = false;
		do {
			@SuppressWarnings("resource")
			String inputStr = new Scanner(System.in).next();
			if (optionsPresent) {
				validAnswer = Integer.valueOf(inputStr) >= 1 && Integer.valueOf(inputStr) <= options.size();
				if (validAnswer) {
					answer = Integer.valueOf(inputStr);
					this.message("Option " + String.valueOf(answer) + " gewählt (" + options.get(answer -1) + ").");
				}
			} else {
				validAnswer = inputStr.toLowerCase().equals('j') || inputStr.toLowerCase().equals('n');
				answer = inputStr.toLowerCase().equals('j') ? 1:0;
				this.message(answer==1 ? "Ja":"Nein" + " gewählt");
			}
		} while (!validAnswer);
		
		this.message("");
		
		return answer - 1;
	}

	@Override
	public void message(String message) {
		System.out.println(message);
	}

	@Override
	public void printDelimiter() {
		System.out.println(DELIMITER);
	}
	
}
