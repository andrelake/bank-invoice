package com.andrelake.bank_invoice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BankInvoiceApplicationTests {

	DecimalFormat df;

	String path;
	List<String> lines;

	@BeforeEach
	void setUp() throws IOException {
		path = Paths.get("src/main/resources/static/extrato_fake.csv").toAbsolutePath().toString();
		lines = Files.readAllLines(Paths.get(path));
		df = new DecimalFormat("####0.00");  
	}

	@Test
	void sumTransactions() throws IOException {
		var sum = lines.stream()
		.mapToDouble(line -> Double.parseDouble(line.split(",")[1]))
		.reduce(0.0, Double::sum);
		
		System.out.println(df.format(sum));
	}		
	
	@Test
	void countTransactionsByMonth() throws IOException {
		var dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		var total = 0.0;

		for (String line : lines) {
			var date = LocalDate.parse(line.split(",")[0], dateFormatter);
			if(date.getMonth() == Month.JANUARY) {
				var amount = Double.parseDouble(line.split(",")[1]);
				total += amount;
			}
		}

		System.out.println(df.format(total));
	}	

}
