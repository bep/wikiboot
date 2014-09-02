package wikiboot.support;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import wikiboot.ArticleSet;

/**
 * @author Bjørn Erik Pedersen
 */
@RepositoryRestResource(collectionResourceRel = "articles", path = "articles")
public interface ArticleSetRepository extends JpaRepository<ArticleSet, Long> {
}
