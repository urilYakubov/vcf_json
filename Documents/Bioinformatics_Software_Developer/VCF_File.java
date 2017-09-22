package exercise;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/* This class is responsible to process VCF file format. Class read 
 * file line by line to avoid OutOfMemory error if we keep entire lines
 * in memory, process each line, update Scores object of each individual. 
 * 
 */

public class VCF_File {

	private final int numTabs = 9;
	private final int indexOfIndividuals = 9;
	private final int indexOfInfo = 7;
	private Processor processor;
	private final String indelMap= "VT=INDEL";
	private final String structurialVariationMap= "VT=SV";
	private final String SNPMap= "VT=SNP";
	
	private class Individuals {
		public String name;
		public String gt;
	}
	

	public VCF_File(Processor processor) throws Exception {
		
		this.processor = processor;
		
		processFile();
	}
	
	private void processFile() throws Exception {
		
		BufferedReader br = null;
		String[] splitStr;
		boolean startWith = true;
		//ArrayList<String> individuals = new ArrayList<String>();
		ArrayList<Individuals> individualsArray = new ArrayList<Individuals>();
		int numColumns = 0;
		
		try {
			br = new BufferedReader(new FileReader(processor.getVcfFilename()));
			String line;
		    while ((line = br.readLine()) != null) {
		       if(startWith) {
		    	   splitStr = line.split("\t");
		    	   if(splitStr.length > numTabs && splitStr[0].equals("#CHROM")) {
		    		   for(int i = indexOfIndividuals; i < splitStr.length; i++) {
		    			   Individuals individuals = new Individuals();
		    			   individuals.name = splitStr[i];
		    			   individualsArray.add(individuals);		    			   
		    		   }
		    		   processor.buildindIvidualScores(getNamesFromArray(individualsArray));
		    		   startWith = false;
		    		   numColumns = splitStr.length;
		    	   }
		    	   continue;
		       }
		       // Start parsing data, index of info=7
		       splitStr = line.split("\t");
		       if(splitStr.length != numColumns) {
		    	   throw new Exception("Parsing Error: number of columns in header does not match at details");
		       }
		       
		       
		       // Update individuals array with GT sequence
		       {
		    	   int i = 0;
		    	   for(Individuals individual: individualsArray) {
		    		   individual.gt = splitStr[indexOfIndividuals + i].substring(0, 3);
		    		   i++;
		    	   }
		       }
		       
		       
		       for(Individuals individual: individualsArray) {
		    	   int num = Integer.valueOf(individual.gt.substring(0, 1)) + Integer.valueOf(individual.gt.substring(2, 3));
		    	   // Check if there zero copies of variants
		    	   if(num == 0) { 
		    		   continue;
		    	   }
		    	   
		    	   Scores scores = processor.getIndividualsScores().get(individual.name);
	    		   if(scores == null) {
	    			   throw new Exception("Parsing Error: individual name does not exist" + individual.name);	    			   
	    		   }
		    	   // Check if transition, else transversion.
		    	   if(isTransition(splitStr[3], splitStr[4])) {
		    		   scores.increaseTransitionCount(num);
		    	   } else {
		    		   scores.increaseTransversionCount(num);
		    	   }
		    		
		    	   // Check if this SNP type, if yes increase count.
		    	   if(isSNPType(splitStr[indexOfInfo])) {
		    		   scores.increaseSnpCount(splitStr[3], splitStr[4], num);
		    		   continue;
		    	   }
		    	   
		    	   // Check if this Indel type, if yes increase count.
		    	   if(isIndelType(splitStr[indexOfInfo])) {
		    		   scores.increaseIndelsCount(num);
		    		   continue;
		    	   }
		    	   
		    	   // Check if this Structural variation type, if yes increase count.
		    	   if(isStructVariationType(splitStr[indexOfInfo])) {
		    		   scores.increaseStructVariantsCount(num);
		    		   continue;
		    	   }
		       }		       
		       
		    }
		}
		finally {
	        if(br != null) {
	        	br.close();
	        }
	    }
	}
	
	public boolean isIndelType(String info) {
		if(info.contains(indelMap)) {
			return true;
		}
		return false;		
	}
	
	public boolean isStructVariationType(String info) {
		if(info.contains(structurialVariationMap)) {
			return true;
		}
		return false;		
	}
	
	public boolean isSNPType(String info) {
		if(info.contains(SNPMap)) {
			return true;
		}
		return false;		
	}
	
	public boolean isTransition(String ref, String alt) {
		
		String accumulated = ref + alt;
		if(accumulated.equalsIgnoreCase("AG") || accumulated.equalsIgnoreCase("GA") || accumulated.equalsIgnoreCase("CT") || accumulated.equalsIgnoreCase("TC")) {
			return true;
		}		
		return false;
	}	
	
	
	public ArrayList<String> getNamesFromArray(ArrayList<Individuals> arrayOfIndividuals) {
		ArrayList<String> arrayOfNames = new ArrayList<String>();
		
		for(Individuals individuals: arrayOfIndividuals) {
			arrayOfNames.add(individuals.name);
		}
		
		return arrayOfNames;
	}

	
}
