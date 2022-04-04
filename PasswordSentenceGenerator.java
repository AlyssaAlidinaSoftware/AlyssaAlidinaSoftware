import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


import simplenlg.phrasespec.SPhraseSpec;

public class PasswordSentenceGenerator {

	private static PasswordSentenceGenerator instance;
	private static String DICTIONARY_PATH = "dictionary" + File.separator;
	
	private static String RESOURCES_PATH = "resources" + File.separator;
	private static String NOUNS_FILE = "nouns.txt";
	private static String DETERMINERS_FILE = "determiners.txt";
	private static String NAMES_FILE = "names.txt";
	private static String VERBS_FILE = "verbs.txt";
	
	private final int PASSWORD_NUM_LIMIT = 10;
	private ArrayList<String> nouns;
	private List<String> names;
	private List<String> verbs;
	private List<String> determiners;
	private Random randomGenerator;
	private NLGFactory nlgFactory;
	private Lexicon lexicon;
	private Realiser realiser;
	private int currentNumber;
	private Password currentPassword;
	
	private List<String> vowelsList;
	private String[] vowels = new String[]{"a", "e", "i", "o", "u"};
	
	public PasswordSentenceGenerator() {
		nouns = new ArrayList<String>();
		loadWords();
		randomGenerator = new Random();
		lexicon = Lexicon.getDefaultLexicon();
		nlgFactory = new NLGFactory(lexicon);
		realiser = new Realiser(lexicon);
		vowelsList = Arrays.asList(vowels);
		instance = this;
		
	}
	
	public static PasswordSentenceGenerator getInstance() {
		if (instance == null) {
			instance = new PasswordSentenceGenerator();
		}
		return instance;
	}
	
	private void loadWords()  {
		verbs = loadFromFile(VERBS_FILE);
		nouns = loadFromFile(NOUNS_FILE);
		names = loadFromFile(NAMES_FILE);
		determiners = loadFromFile(DETERMINERS_FILE);	
	}
	private ArrayList<String> loadFromFile(String filename) {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(RESOURCES_PATH + filename).getFile());
		ArrayList<String> words = new ArrayList<String>();
		try {
			Scanner s = new Scanner(file);
			while (s.hasNextLine()) {
				words.add(s.nextLine());
			}
			s.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return words;
	}
	private String selectRandomWord(List<String> wordlist) {
		int index = randomGenerator.nextInt(wordlist.size());
		String word = wordlist.get(index);
		return word;
	}
	private String getPasswordFromSentence(String sentence) {
		 StringBuilder sb = new StringBuilder();
		    for(String s : sentence.split(" ")){
		        sb.append(s.charAt(0));         
		    }
		    currentNumber = generateNumber();
		    return sb.toString() + currentNumber; 
	}
	private int generateNumber() {
		int num = randomGenerator.nextInt(PASSWORD_NUM_LIMIT);
		return num;
	}
	public Password getPassword() {
		return currentPassword;
	}
	public Password generatePassword() {
		String sentence;
		String name = selectRandomWord(names);
		String verb = selectRandomWord(verbs);
		String determiner = selectRandomWord(determiners);
		String noun = selectRandomWord(nouns);
		SPhraseSpec p = nlgFactory.createClause();
		p.setSubject(name);
		p.setVerb(verb);
		p.setObject(determiner + " " + noun);
		sentence = formSentence(realiser.realiseSentence(p));
		System.out.println(sentence);
		String password = getPasswordFromSentence(sentence);
		return new Password(password, sentence,currentNumber);
	}
	public static void main(String[] args)  {
		PasswordSentenceGenerator psg = PasswordSentenceGenerator.getInstance();
		psg.loadWords();
	}
	private String formSentence(String sentence) {
		String password = getPasswordFromSentence(sentence);
		List<String> passwordArr = Arrays.asList(password.split(""));
		if (vowelsList.contains(passwordArr.get(3)) && passwordArr.get(2).equals("a")) {
		 sentence = sentence.replaceAll("\\ba\\b", "an");	
		}
		if (!vowelsList.contains(passwordArr.get(3)) && passwordArr.get(2).equals("a")) {
			 sentence = sentence.replaceAll("\\ban\\b", "a");
		}
		return sentence;
	}
}
