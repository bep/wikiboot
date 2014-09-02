package wikiboot.support;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import wikiboot.Article;

/**
 * @author Bjørn Erik Pedersen
 */
@RepositoryRestResource(collectionResourceRel = "article", path = "article")
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findByArticleSetIdAndIndex(Long articleSetId, Integer index);

}
