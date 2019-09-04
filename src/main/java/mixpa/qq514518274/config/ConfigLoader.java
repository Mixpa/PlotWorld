package mixpa.qq514518274.config;

import java.io.*;

public interface ConfigLoader {
    void load(File file) throws FileNotFoundException, IllegalAccessException;

    void load(Reader reader) throws IllegalAccessException;
}
