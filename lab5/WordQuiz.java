package lab5;

import java.util.*;
import java.io.*;


public class WordQuiz { 
	Dictionary dict; 
	public WordQuiz(Dictionary dictionary) throws IOException {
		System.out.println("inne i wordquiz");
		dict = dictionary;
	}

	
	public void runQuiz() throws FileNotFoundException, IOException {
		System.out.println("Början va runquiz");
		
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
			System.out.println("Du klarade inte " + antFel + " ord försök igen");
			}
		}
		
		if (antRundor == 1) {
			System.out.println("Du klarade det på första försöket");
		}
		else {
		System.out.println("Du klarade det på " + antRundor + " försök");
		}
		System.out.println("slutet va runquiz");

		printMeny();
}
	
	public static void main(String[] args) throws IOException{
		printMeny();
    	//skapar en tom ordlista
    	//Dictionary sweng = new Dictionary();
    	//WordQuiz wq = new WordQuiz(sweng);
      	//wq.runQuiz();
    }
	
	public static void printMeny() throws FileNotFoundException, IOException {
		System.out.println("---------------------Meny-------------------");
		System.out.println("1: Ladda filen");
		System.out.println("2: Inverterar den nuvarande textfilen");
		System.out.println("3: Sparar de laddade orden till textfil");
		System.out.println("4: Avslutar programmet");
		System.out.println("5: Kör prog");
		Dictionary dict = new Dictionary();

		

		//Skriv meny med cases
		Scanner Scan = new Scanner(System.in);
		String val = Scan.nextLine();
		
		while(!val.equals("4")) {

		val = Scan.nextLine();
		
		
		switch(val) {
		//ladda en ordlista
		case "1":
			FileInputStream is = new FileInputStream("/home/rassa328/eclipse-workspace/TDDC77Labs/src/lab5/Ordlista.txt");
			dict.load(is);
			
			break;
		//inverterar ordlistan
		case "2":
//			dict.load(is);
			dict = dict.inverse();
			
			break;
		//sparar programmet
		case "3":
			FileOutputStream os = new FileOutputStream("/home/rassa328/eclipse-workspace/TDDC77Labs/src/lab5/Ordlista.txt");
			dict.save(os);
			break;
		//avslutar programmet
		case "4":
			
			break;
		case "5":
			WordQuiz wq = new WordQuiz(dict);
	      	wq.runQuiz();
			break;
		default:
			System.out.println("Felinmatning, försök igen");
		}
	}	
	
	}
}
