package exercise;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/* This class is a control class that back bone of operation. It receives 
 * VCF format file name and name of output directory from Main. First 
 * of all it validate existence of VCF format file and output directory. After
 * validation is finished it process VCF file and call output class to create JSON
 * format file for each individual. 
 * 
 */

public class Processor {
	
	private String vcfFilename;
	private String outputDirectory;
	private HashMap<String, Scores> individualsScores;
		
	public Processor(String vcfFilename, String outputDirectory) throws Exception {
		
		this.vcfFilename = vcfFilename;
		this.outputDirectory = outputDirectory;
		
		Validate();
		
		individualsScores = new HashMap<String, Scores>();
		
		Process();
	}
	
	private void Process() throws Exception {
		VCF_File vcffile = new VCF_File(this);		
		JSONOutput json = new JSONOutput(this);
	}

	public String getVcfFilename() {
		return vcfFilename;
	}

	public void setVcfFilename(String vcfFilename) {
		this.vcfFilename = vcfFilename;
	}
	
	
	private void Validate() throws Exception {
		// First check if input vcf file exist
		File f = new File(vcfFilename);
		if(!f.exists() || f.isDirectory()) { 
		    throw new Exception("Input VCF file " + vcfFilename + " does not exist or it is directory");
		}
		
		f = new File(outputDirectory);
		if(!f.exists() || !f.isDirectory()) { 
		    throw new Exception("Output directory " + outputDirectory + "does not exist");
		}		
		
	}
	
	public void buildindIvidualScores(ArrayList<String> individuals) {
		
		for(String individual: individuals) {
			Scores marks = new Scores();
			marks.setIndividualName(individual);
			individualsScores.put(individual, marks);
		}
	}
	
	public HashMap<String, Scores> getIndividualsScores() {
		
		return individualsScores;
	}
	
	public String getOutputDirectory() {
		return outputDirectory;
	}

}
