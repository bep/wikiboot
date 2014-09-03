package wikiboot;

import org.javatuples.KeyValue;
import wikiboot.support.BaseEntity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A set of articles.
 *
 * @author Bjørn Erik Pedersen
 */
@Entity
public class ArticleSet extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "TEMPLATE_ID", nullable = false, updatable = false)
    private Template template;

    @OneToOne(cascade = CascadeType.ALL)
    private Template localTemplate;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "articleSet")
    private Set<Article> articles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "articleSet")
    private Set<ValueOverride> valueOverrides;

    @Transient
    private DataSet dataSet;

    ArticleSet() {
    }

    public ArticleSet(Template template) {
        this.template = template;
        this.localTemplate = Template.from(template);
    }

    public Template getTemplate() {
        return template;
    }

    public Template getLocalTemplate() {
        return localTemplate;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public Set<ValueOverride> getValueOverrides() {
        return valueOverrides;
    }

    public void addArticle(int dataSetIndex, Article article) {
        if (articles == null) {
            articles = new HashSet<>();
        }
        article.setArticleSet(this);

        article.setIndex(dataSetIndex);
        articles.add(article);

    }

    public void addValueOverride(String propertyPath, String oldValue, String newValue) {
        if (this.valueOverrides == null) {
            this.valueOverrides = new HashSet<>();
        }
        this.valueOverrides.add(ValueOverride.of(this, propertyPath, oldValue, newValue));
    }

    @Transient
    public Map<KeyValue<String, String>, String> getOverrides() {
        if (getValueOverrides() == null) {
            return null;
        }
        Map<KeyValue<String, String>, String> map = new HashMap<>();
        for (ValueOverride override : getValueOverrides()) {
            map.put(new KeyValue<>(override.getKey(), override.getOldValue()), override.getNewValue());
        }
        return map;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ArticleSet{");
        sb.append(super.toString()).append(", ");
        sb.append("template=").append(getTemplate());
        sb.append(", localTemplate=").append(getLocalTemplate());
        sb.append('}');
        return sb.toString();
    }
}
