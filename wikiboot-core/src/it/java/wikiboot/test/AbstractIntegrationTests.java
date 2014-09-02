package wikiboot.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wikiboot.CoreTestConfig;
import wikiboot.support.ArticleRepository;
import wikiboot.support.ArticleSetRepository;
import wikiboot.support.TemplateRepository;

import javax.persistence.EntityManager;

/**
 * Common base class for the integration tests that do not run in the web container.
 *
 * @author Bjørn Erik Pedersen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CoreTestConfig.class)
public abstract class AbstractIntegrationTests {

    @Autowired
    protected TemplateRepository templateRepository;

    @Autowired
    protected ArticleSetRepository articleSetRepository;

    @Autowired
    protected ArticleRepository articleRepository;

    @Autowired
    protected EntityManager entityManager;

    protected void flush() {
        entityManager.flush();
    }
}
