package comparison;

import java.io.FileWriter;
import java.io.IOException;

public class TerribleFlowB {
    public void write(String name, String content) throws IOException {
        FileWriter fw = new FileWriter("target/" + name + ".txt");
        fw.write(content);
        fw.flush();
        // missing close
    }
}
