
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;


public class GoodSentenceProgram {
	
	
	
	public static void main(String[] args) {
		
		while(true) {
			Scanner reader = new Scanner(System.in);
			Password password = PasswordSentenceGenerator.getInstance().generatePassword();
			String sentence = password.getSentence();
			System.out.println("=============================================");
			System.out.println(sentence);
			System.out.println("Good sentence? y/n");
			String res = reader.nextLine();
			Path p = Paths.get("dictionary/goodsentences.txt");
			if (res.equals("y")) {
				try (BufferedWriter writer = Files.newBufferedWriter(p, StandardOpenOption.APPEND)) {
				    writer.write(sentence +"\n");
				} catch (IOException e) {
				    e.printStackTrace();
				}
			}
		}
	}

}
