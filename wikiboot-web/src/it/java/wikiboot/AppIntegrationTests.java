package wikiboot;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.OutputCapture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * Integration tests for {@link wikiboot.App},
 */
public class AppIntegrationTests {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void startApplication() throws Exception {
        App.main(getArgs());
        assertThat(output(), containsString("Started App in"));
        assertThat(output(), not(containsString("WARN")));
        assertThat(output(), not(containsString("ERROR")));
        assertThat(output(), not(containsString("FATAL")));
    }

    private String output() {
        return this.outputCapture.toString();
    }

    private String[] getArgs() {
        List<String> list = new ArrayList<>(Arrays.asList("--server.port=0", "--spring.main.showBanner=false"));
        return list.toArray(new String[list.size()]);
    }

}
