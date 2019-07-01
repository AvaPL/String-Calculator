import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringCalculatorTest {
    StringCalculator stringCalculator;

    @Before
    public void setUp() {
        stringCalculator = new StringCalculator();
    }

    @Test
    public void shouldReturn0ForEmptyString() {
        assertEquals(0, stringCalculator.add(""));
    }

    @Test
    public void shouldReturnNumberForOneNumberInString() {
        assertEquals(2, stringCalculator.add("2"));
    }

    @Test
    public void shouldReturnSumForTwoNumbersSeparatedByComma() {
        assertEquals(5, stringCalculator.add("2,3"));
    }
}