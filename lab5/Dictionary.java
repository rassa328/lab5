package lab5;

import java.util.*;
import java.io.*;


/*
 * Denna klass modellerar en ordlista (Dictionary). En ordlista
 * associerar termer med betydelser. En term kan mappas till flera betydelser.
 * Både term och betydelse representeras med klassen Word.
*/


public class Dictionary {
	
	private Map <Word, Set<Word>> map = new HashMap<Word, Set<Word>>(); 
	//Lägger till termen key till ordlistan med betydelsen value. Om termen
	//redan  är tillagd med angiven betydelse händer ingenting.
	int raknare = 0;
  	public void add(Word key, Word value){
  		
  		
	//KOLLA OM key FINNS I MAP
  	if (map.containsKey(key)) {
  		// OM FINNS - HÄMTA VALUE SET SOM HÖR TILL KEY (key) OCH LÄGG TILL value I SET.
  		map.get(key).add(value); 
//  		System.out.println("key existerar redan lägger till: " + value.toString() + " till " + key);
  		}
  	else {
  		//Ska nytt hashSet
  		 HashSet<Word> set =new HashSet<>();  
  		 set.add(value);
  		 map.put(key, set);
//   		System.out.println("Skapar nytt set och lägger in " + value.toString() + " till " + key);
  	}
}
  	

//Bekvämare sätt att anropa add för 2 strängar  än
// (Word, Word).
	public void add(String key, String value){
		// GÖR OM STRING TILL WORD, 
		// ROPA PÅ ADD(WORD WORD)
		Word keyWord = new Word(key);
		Word valueWord = new Word(value);
		add(keyWord,valueWord);
}

	
// Returnerar en icke-null mängd med ordlistans alla termer.		
public Set<Word> terms(){
	return map.keySet();
}


// Slår upp och returnerar en mängd av betydelser till key, eller
// null om key inte finns i ordlistan.
public Set<Word> lookup(Word key){
	if (map.containsKey(key))
		return map.get(key);
	else
		return null;
}


// Skapar och returnerar en ny ordlista på det motsatta språket, dvs, alla
// betydelser blir termer och alla termer blir betydelser. T.ex. en
// svensk-engelsk ordlista blir efter invertering engelsk-svensk.
public Dictionary inverse(){
 Dictionary inversedMap = new Dictionary();
 for (Word termer : map.keySet()) {
	 for (Word betydelser : lookup(termer)) {
		 inversedMap.add(betydelser, termer); 
	 	}
 	}
 return inversedMap;
}


//Läser in orden från den givna strömmen och lägger dessa i ordlistan.
public void load(InputStream is) throws IOException, FileNotFoundException{
	is = new FileInputStream("/home/rassa328/eclipse-workspace/TDDC77Labs/src/lab5/Ordlista.txt");
	try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
		while (reader.ready()) {
			String line = reader.readLine();
			String[] ord = line.split(":");
			add(ord[0], ord[1]);
			}
		}
		catch(NullPointerException e) {
			System.out.println("Inga ord i ordlistan");
		}	
}
	
//Lagrar ordlistan på den givna strömmen.
public void save(OutputStream os)throws IOException {
		
	try(FileWriter fw = new FileWriter("/home/rassa328/eclipse-workspace/TDDC77Labs/src/lab5/Ordlista.txt", true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter out = new PrintWriter(bw)){
			for(Word termer : map.keySet()) {
				for(Word betydelser : lookup(termer)) {
					out.write(termer + ":" + betydelser + "\n");
			}
		}
	}
		catch (IOException e) {
			System.out.println("IOException");
		}
	}
}