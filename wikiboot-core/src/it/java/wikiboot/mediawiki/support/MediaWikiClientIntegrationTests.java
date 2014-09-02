package wikiboot.mediawiki.support;

import org.junit.Test;
import wikiboot.mediawiki.ParsedWikiText;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link MediaWikiClient}.
 */
public class MediaWikiClientIntegrationTests {

    private final MediaWikiClient client = new MediaWikiClient();

    @Test
    public void testPreviewArticle() throws Exception {
        ParsedWikiText preview = client.previewArticle("== HelloAøÆæÅå == \n\n[Dolly]\n\n", "Title1");
        assertThat(preview.getText(), containsString("HelloAøÆæÅå</span>"));
        assertThat(preview.getText(), containsString("Title1"));
    }
}