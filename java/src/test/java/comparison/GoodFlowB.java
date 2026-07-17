package comparison;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GoodFlowB {
    public void write(Path path, String content) throws IOException {
        Files.writeString(path, content);
    }
}
