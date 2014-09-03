package wikiboot.render;

/**
 * Holder class for a template (FreeMarker, ...).
 * @author Bjørn Erik Pedersen
 */
public class TemplateSource {

    private final String id;
    private final String content;
    private final Long lastModified;

    public TemplateSource(String id, String content, Long lastModified) {
        this.id = id;
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

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
