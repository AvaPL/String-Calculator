import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class GetDelimitersTest {
    private StringCalculator stringCalculator;
    private PrivateMethodTester getDelimitersTester;

    @Before
    public void setUp() {
        stringCalculator = new StringCalculator();
        getDelimitersTester = new PrivateMethodTester(StringCalculator.class, "getDelimiters", String.class);
    }

    private String[] getDelimiters(String numbers) {
        return (String[]) getDelimitersTester.safeInvoke(stringCalculator, numbers);
    }

    @Test
    public void shouldReturnCommaAndNewLineDelimitersForEmptyString() {
        assertArrayEquals(new String[]{",", "\n"}, getDelimiters(""));
    }

    @Test
    public void shouldReturnCommaAndNewLineDelimitersForSingleNumber() {
        assertArrayEquals(new String[]{",", "\n"}, getDelimiters("42"));
    }

    @Test
    public void shouldReturnCommaAndNewLineDelimitersForThreeNumbers() {
        assertArrayEquals(new String[]{",", "\n"}, getDelimiters("2,15\n7"));
    }

    @Test
    public void shouldReturnCustomDelimiterForStringWithPrefix() {
        assertArrayEquals(new String[]{";"}, getDelimiters("//;\n2;15\n7"));
    }

    @Test
    public void shouldReturnCustomDelimiterWithRepeatingCharacter() {
        assertArrayEquals(new String[]{";;;"}, getDelimiters("//[;;;]\n2;;;15\n7"));
    }

    @Test
    public void shouldReturnCustomDelimiterWithDifferentCharacters() {
        assertArrayEquals(new String[]{"^test$"}, getDelimiters("//[^test$]\n2^test$15\n7"));
    }
}
