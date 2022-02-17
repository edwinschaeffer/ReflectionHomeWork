package vo;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;

public class MerlinApplication {

	private static final String runeStoneFile = "./runeStone.csv";
	
	public static void main(String[] args) throws IOException, CsvValidationException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Reader reader = Files.newBufferedReader(Paths.get(runeStoneFile));
		CSVReader headerReader = new CSVReader(reader);
		String[] headerLine = headerReader.readNext();
		reader.reset();
		CsvToBean<RuneStone> runeRecords = new CsvToBeanBuilder<RuneStone>(reader).withType(RuneStone.class).withSkipLines(0).build();
		List<RuneStone> runeRecordList = runeRecords.parse();
		String decipheredMessage = "";
		for (RuneStone r : runeRecordList) {
			for (int i = 0; i < headerLine.length; i++) {
				Method m = RuneStone.class.getMethod("get" + headerLine[i].substring(0, 1).toUpperCase() + headerLine[i].substring(1));
				String phrase = (String) m.invoke(r);
				String decipheredLetter = phrase.substring(i, i+1);
				decipheredMessage += decipheredLetter; 
			}
		}
		System.out.println(decipheredMessage);
		headerReader.close();
	}
}
