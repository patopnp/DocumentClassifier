package model.machinelearning;

import model.Category;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NaiveBayesClassifier {
	private List<Category> categories;
	
	public NaiveBayesClassifier() {
		categories = new ArrayList<Category>();
	}
	
	public void addCategory(Category c) {
		categories.add(c);
	}
	
	public void removeCategory(int index) {
		categories.remove(index);
	}
	
	public boolean isAnyCategoryEmpty() {
		return categories.stream().anyMatch(c -> {return c.getListOfFiles().isEmpty();});
	}
	
	public int numberOfCategories() {
		return categories.size();
	}
	
	public Category clasify(String text)
	{
		try
		{
			int totalDifferentWordsAllDataset = 0;
			double maxScore = 0;
			Category category = categories.get(0);

			BufferedReader in;
			String palabra = "ERROR";
			
			HashMap<String,Integer> bagOfWords[] = new HashMap[categories.size()];
			HashSet<String> wordsUsed = new HashSet<String>();
			Set<String> excludedWords = Set.of("the","of","and","to","in","a","that","he","as","is","with","are","on","be","an","from","was","were","it","she","had","s","they","their","his","or","but","this","by","for","which","not","at","its");
			
			StringTokenizer t;
			
			
			int catSize[] = new int[categories.size()];
			
			for(int z = 0; z < categories.size(); z++)
			{
				List<File> listOfFiles = categories.get(z).getListOfFiles();
				catSize[z] = listOfFiles.size();
				bagOfWords[z] = new HashMap<>();
				for(int j = 0; j < listOfFiles.size(); j++)
				{
					in = new BufferedReader(new FileReader(listOfFiles.get(j)));
					String s = in.readLine();
					while (s != null)
					{
					
						t = new StringTokenizer(s," ,.;:'[]/<>|}{!()?\"");
						
						while(t.hasMoreTokens())
						{
							palabra = t.nextToken().toLowerCase();
							if (excludedWords.contains(palabra)) continue;
							
							bagOfWords[z].put(palabra, bagOfWords[z].getOrDefault(palabra, 0)+1);
							wordsUsed.add(palabra);
						} 
						s = in.readLine();
					}
			
					in.close();
				}
			}
			
			HashMap<String, Integer> bagOfWordsSample = new HashMap<>();
			
			t = new StringTokenizer(text," ,.;:'[]/<>|}{!()?\"");
			
			while(t.hasMoreTokens())
			{
				palabra = t.nextToken().toLowerCase();
				if (excludedWords.contains(palabra)) continue;
				
				bagOfWordsSample.put(palabra, bagOfWordsSample.getOrDefault(palabra, 0)+1);
			} 
			
			totalDifferentWordsAllDataset = wordsUsed.size();
			int totalDoc = 0;
			
			for(int u = 0; u < catSize.length; u++) totalDoc += catSize[u];
			for(int u = 0; u < categories.size(); u++){
				
				double pc = Math.log10(catSize[u]/(double)(totalDoc));
				int totalDePalabras = bagOfWords[u].values().stream().mapToInt(Integer::intValue)
						  .sum();
					for (Map.Entry<String, Integer> entry : bagOfWordsSample.entrySet()) {
			            String word = entry.getKey();
			            Integer count = bagOfWords[u].getOrDefault(word, 0);
			            Integer countOnSample = entry.getValue();
			            
		            	pc += Math.log10((count+1)/((double)(totalDePalabras+totalDifferentWordsAllDataset)))*countOnSample;
					}
		            

					System.out.println(pc);
					if (pc > maxScore || maxScore == 0) {
						maxScore = pc;
						category = categories.get(u);
					}
				
				}

			return category;
			
		}
		catch(IOException exception){
			exception.printStackTrace();
		}
		return null;
	}
}
