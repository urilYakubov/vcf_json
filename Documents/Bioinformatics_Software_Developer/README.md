# vcf_json

This project uses external jar to generate a JSON formatted file: json-simple-1.1.1.jar. This jar file should be included inside the MANIFEST.MF of exercise-0.0.1-SNAPSHOT.jar as a Class-Path variable. For example in my case name of the directory which is located in json-simple-1.1.1.jar is /home/uril/Documents/Bioinformatics_Software_Developer and I updated Class-Path inside MANIFEST.MF as: Class-Path: /home/uril/Documents/Bioinformatics_Software_Developer/json-simple-1.1.1.jar

The program accepts two parametrs from the command line: full path of VCF file and output directory. Processor object is a control object that is the back bone of the operation. It receives a VCF format file name and name of the output directory from Main. First of all it validates the existence of a VCF format file and output directory. After the validation is finished it processes the VCF_file object as well as updates the Scores object and calls JSONOutput object to create JSON format file for each individual.

Run jar from the comand line: java  -jar exercise-0.0.1-SNAPSHOT.jar first_parameter second_parameter
