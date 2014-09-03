package wikiboot;

import org.apache.commons.lang3.builder.ToStringBuilder;
import wikiboot.support.BaseEntity;
import wikiboot.support.ShortToStringStyle;

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
        return new ToStringBuilder(this, ShortToStringStyle.SHORT_TO_STRING_STYLE)
                .appendSuper(super.toString())
                .append("type", type)
                .append("content", content)
                .toString();
    }
}
