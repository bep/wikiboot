package wikiboot.support;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import wikiboot.TestUtils;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ProjectUtilsTests {

    private static String oldUserDir;

    @BeforeClass
    public static void before() {
        oldUserDir = System.getProperty("user.dir");
        System.setProperty("user.dir",  "/home/wikiboot/client");
    }

    @AfterClass
    public static void after() {
        System.setProperty("user.dir", oldUserDir);
    }

    @Test
    public void getProjectHome() throws Exception {
        assertThat(ProjectUtils.getProjectHome(), is( "/home/wikiboot"));
    }

    @Test
    public void getProjectHomeFromUserDir() throws Exception {
        String projectHome = "/home/bep/dev/wikiboot";

        assertThat(ProjectUtils.getProjectHomeFromUserDir(projectHome), is(projectHome));
        assertThat(ProjectUtils.getProjectHomeFromUserDir(projectHome + "/wikiboot-client"), is(projectHome));
        assertThat(ProjectUtils.getProjectHomeFromUserDir(projectHome + "/wikiboot-web"), is(projectHome));
    }

    @Test
    public void utilityClassShouldBeWellDefined() throws  Exception {
        TestUtils.assertUtilityClassWellDefined(ProjectUtils.class);
    }
}
