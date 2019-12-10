package lab5;

import java.util.*;
import java.io.*;


/*
 * Denna klass modellerar en ordlista (Dictionary). En ordlista
 * associerar termer med betydelser. En term kan mappas till flera betydelser.
 * Både term och betydelse representeras med klassen Word.
*/


public class Dictionary {
	
	//initialiserar en hashmap
	private Map <Word, Set<Word>> map = new HashMap<Word, Set<Word>>(); 
	
	//Lägger till termen key till ordlistan med betydelsen value. Om termen
	//redan  är tillagd med angiven betydelse händer ingenting.
  	public void add(Word key, Word value){
  	//kollar om key redan existerar och lägger isåfall in value i key
  		if (map.containsKey(key)) {
  		map.get(key).add(value); 
  		}
  	else {
  		//Skapar nytt hashSet och lägger till key
  		 HashSet<Word> set =new HashSet<>();  
  		 set.add(value);
  		 map.put(key, set);
  	}
}
  	

//Bekvämare sätt att anropa add för 2 strängar  än
// (Word, Word).
	public void add(String key, String value){
		// GÖR OM STRING TILL WORD, 
		// ROPA PÅ ADD(WORD WORD)
		add(new Word(key), new Word(value));
		
}

	
// Returnerar alla keys tillhörande mappen
public Set<Word> terms(){
	return map.keySet();
}


//retunerar betydelser till key, alternativt null om key ej existerar
public Set<Word> lookup(Word key){
	if (map.containsKey(key))
		return map.get(key);
	else
		return null;
}


//skapar en ny inverterad ordlista
public Dictionary inverse(){
 Dictionary inversedMap = new Dictionary();
 for (Word termer : map.keySet()) {
	 for (Word betydelser : lookup(termer)) {
		 inversedMap.add(betydelser, termer); 
	 	}
 	}
 return inversedMap;
}


//läser in orden från den valda textfilen och lägger till dem till ordlistan
public void load(InputStream is) throws IOException, FileNotFoundException{
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
	

//sparar ordlistan till den valda textfilen
public void save(OutputStream os)throws IOException {
	BufferedWriter out = new BufferedWriter(
            new OutputStreamWriter(os)); {
				for(Word termer : map.keySet()) {
					for(Word betydelser : lookup(termer)) {
						out.write(termer + ":" + betydelser + "\n");
					}
				}
			out.close();
	}
}
}
