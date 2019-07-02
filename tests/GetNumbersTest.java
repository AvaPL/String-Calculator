import org.junit.Before;

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
}
