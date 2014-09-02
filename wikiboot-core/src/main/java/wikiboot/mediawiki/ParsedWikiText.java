package wikiboot.mediawiki;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Holder for parsed Wiki text returned from the MediaWiki API.
 *
 * @author Bjørn Erik Pedersen
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParsedWikiText {

    private Warnings warnings;

    @JsonProperty("parse")
    private Result result;

    public Warnings getWarnings() {
        return warnings;
    }

    public Result getResult() {
        return result;
    }

    public String getText() {
        return "<h1>" + getTitle() + "</h1>\n\n" + (this.result.text.values().stream().collect(Collectors.joining("\n\n")));
    }

    public String getTitle() {
        return this.result.title;
    }

    static class Warnings {
        @JsonProperty("parse")
        public Map<String, String> warnings;
    }


    static class Result {
        public String title;
        public Map<String, String> text;

    }
}

