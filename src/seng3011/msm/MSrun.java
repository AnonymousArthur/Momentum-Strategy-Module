package seng3011.msm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
//Momentum Strategy Module Execution module version 1.8.1
//UNSW CSE SENG3011 Team Awesome Copyright Reserved

public class MSrun {
	// Uasge: java -jar MSM.jar FILE_NAME PARAMETER_FILE_NAME
	public static String csvPath;
	public static String outputPath;
	private static String parametersPath;
	public static String version = "1.8.1";

	public static void main(String[] args) throws ParseException {
		long time = System.currentTimeMillis();
		if (args.length == 0) {
			System.out
					.println("Usage: java -jar MSM.jar FILE_NAME PARAMETER_FILE_NAME");
			System.exit(1);
		}
		csvPath = args[0];
		int window = 0;
		double threshold = 0;
		Date startDate = null;
		Date endDate = null;
		parametersPath = args[1];
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(parametersPath));
			String line = "";
			SimpleDateFormat fmttmp = new SimpleDateFormat("dd-MMM-yyy",
					Locale.ENGLISH);

			startDate = fmttmp.parse("01-JAN-1000");
			endDate = fmttmp.parse("01-JAN-3000");
			while ((line = br.readLine()) != null) {
				line = line.replaceAll("\\s", "");
				String[] tmp = line.split("=");
				// System.out.println(tmp[1]);
				if (tmp[0].equals("window")) {
					window = Integer.parseInt(tmp[1]);
				} else if (tmp[0].equals("threshold")) {
					threshold = Double.parseDouble(tmp[1]);
				} else if (tmp[0].equals("startDate") && tmp[1] != null) {
					SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyy",
							Locale.ENGLISH);
					startDate = fmt.parse(tmp[1]);
					// System.out.println(startDate);
				} else if (tmp[0].equals("endDate") && tmp[1] != null) {
					SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyy",
							Locale.ENGLISH);
					endDate = fmt.parse(tmp[1]);
				} else if (tmp[0].equals("output")) {
					outputPath = tmp[1];
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (window <= 0 || threshold < 0 || endDate.before(startDate)) {
			System.out.println("Parameters not enough or wrong date parameter");
			System.exit(1);
		}
		// System.out.println(startDate);
		long parse = System.currentTimeMillis();
		ArrayList<TradeRec> tradeRecs = CSVParser.CSVParse(csvPath, startDate,
				endDate);
		System.out.println("Time to parse input: " + (System.currentTimeMillis() - parse) + "ms");

		long generate = System.currentTimeMillis();
		GenerateOrder strategy = new GenerateOrder(window, threshold);
		strategy.generate(tradeRecs);
		System.out.println("Time to generate output: " + (System.currentTimeMillis() - generate) + "ms");
		System.out.println("Proceess finished. Please check output files.");
		System.out.println("Time elapsed: " + (System.currentTimeMillis() - time) + "ms");
	}
}
