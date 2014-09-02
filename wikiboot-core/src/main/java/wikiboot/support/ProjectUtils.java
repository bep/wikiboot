package wikiboot.support;

import java.io.File;

/**
 * Project  related utils.
 *
 * @author Bjørn Erik Pedersen
 */
public final class ProjectUtils {

    private ProjectUtils() {
    }

    public static String getProjectHome() {
        String userDir = System.getProperty("user.dir");
        return getProjectHomeFromUserDir(userDir);
    }

    static String getProjectHomeFromUserDir(String userDir) {
        File file = new File(userDir);
        if (!file.getPath().endsWith("wikiboot")) {
            file = file.getParentFile();
        }
        return file.getPath();
    }
}
