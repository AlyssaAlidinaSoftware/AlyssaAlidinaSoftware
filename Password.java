
public class Password {
	public static final String EMAIL = "email";
	public static final String BANK = "bank";
	public static final String SHOPPING = "shopping";
	
	private String password;
	private String sentence;
	private int number;
	private String type;
	
	public Password(String password){
		this.password = password;
	}
	public Password(String password, String sentence, int number) {
		this.password = password;
		this.sentence = sentence;
		this.number = number;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public String getSentence() {
		return sentence;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getNumber() {
		return number;
	}
	
}
