package ro.ubb.cs.re.jcm.controller;


import lombok.Data;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CsvConverterTests {

	@Test
	public void testReadCsvSuccessful() throws IOException {
		String inp = "fname,lname,univ\nana,1,1.2";
		InputStream is = new ByteArrayInputStream(inp.getBytes());
		CsvConverter csv = new CsvConverter();
		assertEquals("ana", csv.read(TestData.class, is).get(0).getFName());
	}

	@Test
	public void testWriteCsvSuccessful() throws IOException {
		CsvConverter csv = new CsvConverter();
		TestData testData = new TestData();

		testData.setFName("rac");
		testData.setLName(3);
		testData.setUniv(3.2f);

		List<Object> l1 = new ArrayList<>();
		l1.add(testData);

		OutputStream os = new ByteArrayOutputStream();
		csv.write(TestData.class, l1, os);
		assertEquals("fname,lname,univ\nrac,3,3.2\n", os.toString());
	}

	@Data
	private static class TestData {
		String fName;
		Integer lName;
		Float univ;
	}
}
