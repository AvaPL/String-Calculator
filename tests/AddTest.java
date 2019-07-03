import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddTest {
    private StringCalculator stringCalculator;

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

    @Test
    public void shouldReturnSumForThreeNumbersSeparatedByComma() {
        assertEquals(9, stringCalculator.add("2,3,4"));
    }

    @Test
    public void shouldReturnSumForFiveNumbersSeparatedByComma() {
        assertEquals(20, stringCalculator.add("2,3,4,5,6"));
    }

    @Test
    public void shouldReturnSumForTwoNumbersSeparatedByNewLine() {
        assertEquals(5, stringCalculator.add("2\n3"));
    }

    @Test
    public void shouldReturnSumForFiveNumbersSeparatedByCommaOrNewLine() {
        assertEquals(20, stringCalculator.add("2,3\n4\n5,6"));
    }

    @Test
    public void shouldReturnSumForTwoNumbersSeparatedByCustomDelimiter() {
        assertEquals(5, stringCalculator.add("//$\n2$3"));
    }

    @Test
    public void shouldReturnSumForFiveNumbersSeparatedByCustomDelimiter() {
        assertEquals(20, stringCalculator.add("//%\n2%3%4%5%6"));
    }

    @Test
    public void shouldThrowExceptionForNegativesInInput() {
        try {
            stringCalculator.add("//%\n2%-3%4%-5%6");
            fail("Exception not thrown");
        }
        catch (IllegalArgumentException e){
            assertEquals("Negatives not allowed: [-3, -5]", e.getMessage());
        }
    }

    @Test
    public void shouldIgnoreNumbersBiggerThan1000() {
        assertEquals(1001, stringCalculator.add("//%\n1000%1%1001"));
    }
}

