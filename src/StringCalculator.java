import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {
    final String inputStringRegex = "(?://\\D\\n)?\\d+(?:(?:\\D\\d+)+)?"; //Might not be needed.
    Pattern delimiterPrefix = Pattern.compile("//\\D\\n");

    public int add(String numbers) {
        if (numbers.isEmpty())
            return 0;
        String[] delimiters = getDelimiters(numbers);
        String delimitersRegex = getDelimitersRegex(delimiters);
        //TODO: Add numbers string.
        return Arrays.stream(numbers.split(delimitersRegex)).mapToInt(Integer::parseInt).sum();
    }

    private String[] getDelimiters(String numbers) {
        Matcher matcher = delimiterPrefix.matcher(numbers);
        if (matcher.find())
            return extractCustomDelimiter(numbers, matcher.end() - 1);
        else
            return new String[]{",", "\n"};
    }

    private String[] extractCustomDelimiter(String numbers, int prefixEndIndex) {
        String delimiter = numbers.substring(2, prefixEndIndex); //Removes leading slashes.
        return new String[]{delimiter};
    }

    private String getDelimitersRegex(String[] delimiters){
        return "[" + Arrays.stream(delimiters).collect(Collectors.joining()) + "]";
    }

    private int[] getNumbers(String numbers, String[] delimiters) {
        return new int[]{};
    }
}
