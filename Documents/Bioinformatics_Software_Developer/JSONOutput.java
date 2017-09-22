package exercise;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

/* This class is responsible to output scores of each individual from 
 * individualsScores instance variable of Processor object in JSON format 
 * on specified output directory. Output file is individual + .json. For example
 *  for individual HG000097 output file will be HG000097.json.
 */

public class JSONOutput {

	private Processor processor;
	
	public JSONOutput(Processor processor) throws Exception {
		
		this.processor = processor;
		
		processOutput();
	}
	
	private void processOutput() throws Exception {
		
		if(processor == null) {
			throw new Exception("Processing JSON Error: processor is null");
		}
		
		if(processor.getIndividualsScores() == null) {
			throw new Exception("Processing JSON Error: processor individual scores is null");
		}
		
		for (Scores scores : processor.getIndividualsScores().values()) {
			
			HashMap<String, HashMap<String, Long>> snpType = scores.getSnpType();
			
			if(snpType == null) {
				throw new Exception("Processing JSON Error: snpType is null");
			}
			
			JSONObject mainObject = new JSONObject();
			long variantCount = 0;
			
			JSONObject snpJsonType = new JSONObject();
			for (Entry<String, HashMap<String, Long>> entry : snpType.entrySet()) {
			    String key = entry.getKey();
			    HashMap<String, Long> values = entry.getValue();
			    JSONObject innerSnpJsonType = new JSONObject();
			    for (Entry<String, Long> innerEntry : values.entrySet()) {
			    	String innerKey = innerEntry.getKey();
				    long innerValues = innerEntry.getValue();
				    innerSnpJsonType.put(innerKey, innerValues);
				    variantCount += innerValues;
			    }
			    snpJsonType.put(key, innerSnpJsonType);
			}
			mainObject.put("snps", snpJsonType);
			variantCount += scores.getIndelsCount() + scores.getStructVariantsCount();
			mainObject.put("sample", scores.getIndividualName());
			
			long transitionCount = scores.getTransversionCount();
			if(transitionCount == 0) {
				transitionCount = 1;
			}
			long transversionCount = scores.getTransversionCount();
			if(transversionCount == 0) {
				transversionCount = 1;
			}
			
			mainObject.put("ti-tv", transitionCount/transversionCount );
			mainObject.put("variant_count", variantCount);
			mainObject.put("indel_count", scores.getIndelsCount());
			mainObject.put("sv_count", scores.getStructVariantsCount());
			
			FileWriter file = null;
			try {
				file = new FileWriter(processor.getOutputDirectory() + "/" + scores.getIndividualName() + ".json");
	            file.write(mainObject.toJSONString());
	            file.flush();

	        } catch (IOException e) {
	            e.printStackTrace();
	            throw e;
	        }
			finally {
		        if(file != null) {
		        	file.close();
		        }
		    }

		}
	}
}
