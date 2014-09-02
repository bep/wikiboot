package wikiboot.support;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import wikiboot.Template;

/**
 * @author Bjørn Erik Pedersen
 */
@RepositoryRestResource(collectionResourceRel = "templates", path = "templates")
public interface TemplateRepository extends JpaRepository<Template, Long> {
}
