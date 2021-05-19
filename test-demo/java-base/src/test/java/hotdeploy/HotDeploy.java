package hotdeploy;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

public class HotDeploy {
    public static void main(String[] args) {
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        list(new File(url.getPath()));
    }

    public static void list(File file) {
        if (file.isFile()) {
            System.out.println(file.getName());
            System.out.println(String.format("%s\n \t lastmodifier: %d\n", file.toPath(), file.lastModified()));
        } else {
            Arrays.stream(file.listFiles()).forEach(item -> {
                list(item);
            });
        }
    }
}
