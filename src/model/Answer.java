package model;

public class Answer {
	private String questionId;
	// participantId is the name of the player
	private String participantId;
	private String text;
	
	public Answer(String questionId, String participantId, String text) {
		super();
		this.questionId = questionId;
		this.participantId = participantId;
		this.text = text;
	}
	
	public String toString(){
		return this.questionId + ", " + this.participantId + ", " + this.text;
	}
	
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getParticipantId() {
		return participantId;
	}
	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
