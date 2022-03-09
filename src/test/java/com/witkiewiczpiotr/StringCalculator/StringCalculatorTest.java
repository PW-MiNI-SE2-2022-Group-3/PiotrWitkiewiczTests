package com.witkiewiczpiotr.StringCalculator;

import static com.witkiewiczpiotr.StringCalculator.StringCalculatorApplication.sumString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StringCalculatorTest {

	@Test
	void shouldReturnZeroForEmptyString() {
		// given
		String input = "";

		// when
		int output = sumString(input);

		// then
		assertThat(output).isEqualTo(0);
	}

	@ParameterizedTest
	@MethodSource("singleNumberSource")
	void shouldReturnSingleValueGivenSingleNumber(String input, Integer correctOutput) {
		// when
		int output = sumString(input);

		// then
		assertThat(output).isEqualTo(correctOutput);
	}

	private static Stream<Arguments> singleNumberSource() {
		return Stream.of(
				Arguments.of("20", 18),
				Arguments.of("14", 14),
				Arguments.of("0", 0)
		);
	}

	@ParameterizedTest
	@MethodSource("commaDelimitedSource")
	void shouldReturnCorrectSumGivenCommaDelimitedNumbers(String input, Integer correctOutput) {
		// when
		int output = sumString(input);

		// then
		assertThat(output).isEqualTo(correctOutput);
	}

	private static Stream<Arguments> commaDelimitedSource() {
		return Stream.of(
				Arguments.of("10,20,15", 45),
				Arguments.of("8,8,0", 16),
				Arguments.of("0,1,2,3,4", 10)
		);
	}

	@ParameterizedTest
	@MethodSource("newLineDelimitedSource")
	void shouldReturnCorrectSumGivenNewLineDelimitedNumbers(String input, Integer correctOutput) {
		// when
		int output = sumString(input);

		// then
		assertThat(output).isEqualTo(correctOutput);
	}

	private static Stream<Arguments> newLineDelimitedSource() {
		return Stream.of(
				Arguments.of("10\n20\n15", 45),
				Arguments.of("8\n8\n0", 16),
				Arguments.of("01\n2\n3\n4", 10)
		);
	}

	@ParameterizedTest
	@MethodSource("delimitedSource")
	void shouldReturnCorrectSumGivenDelimitedNumbers(String input, Integer correctOutput) {
		// when
		int output = sumString(input);

		// then
		assertThat(output).isEqualTo(correctOutput);
	}

	private static Stream<Arguments> delimitedSource() {
		return Stream.of(
				Arguments.of("10\n20,15\n5", 50),
				Arguments.of("8\n8\n0,20\n4", 40),
				Arguments.of("0,1\n2\n3\n4,3,1", 14)
		);
	}

	@Test
	void shouldThrowExceptionGivenNegativeInputy() {
		// given
		String input = "2,-1,0,3";

		// then
		assertThatThrownBy(() -> sumString(input))
				.isInstanceOf(NegativeNumberException.class);
	}

	@ParameterizedTest
	@MethodSource("bigNumbersSource")
	void shouldCorrectlyIgnoreBigNumbers(String input, Integer correctOutput) {
		// when
		int output = sumString(input);

		// then
		assertThat(output).isEqualTo(correctOutput);
	}

	private static Stream<Arguments> bigNumbersSource() {
		return Stream.of(
				Arguments.of("10\n2000,15\n5", 30),
				Arguments.of("1,1000,1001", 1001),
				Arguments.of("1000", 1000),
				Arguments.of("1001", 0)
		);
	}

	@ParameterizedTest
	@MethodSource("additionalSingleCharDelimiterSource")
	void shouldCorrectlyHandleAdditionalSingleCharParameter(String input, Integer correctOutput) {
		// when
		int output = sumString(input);

		// then
		assertThat(output).isEqualTo(correctOutput);
	}

	private static Stream<Arguments> additionalSingleCharDelimiterSource() {
		return Stream.of(
				Arguments.of("//%\n10%15", 25),
				Arguments.of("//$\n1,2,3$4", 10)
		);
	}

	@ParameterizedTest
	@MethodSource("additionalStringDelimiterSource")
	void shouldCorrectlyHandleAdditionalStringParameter(String input, Integer correctOutput) {
		// when
		int output = sumString(input);

		// then
		assertThat(output).isEqualTo(correctOutput);
	}

	private static Stream<Arguments> additionalStringDelimiterSource() {
		return Stream.of(
				Arguments.of("//[%%%]\n10%%%15", 25)
		);
	}

	@ParameterizedTest
	@MethodSource("additionalStringDelimitersSource")
	void shouldCorrectlyHandleAdditionalStringParameters(String input, Integer correctOutput) {
		// when
		int output = sumString(input);

		// then
		assertThat(output).isEqualTo(correctOutput);
	}

	private static Stream<Arguments> additionalStringDelimitersSource() {
		return Stream.of(
				Arguments.of("//[%][!]\n10%15!3", 28)
		);
	}
}
