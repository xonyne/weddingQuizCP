package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import sun.awt.CharsetString;

public class Questionnaire {
	List<Question> questions;
	List<Answer> answers;
	List<String> participants;
	
	private Questionnaire(Path inputFile) {
		questions = new ArrayList<>();
		answers = new ArrayList<>();
		participants = new ArrayList<>();
		readLimeSurveyCSVFile(inputFile);
	}
	
	public static Questionnaire loadQuestionnaire(String questFile) {
		String path;
		try {
			path = new java.io.File( "." ).getCanonicalPath() + "\\" + questFile;
			return new Questionnaire(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void readLimeSurveyCSVFile(Path inputFile) {
		Charset charset = Charset.forName("UTF-8");
		try (BufferedReader reader = Files.newBufferedReader(inputFile, charset)) {
		    String line = null;
		    int lineNr=1;
		    while ((line = reader.readLine()) != null && line.length() != 0) {
		    	//First line is reserved for the questions
		    	if (lineNr==1) {
		    		parseQuestions(line);
		    	} else { 
		    		parseAnswerLine(line);
		    	}
		    	lineNr++;
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
	}

	private void parseAnswerLine(String line) {
		ArrayList<String> answers = splitCSVLine(line);

		// First entry in the answer line is the name of the participant
		String participant = answers.get(0);
		this.participants.add(participant);
		
		for (int a=1; a<answers.size();a++){
			Answer currentAnswer = new Answer(String.valueOf(a-1), participant, answers.get(a)); 
			this.answers.add(currentAnswer);
		}
		
	}

	private void parseQuestions(String line) {
		ArrayList<String> questions = splitCSVLine(line);
		
		for (int a=0; a<questions.size();a++){
			Question currentQuestion = new Question(String.valueOf(a), questions.get(a)); 
			this.questions.add(currentQuestion);
		}
	}

	private ArrayList<String> splitCSVLine(String line) {
		ArrayList<String> items = new ArrayList<String>();
	    int i = 0;
		while  (i < line.length()) {
			int apostropheCount = 0;
			StringBuffer sb = new StringBuffer();
			while (apostropheCount < 2) {
				char currentChar = line.charAt(i);
				if (currentChar == '\"') {
					apostropheCount++;
					i++;
					continue;
				}
				sb.append(currentChar);
				i++;
			}
			items.add(sb.toString());
			//increase value of i to skip comma separator
			i++;
		}
		return items;
	}
	
	public void printParticipants() {
		List<String> participants = this.getParticipants();
		System.out.println("*** Participants ***");
		for (int i = 0; i<participants.size();i++) {
			System.out.println(participants.get(i));
		}
	}
	
	public void printQuestions(){
		List<Question> questions = this.getQuestions();
		System.out.println("*** Questions ***");
		for (int i = 0; i<questions.size();i++) {
			System.out.println(questions.get(i));
		}
	} 
	
	public void printAnswers(){
		List<Answer> answers = this.getAnswers();
		System.out.println("*** Answers ***");
		for (int i = 0; i<answers.size();i++) {
			System.out.println(answers.get(i));
		}
	}
	
	public List<Question> getQuestions() {
		return questions;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public List<String> getParticipants() {
		return participants;
	}
	
	public int getQuestionCount(){
		return this.questions.size();
	}
	
	public Question getQuestionById(int id){
		return this.questions.get(id);
	}

	public List<Question> getRandomQuestions(int count, List<Integer> idsToExclude) {
		List<Question> randomQuestions;
		List<Integer> randomQuestionIds;
		Integer nextQuestionId;
		Random randomGenerator;
		int LIMIT = 1000;
		
		randomQuestions = new ArrayList<Question>();
		randomQuestionIds = new ArrayList<Integer>();
		randomGenerator = new Random();
		int currentLoop = 0;
		for (int a=0; a<count; a++) {
			
			boolean loopCondition = false;
			do {
				currentLoop++;

				nextQuestionId = randomGenerator.nextInt(getQuestionCount());		
				
				if (currentLoop <= LIMIT) {
					loopCondition = idsToExclude.contains(nextQuestionId) || randomQuestionIds.contains(nextQuestionId) ;
				} else {
					loopCondition = randomQuestionIds.contains(nextQuestionId);
				}
				
			} while (loopCondition);
			
			randomQuestions.add(getQuestionById(nextQuestionId));
			randomQuestionIds.add(nextQuestionId);
		}
		
		return randomQuestions;
	}

	public List<Answer> getAllAnswersForQuestion(String questionId) {
		List<Answer> allAnswers = this.getAnswers();
		List<Answer> answersForThisQuestionId = new ArrayList<Answer>();
		
		for (int i = 0; i < allAnswers.size() ; i++) {
			Answer currentAnswer = allAnswers.get(i);
			if (currentAnswer.getQuestionId().equals(questionId)) {
				answersForThisQuestionId.add(currentAnswer);
			}
		}
		
		return answersForThisQuestionId;
	}
}
