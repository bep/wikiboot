package wikiboot.render;

/**
 * Holder class for a template (FreeMarker, ...).
 * @author Bjørn Erik Pedersen
 */
public class TemplateSource {

    private final String content;
    private final Long lastModified;

    public TemplateSource(String content, Long lastModified) {
        this.content = content;
        this.lastModified = lastModified;
    }

    public String getContent() {
        return content;
    }

    public Long getLastModified() {
        return lastModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateSource that = (TemplateSource) o;

        if (!content.equals(that.content)) return false;
        return lastModified.equals(that.lastModified);

    }

    @Override
    public int hashCode() {
        int result = content.hashCode();
        result = 31 * result + lastModified.hashCode();
        return result;
    }
}
