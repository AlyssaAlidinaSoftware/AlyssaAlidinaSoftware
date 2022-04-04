import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



public class PASFileWriter {
	private static String CSV_PATH = "data" + File.separator + "results.csv";
	private static CSVWriter csvWriter;

	public static void savePasswordSessionCSV(CreatePasswordSession cps) {
        try {
			FileWriter writer = new FileWriter(new File(CSV_PATH), true);
			
            csvWriter = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
			
            csvWriter.writeNext(new String[]{cps.getUsername(), 
            		cps.getDatetime(),
            		
            		boolToString(cps.passwordSuccesful(Password.EMAIL)),
            		Integer.toString(cps.getAttemptsByType(Password.EMAIL)),
            		Double.toString(cps.getTotalTime(Password.EMAIL)),
            		Double.toString(cps.getAttemptTime(Password.EMAIL, 1)),
            		Double.toString(cps.getAttemptTime(Password.EMAIL, 2)),
            		Double.toString(cps.getAttemptTime(Password.EMAIL, 3)),
            		
            		boolToString(cps.passwordSuccesful(Password.BANK)),
            		Integer.toString(cps.getAttemptsByType(Password.BANK)),
            		Double.toString(cps.getTotalTime(Password.BANK)),
            		Double.toString(cps.getAttemptTime(Password.BANK, 1)),
            		Double.toString(cps.getAttemptTime(Password.BANK, 2)),
            		Double.toString(cps.getAttemptTime(Password.BANK, 3)),
            		
               		boolToString(cps.passwordSuccesful(Password.SHOPPING)),
            		Integer.toString(cps.getAttemptsByType(Password.SHOPPING)),
            		Double.toString(cps.getTotalTime(Password.SHOPPING)),
            		Double.toString(cps.getAttemptTime(Password.SHOPPING, 1)),
            		Double.toString(cps.getAttemptTime(Password.SHOPPING, 2)),
            		Double.toString(cps.getAttemptTime(Password.SHOPPING, 3)) 
            		}, true);
        
            
            csvWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static String boolToString(boolean b) {
		return b ? "SUCCESS" : "FAIL";
	}
}
