package view;

import java.util.List;

public interface GUI {
	public int askQuestion(String question, List<String> options);
	public void message(String message);
	public void printDelimiter();
}
