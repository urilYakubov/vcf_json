package exercise;

/* Main accept two parameters: VCF format file and output directory 
 * for JSON output files and call for Processor.
 * 
 */

public class Main {
		

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Check how many arguments were passed in
	    if(args.length < 2)
	    {
	        System.out.println("Proper Usage is: full path/filename of vcf format file  and output full path/test of jason file, for example: /home/uril/genotype.vcf  /home/uril ");
	        System.exit(0);
	    }	    
	    
	    String inputFile = args[0];
	    String outputDirectory = args[1];	    
	    
	    try {
			Processor processor = new Processor(inputFile, outputDirectory);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	    

	}

}
