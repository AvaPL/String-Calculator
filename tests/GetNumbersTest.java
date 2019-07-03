import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class GetNumbersTest {
    private StringCalculator stringCalculator;
    private PrivateMethodTester getNumbersTester;

    @Before
    public void setUp() {
        stringCalculator = new StringCalculator();
        getNumbersTester = new PrivateMethodTester(StringCalculator.class, "getNumbers", String.class, String[].class);
    }

    private int[] getNumbers(String numbers, String[] delimiters) {
        return (int[]) getNumbersTester.safeInvoke(stringCalculator, numbers, delimiters);
    }

    @Test
    public void shouldReturnEmptyArrayForPrefixOnly() {
        assertArrayEquals(new int[]{}, getNumbers("//$\n", new String[]{"$"}));
    }

    @Test
    public void shouldReturnOneNumberForDefaultDelimiters() {
        assertArrayEquals(new int[]{11}, getNumbers("11", new String[]{",", "\n"}));
    }

    @Test
    public void shouldReturnThreeNumbersForDefaultDelimiters() {
        assertArrayEquals(new int[]{11, 12, 7}, getNumbers("11,12\n7", new String[]{",", "\n"}));
    }

    @Test
    public void shouldReturnOneNumberForCustomDelimiter() {
        assertArrayEquals(new int[]{11}, getNumbers("//$\n11", new String[]{"$"}));
    }

    @Test
    public void shouldReturnThreeNumbersForCustomDelimiter() {
        assertArrayEquals(new int[]{11, 12, 7}, getNumbers("//#\n11#12#7", new String[]{"#"}));
    }

    @Test
    public void shouldReturnNegativeNumbersForDefaultDelimiters() {
        assertArrayEquals(new int[]{11, -12, -7}, getNumbers("11,-12\n-7", new String[]{",", "\n"}));
    }

    @Test
    public void shouldReturnNegativeNumbersForCustomDelimiter() {
        assertArrayEquals(new int[]{11, -12, -7}, getNumbers("//#\n11#-12#-7", new String[]{"#"}));
    }
}
