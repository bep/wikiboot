package wikiboot;

import org.apache.commons.lang3.builder.ToStringBuilder;
import wikiboot.support.BaseEntity;
import wikiboot.support.ShortToStringStyle;

import javax.persistence.*;

/**
 * Represents an article with title and content.
 *
 * @author Bjørn Erik Pedersen
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"INDEX", "ARTICLE_SET_ID"}))
public class Article extends BaseEntity {

    private String title;
    @Column(nullable = false, length = 20000)
    private String content;
    @JoinColumn(name = "INDEX", nullable = false, updatable = true)
    private Integer index;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "ARTICLE_SET_ID", nullable = false, updatable = true)
    private ArticleSet articleSet;

    public Article() {
    }

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public ArticleSet getArticleSet() {
        return articleSet;
    }

    public void setArticleSet(ArticleSet articleSet) {
        this.articleSet = articleSet;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ShortToStringStyle.SHORT_TO_STRING_STYLE)
                .appendSuper(super.toString())
                .append("title", title)
                .append("content", content)
                .append("index", index)
                .append("articleSet", getArticleSet())
                .toString();
    }


}
