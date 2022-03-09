package com.witkiewiczpiotr.StringCalculator;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StringCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(StringCalculatorApplication.class, args);
	}

	public static Integer sumString(String input) {
		if (input.isEmpty()) {
			return 0;
		}

		String additionalDelimiter = ",";

		if (input.startsWith("//[")) {
			input = input.substring(3);
			if (input.contains("][")) {
				var delimiters = input.split("]\\[");
				var builder = new StringBuilder();

				for (String delim : delimiters) {
					if(delim.contains("\n"))
						delim = delim.split("\n")[0];

					if (delim.contains("]")) {
						delim = delim.replaceAll("]", "");
					}
					builder.append(delim);
					builder.append("|");
				}
				builder.deleteCharAt(builder.length() - 1);
				additionalDelimiter = builder.toString();
			} else {
				additionalDelimiter = input.split("]")[0];
				if (additionalDelimiter.equals("$"))
					additionalDelimiter = "\\$";
			}

			input = input.split("\n", 2)[1];
		} else if (input.startsWith("//")) {
			additionalDelimiter = input.substring(2, 3);
			input = input.split("\n", 2)[1];

			if (additionalDelimiter.equals("$"))
				additionalDelimiter = "\\$";
		}

		var numbers = Arrays.stream(input.split(",|\n|" + additionalDelimiter)).map(Integer::parseInt)
				.collect(Collectors.toList());

		if (numbers.stream().anyMatch(i -> i < 0)) {
			throw new NegativeNumberException();
		}

		var list = numbers.stream().filter(i -> i <= 1000).collect(Collectors.toList());

		if (list.isEmpty())
			return 0;

		var sum = list.stream().reduce(Integer::sum);
		if (sum.isPresent())
			return sum.get();

		throw new UnsupportedOperationException();
	}
}
