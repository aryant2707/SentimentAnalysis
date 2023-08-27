package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkThingy(String filePath, Dictionary dictionary) {
        assertEquals(filePath, dictionary.getPath());
    }
}