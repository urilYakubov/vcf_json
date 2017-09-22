package exercise;

import java.util.HashMap;

/* This class is responsible to keep scores of each individual. Snptype 
 * instant variable is HashMap that map reference to HashMap of alt variants.
 * For Example reference A map to set of "A", "C", "T", "G", "N". Constructor
 * through function buildSnpType create mapping of each reference to set of
 * alt variants. Other functions are set, get and increase of each instant
 * variable of the class. 
 * 
 */

public class Scores {

	final String variants[] = {"A", "C", "T", "G", "N" };
	private HashMap<String, HashMap<String, Long>> snpType;
	private String individualName;
	private long indelsCount;
	private long structVariantsCount;
	private long transitionCount;
	private long transversionCount;
	
	
	Scores() {
		snpType = new HashMap<String, HashMap<String, Long>>();
		buildSnpType();
	}
	
	private void buildSnpType() {
		
		for(String outerVariant : variants) {
			HashMap<String, Long> innerMap = new HashMap<String, Long>();
			for(String innerVariant : variants) {
				innerMap.put(innerVariant, (long) 0);
			}
			snpType.put(outerVariant, innerMap);
		}
	}

	public HashMap<String, HashMap<String, Long>> getSnpType() {
		return snpType;
	}

	public void setSnpType(HashMap<String, HashMap<String, Long>> snpType) {
		this.snpType = snpType;
	}

	public String getIndividualName() {
		return individualName;
	}

	public void setIndividualName(String individualName) {
		this.individualName = individualName;
	}

	public long getIndelsCount() {
		return indelsCount;
	}

	public void setIndelsCount(long indelsCount) {
		this.indelsCount = indelsCount;
	}

	public long getStructVariantsCount() {
		return structVariantsCount;
	}

	public void setStructVariantsCount(long structVariantsCount) {
		this.structVariantsCount = structVariantsCount;
	}

	public long getTransitionCount() {
		return transitionCount;
	}

	public void setTransitionCount(long transitionCount) {
		this.transitionCount = transitionCount;
	}

	public long getTransversionCount() {
		return transversionCount;
	}

	public void setTransversionCount(long transversionCount) {
		this.transversionCount = transversionCount;
	}

	public String[] getVariants() {
		return variants;
	}
	
	public void increaseIndelsCount() {
		this.indelsCount++;
	}
	
	public void increaseIndelsCount(int count) {
		this.indelsCount += count;
	}
	
	public void increaseStructVariantsCount() {
		this.structVariantsCount++;
	}
	
	public void increaseStructVariantsCount(int count) {
		this.structVariantsCount += count;
	}
	
	public void increaseTransitionCount() {
		this.transitionCount++;
	}
	
	public void increaseTransitionCount(int count) {
		this.transitionCount += count;
	}
	
	public void increaseTransversionCount() {
		this.transversionCount++;
	}
	
	public void increaseTransversionCount(int count) {
		this.transversionCount += count;
	}
	
	public void increaseSnpCount(String ref, String alt) {
		if(snpType.containsKey(ref)) {
			HashMap<String, Long> alts = snpType.get(ref);
			if(alts.containsKey(alt)) {
				Long score = alts.get(alt);
				score++;
				alts.put(alt, score);
			}
		}
	}
	
	public void increaseSnpCount(String ref, String alt, int count) {
		if(snpType.containsKey(ref)) {
			HashMap<String, Long> alts = snpType.get(ref);
			if(alts.containsKey(alt)) {
				Long score = alts.get(alt);
				score = score + (long) count;
				alts.put(alt, score);
			}
		}
	}
	
}
