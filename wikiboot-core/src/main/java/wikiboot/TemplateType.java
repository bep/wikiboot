package wikiboot;

/**
 * A type of template (FreeMarker, ...).
 *
 * @author Bjørn Erik Pedersen
 */
public enum TemplateType {

    FREEMARKER("ftl");

    private final String shortName;

    TemplateType(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }
}
