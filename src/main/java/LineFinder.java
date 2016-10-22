import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineFinder {

    private static final String REGEX = "\\W+";
    private static final String REPLACE_PATTERN = "[!?,']";

    public String findLineByLine(String filename) throws IOException {
        File file = new File(filename);
        String longestLine = "";
        long numberOfWordsInLine = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                int length = replace(line).split(REGEX).length;
                if (length > numberOfWordsInLine) {
                    longestLine = line;
                    numberOfWordsInLine = length;
                }
            }
        }
        return longestLine;
    }

    public String findLineInMemory(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        return lines.stream()
                .sorted((s1, s2) -> replace(s1).split(REGEX).length > replace(s2).split(REGEX).length ? -1 : 1)
                .findFirst()
                .get();
    }

    public String replace(String str) {
        return str.replaceAll(REPLACE_PATTERN, "");
    }
}
