package com.bridgeLabs.csvHandler;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class OpenCsvBuilder implements ICsvBuilder {

	@Override
	public <T> Iterator<T> getIteratorFromCsv(Reader reader, Class<T> csvBindedClass) throws CsvException {
		try {
			CsvToBean<T> csvToBean = this.getCsvToBean(reader, csvBindedClass);
			Iterator<T> censusCsvIterator = csvToBean.iterator();
			return censusCsvIterator;
		} catch (RuntimeException e) {
			if (ExceptionUtils.indexOfType(e, CsvDataTypeMismatchException.class) != -1) {
				throw new CsvException("Incorrect Type", CsvExceptionType.INCORRECT_TYPE);
			} else if (ExceptionUtils.indexOfType(e, CsvRequiredFieldEmptyException.class) != -1) {
				if (e.getMessage().equals("Error capturing CSV header!")) {
					throw new CsvException("Incorrect Header", CsvExceptionType.INCORRECT_HEADER);
				} else {
					throw new CsvException("Incorrect Delimiter", CsvExceptionType.INCORRECT_DELIMITER);
				}
			} else {
				throw new CsvException("Unable to parse", CsvExceptionType.UNABLE_TO_PARSE);
			}
		}
	}

	@Override
	public <T> List<T> getListFromCsv(Reader reader, Class<T> csvBindedClass) throws CsvException {
		try {
			CsvToBean<T> csvToBean = this.getCsvToBean(reader, csvBindedClass);
			List<T> censusCsvList = csvToBean.parse();
			return censusCsvList;
		} catch (RuntimeException e) {
			if (ExceptionUtils.indexOfType(e, CsvDataTypeMismatchException.class) != -1) {
				throw new CsvException("Incorrect Type", CsvExceptionType.INCORRECT_TYPE);
			} else if (ExceptionUtils.indexOfType(e, CsvRequiredFieldEmptyException.class) != -1) {
				if (e.getMessage().equals("Error capturing CSV header!")) {
					throw new CsvException("Incorrect Header", CsvExceptionType.INCORRECT_HEADER);
				} else {
					throw new CsvException("Incorrect Delimiter", CsvExceptionType.INCORRECT_DELIMITER);
				}
			} else {
				throw new CsvException("Unable to parse", CsvExceptionType.UNABLE_TO_PARSE);
			}
		}
	}

	private <T> CsvToBean<T> getCsvToBean(Reader reader, Class<T> csvBindedClass) throws RuntimeException {
		CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
		csvToBeanBuilder.withType(csvBindedClass);
		csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
		CsvToBean<T> csvToBean = csvToBeanBuilder.build();
		return csvToBean;
	}
}
