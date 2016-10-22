import org.junit.Test;

import static org.junit.Assert.*;

public class LineFinderTest {

    private static final String expected = "anyone who reads Old and Middle English literary texts will be familiar " +
            "with the mid-brown volumes of the EETS,";
    private static final String FILENAME = "test.txt";

    @Test
    public void shouldFindLineWithMaxCountOfWords() throws Exception {
        LineFinder lineFinder = new LineFinder();
        String readLine = lineFinder.findLineByLine(FILENAME);
        assertEquals(expected, readLine);
        readLine = lineFinder.findLineInMemory(FILENAME);
        assertEquals(expected, readLine);
    }
}