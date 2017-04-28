package uml_parser.uml_parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import net.sourceforge.plantuml.SourceStringReader;

public class Main {

	static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] input) throws IOException, ClassNotFoundException {
		logger.info("Starting application");
		String sourceFolder = input[0];
		String destination = input[1];

		File folder = new File(sourceFolder);

		File[] listOfFiles = folder.listFiles(new java.io.FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				if (pathname.getName().endsWith(".java")) {
					return true;
				}
				return false;
			}
		});

		for (File file : listOfFiles) {

			System.out.println(file.getName());

		}

		Map<String, List> completeParsedData = new HashMap<String, List>();
		SourceCodeParser srcParser = new SourceCodeParser();
		PlantUMLdatagenerator plantUMLdatagenerator = new PlantUMLdatagenerator();

		for (File file : listOfFiles) {
			System.out.println("Going through the file : " + file);
			Map<String, List<List>> parsedDataofFile = srcParser.getParsedData(file);
			completeParsedData.putAll(parsedDataofFile);
		}

		String umlData = plantUMLdatagenerator.generateUMLData(completeParsedData);
		// System.out.println(umlData);
		File pngFile = new File(destination);
		OutputStream png = new FileOutputStream(pngFile);

		SourceStringReader reader = new SourceStringReader(umlData);
		// Write the first image to "png"
		String desc = reader.generateImage(png);
		// Use Platnt UML lib to generate image with uml data as input.

	}

}
