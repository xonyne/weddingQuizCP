package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private String name;
	private List<Integer> questionsAsked;
	private int score;
	
	public Player(String name){
		this.name = name;
		this.score = 0;
		this.questionsAsked = new ArrayList<Integer>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Integer> getQuestionsAsked() {
		return questionsAsked;
	}
	public void addQuestionsAsked(int questionId) {
		this.questionsAsked.add(questionId);
	}

	public int getScore() {
		return score;
	}

	public void raiseScore(int amount) {
		this.score += amount;
	}
	
	public void reduceScore(int amount) {
		this.score -= amount;
	}
}
