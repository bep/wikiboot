package wikiboot.render;

import wikiboot.ArticleSet;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Allowing overrides (translation etc.) for values in data sets' model.
 *
 * @author Bjørn Erik Pedersen
 */
@Entity
@IdClass(ValueOverrideId.class)
public class ValueOverride implements Serializable {

    @Id
    private String key;

    @Id
    private String oldValue;

    private String newValue;

    @Id
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "ARTICLE_SET_ID", nullable = false, updatable = true)
    private ArticleSet articleSet;

    ValueOverride() {
    }

    private ValueOverride(String propertyPath, String oldValue, String newValue) {
        this.key = propertyPath;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public static ValueOverride of(String propertyPath, String oldValue, String newValue) {
        return new ValueOverride(propertyPath, oldValue, newValue);
    }


    public String getKey() {
        return key;
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public ArticleSet getArticleSet() {
        return articleSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValueOverride that = (ValueOverride) o;

        if (!key.equals(that.key)) return false;
        if (!oldValue.equals(that.oldValue)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + oldValue.hashCode();
        return result;
    }
}

class ValueOverrideId implements Serializable {
    private String key, oldValue;
    private ArticleSet articleSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValueOverrideId that = (ValueOverrideId) o;

        if (!articleSet.equals(that.articleSet)) return false;
        if (!key.equals(that.key)) return false;
        if (!oldValue.equals(that.oldValue)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + oldValue.hashCode();
        result = 31 * result + articleSet.hashCode();
        return result;
    }
}
