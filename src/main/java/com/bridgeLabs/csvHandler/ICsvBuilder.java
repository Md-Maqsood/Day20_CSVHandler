package com.bridgeLabs.csvHandler;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICsvBuilder {
	public <T> Iterator<T> getIteratorFromCsv(Reader reader, Class<T> csvBindedClass) throws CsvException;
	public <T> List<T> getListFromCsv(Reader reader, Class<T> csvBindedClass) throws CsvException;
}
