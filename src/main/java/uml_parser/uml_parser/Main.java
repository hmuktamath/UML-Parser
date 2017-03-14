package uml_parser.uml_parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.plantuml.SourceStringReader;

public class Main {

	public static void main(String[] input) throws IOException, ClassNotFoundException {
		// String srcDir = input[0];
		// String destFile = input[1];
		// write logic to get all the java files in srcDir
		String[] files = new String[1];
		files[0] = new String(
				"/Users/harsha.muktamath/Documents/college/uml-parser/src/main/java/uml_parser/uml_parser/Table.java");
		Map<String, List<List>> completeParsedData = new HashMap<String, List<List>>();
		SourceCodeParser srcParser = new SourceCodeParser();
		PlantUMLdatagenerator plantUMLdatagenerator = new PlantUMLdatagenerator();
		for (String file : files) {
			System.out.println("Going through the file : " + file);
			Map<String, List<List>> parsedDataofFile = srcParser.getParsedData(file);
			completeParsedData.putAll(parsedDataofFile);
		}

		String umlData = plantUMLdatagenerator.generateUMLData(completeParsedData);
	//	System.out.println(umlData);
		File pngFile = new File("/tmp/monday.png");
		OutputStream png = new FileOutputStream(pngFile);
		
		SourceStringReader reader = new SourceStringReader(umlData);
		// Write the first image to "png"
		String desc = reader.generateImage(png);
		// Use Platnt UML lib to generate image with uml data as input.

	}

}
