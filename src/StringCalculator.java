import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {
    private Pattern delimiterPrefixRegex = Pattern.compile("//\\D\\n");
    private Pattern numbersRegex = Pattern.compile("\\d+(?:\\D\\d+)*");

    public int add(String numbers) {
        if (numbers.isEmpty())
            return 0;
        String[] delimiters = getDelimiters(numbers);
        int[] parsedNumbers = getNumbers(numbers, delimiters);
        return Arrays.stream(parsedNumbers).sum();
    }

    private String[] getDelimiters(String numbers) {
        Matcher matcher = delimiterPrefixRegex.matcher(numbers);
        if (matcher.find())
            return extractCustomDelimiter(numbers, matcher.end() - 1);
        else
            return new String[]{",", "\n"}; //Default delimiters.
    }

    private String[] extractCustomDelimiter(String numbers, int prefixEndIndex) {
        String delimiter = numbers.substring(2, prefixEndIndex); //Removed leading slashes.
        return new String[]{delimiter};
    }

    private int[] getNumbers(String numbers, String[] delimiters) {
        Matcher matcher = numbersRegex.matcher(numbers);
        if (matcher.find())
            return parseNumbers(numbers, delimiters, matcher.start());
        else
            return new int[]{}; //Only delimiters in string.
    }

    private int[] parseNumbers(String numbers, String[] delimiters, int numbersStartIndex) {
        String numbersWithoutPrefix = numbers.substring(numbersStartIndex, numbers.length());
        String delimitersRegex = getDelimitersRegex(delimiters);
        return Arrays.stream(numbersWithoutPrefix.split(delimitersRegex)).mapToInt(Integer::parseInt).toArray();
    }

    private String getDelimitersRegex(String[] delimiters) {
        return "[" + Arrays.stream(delimiters).collect(Collectors.joining()) + "]";
    }
}
