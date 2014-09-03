package wikiboot;

import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Allowing overrides (translation etc.) for values in data sets' model.
 *
 * @author Bjørn Erik Pedersen
 */
@Entity
@IdClass(ValueOverride.ValueOverrideId.class)
public class ValueOverride implements Serializable {

    @Id
    private final String key;

    @Id
    private final String oldValue;

    private String newValue;

    @Id
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "ARTICLE_SET_ID", nullable = false, updatable = false)
    private final ArticleSet articleSet;

    private ValueOverride(ArticleSet articleSet, String propertyPath, String oldValue, String newValue) {
        this.articleSet = articleSet;
        this.key = propertyPath;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public static ValueOverride of(ArticleSet articleSet, String propertyPath, String oldValue, String newValue) {
        return new ValueOverride(articleSet, propertyPath, oldValue, newValue);
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


    @Immutable
    static class ValueOverrideId implements Serializable {
        private  String key, oldValue;
        private ArticleSet articleSet;

        private ValueOverrideId() {
        }


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

}
