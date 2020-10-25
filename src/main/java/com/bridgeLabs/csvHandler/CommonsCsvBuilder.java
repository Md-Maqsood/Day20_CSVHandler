package com.bridgeLabs.csvHandler;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CommonsCsvBuilder implements ICsvBuilder {

	@Override
	public <T> Iterator<T> getIteratorFromCsv(Reader reader, Class<T> csvBindedClass) throws CsvException {
		try {
			List<T> recordObjects = this.getListFromCsv(reader, csvBindedClass);
			Iterator<T> csvIterator = recordObjects.iterator();
			return csvIterator;
		} catch (Exception e) {
			throw new CsvException("Unable to parse", CsvExceptionType.UNABLE_TO_PARSE);
		}
	}

	@Override
	public <T> List<T> getListFromCsv(Reader reader, Class<T> csvBindedClass) throws CsvException {
		CSVParser csvParser = null;
		CSVFormat csvFormat = CSVFormat.DEFAULT;
		try {
			csvParser = new CSVParser(reader, csvFormat);
			List<CSVRecord> records = csvParser.getRecords();
			records.remove(0);
			List<T> recordObjects = new ArrayList<T>();
			for (CSVRecord record : records) {
				List<String> recordStrings = new ArrayList<String>();
				for (String recordString : record) {
					recordStrings.add(recordString);
				}
				Constructor<T> constructor = csvBindedClass.getConstructor(new Class[] { List.class });
				T csvObject = constructor.newInstance(recordStrings);
				recordObjects.add(csvObject);
			}
			return recordObjects;
		} catch (Exception e) {
			throw new CsvException("Unable to parse", CsvExceptionType.UNABLE_TO_PARSE);
		} finally {
			try {
				csvParser.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
