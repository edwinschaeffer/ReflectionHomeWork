package vo;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;

public class MerlinApplication {

	private static final String runeStoneFile = "./runeStone.csv";
	
	public static void main(String[] args) throws IOException, CsvValidationException {
		Reader reader = Files.newBufferedReader(Paths.get(runeStoneFile));
		CSVReader headerReader = new CSVReader(reader);
		String[] headerLine = headerReader.readNext();
		reader.reset();
		headerReader.close();
		CsvToBean<RuneStone> runeRecords = new CsvToBeanBuilder<RuneStone>(reader).withType(RuneStone.class).withSkipLines(0).build();
		List<RuneStone> runeRecordList = runeRecords.parse();
		for (RuneStone r : runeRecordList) {
			System.out.println(r.getScribe0());
		}

	}

}
