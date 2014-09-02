package wikiboot;

import wikiboot.support.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Template used by the article generator.
 *
 * @author Bjørn Erik Pedersen
 */
@Entity
public class Template extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TemplateType type = TemplateType.FREEMARKER;

    @Column(nullable = false, length = 20000)
    private String content;

    Template() {
    }

    private Template(String content) {
        this.content = content;
    }

    private Template(TemplateType type, String content) {
        this.type = type;
        this.content = content;
    }

    public static Template from(String content) {
        return new Template(content);
    }

    public static Template from(Template template) {
        return new Template(template.getType(), template.getContent());
    }

    public TemplateType getType() {
        return type;
    }

    public void setType(TemplateType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Template{");
        sb.append(super.toString()).append(", ");
        sb.append("type=").append(type);
        sb.append(", content.length()='").append(content.length()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
