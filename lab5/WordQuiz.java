package lab5;

import java.util.*;
import java.io.*;


public class WordQuiz { 
	static Dictionary dict; 
	public WordQuiz(Dictionary dictionary) {
		dict = dictionary;
	}
	
	
	public static void runQuiz() throws FileNotFoundException, IOException {
		Set<Word> termer = dict.terms();
		ArrayList<Word> removedWord = new ArrayList<Word>();
		int antRundor = 0;
		while(!termer.isEmpty()) {
			int antFel = 0;
			antRundor++;
			for(Word term : termer) {
				System.out.println("Översätt: " + term);
				Scanner Scan = new Scanner(System.in);
				String in = Scan.nextLine();

				Word inWord = new Word(in);
				Set<Word> betydelser = dict.lookup(term);
				
				if(betydelser.contains(inWord)) {
					System.out.println("Korrekt");
					removedWord.add(term);
				}
				else {
					System.out.println("Fel");
					antFel++;	
					}
				}
			for(int i=0; i< removedWord.size(); i++) {
		        termer.remove(removedWord.get(i)); 
			}
			if(antFel > 0) {
			System.out.println("Du klarade inte " + antFel + " ord. Försök igen!");
			}
		}
		
		if (antRundor == 1) {
			System.out.println("Du klarade det på första försöket!");
		}
		else {
		System.out.println("Du klarade det på " + antRundor + " försök!");
		}		
		
		printMeny();
}
	
	public static void main(String[] args) throws IOException{
		Dictionary dict = new Dictionary();
		WordQuiz wq = new WordQuiz(dict);
		printMeny();
    }
	
	public static void printMeny() throws FileNotFoundException, IOException {
		FileInputStream is = new FileInputStream("/home/rassa328/eclipse-workspace/TDDC77Labs/src/lab5/Ordlista.txt");
		dict.load(is);

		//Skriv meny med cases
		System.out.println("---------------------Meny-------------------");
		System.out.println("1: Träna glosor!");
		System.out.println("2: Träna glosor åt andra hållet!");
		System.out.println("3: Lägg till nya ord");
		System.out.println("4: Avslutar programmet");
		Scanner Scan = new Scanner(System.in);
		
		while(true) {		
			String val = "";
			val = Scan.nextLine();
			switch(val) {
			//ladda en ordlista
			case "1":
				System.out.println("Kör programmet");
				runQuiz();
				break;
			//Kör programmet inverterat
			case "2":
				dict = dict.inverse();
				runQuiz();
				break;
			//lägger till nya ord
			case "3":
				System.out.println("Lägg till ett ord på formen term:betydelse");
				String line = Scan.nextLine();
				String[] Ord = line.split(":");
				dict.add(Ord[0], Ord[1]);
				FileOutputStream os = new FileOutputStream("/home/rassa328/eclipse-workspace/TDDC77Labs/src/lab5/Ordlista.txt");
				dict.save(os);
				printMeny();
			//avslutar programmet
			case "4":
				System.out.println("Ses igen!");
				System.exit(0);
				break;
			default:
				System.out.println("Felinmatning, försök igen");
			}
		}	
	
	}
}
