import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {
    private static final Pattern delimiterPrefixRegex = Pattern.compile("//\\D\\n|//\\[\\D+\\]\\n");
    private static final Pattern numbersRegex = Pattern.compile("\\d+(?:\\D\\d+)*");
    private static final int maxNumberValue = 1000;

    public int add(String numbers) {
        String[] delimiters = getDelimiters(numbers);
        int[] parsedNumbers = getNumbers(numbers, delimiters);
        return sumNumbers(parsedNumbers);
    }

    private String[] getDelimiters(String numbers) {
        Matcher matcher = delimiterPrefixRegex.matcher(numbers);
        if (matcher.find())
            return extractCustomDelimiter(numbers, matcher.end() - 1); //Prefix end is '\n'.
        else
            return new String[]{",", "\n"}; //Default delimiters.
    }

    private String[] extractCustomDelimiter(String numbers, int prefixEndIndex) {
        if (isDelimiterInBrackets(prefixEndIndex))
            //Left bracket is at index 3, right bracket is at index prefixEndIndex - 1 eg. //[;;]\n1;;2
            return new String[]{numbers.substring(3, prefixEndIndex - 1)};
        else //Without brackets delimiter is exactly at index 2 eg. //;\n1;2.
            return new String[]{numbers.substring(2, 3)};
    }

    private boolean isDelimiterInBrackets(int prefixEndIndex) {
        //For square brackets delimiter prefix end is at index at least 5 eg. //[;]\n1;2.
        //For single character delimiter prefix end is exactly at index 3 eg. //;\n1;2.
        return prefixEndIndex != 3;
    }

    private int[] getNumbers(String numbers, String[] delimiters) {
        Matcher matcher = numbersRegex.matcher(numbers);
        if (matcher.find())
            return parseNumbers(numbers, delimiters, matcher.start());
        else
            return new int[]{}; //Empty string or only prefix in string.
    }

    private int[] parseNumbers(String numbers, String[] delimiters, int numbersStartIndex) {
        String numbersWithoutPrefix = numbers.substring(numbersStartIndex, numbers.length());
        String delimitersRegex = getDelimitersRegex(delimiters);
        return Arrays.stream(numbersWithoutPrefix.split(delimitersRegex)).mapToInt(Integer::parseInt).toArray();
    }

    private String getDelimitersRegex(String[] delimiters) {
        //Delimiters are mapped using Pattern.quote in case they contain a special regex character eg. $.
        return Arrays.stream(delimiters).map(delimiter -> Pattern.quote(delimiter)).collect(Collectors.joining("|"));
    }

    private int sumNumbers(int[] parsedNumbers) {
        checkInputCorrectness(parsedNumbers);
        return Arrays.stream(parsedNumbers).filter(x -> x <= maxNumberValue)
                     .sum(); //Ignores numbers bigger than maxNumberValue.
    }

    private void checkInputCorrectness(int[] parsedNumbers) {
        int[] negatives = Arrays.stream(parsedNumbers).filter(x -> x < 0).toArray();
        if (negatives.length > 0)
            throw new IllegalArgumentException("Negatives not allowed: " + Arrays.toString(negatives));
    }
}
