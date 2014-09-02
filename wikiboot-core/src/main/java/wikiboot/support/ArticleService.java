package wikiboot.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wikiboot.*;
import wikiboot.render.DataSetRenderer;
import wikiboot.render.OverridenDataSet;
import wikiboot.render.Rendering;
import wikiboot.render.support.DataItemOverrideProvider;

import java.util.List;

/**
 * @author Bjørn Erik Pedersen
 */
@Service
public class ArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    private final DataItemOverrideProvider dataItemOverrideProvider = new DataItemOverrideProvider();

    @Autowired
    ArticleSetRepository articleSetRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    DataSetRenderer articleRenderer;

    @Transactional
    public Article getOrCreateArticle(long articleSetId, int dataSetIndex, DataSet dataSet) {

        Article article = articleRepository.findByArticleSetIdAndIndex(articleSetId, dataSetIndex);
        ArticleSet articleSet;

        if (article == null) {
            articleSet = articleSetRepository.findOne(articleSetId);

            if (articleSet == null) {
                throw new ResourceNotFound("articleSet", articleSetId);
            }
            article = new Article();
            articleSet.addArticle(dataSetIndex, article);

        } else {
            articleSet = article.getArticleSet();
        }

        Template template = article.getArticleSet().getLocalTemplate();
        DataSet overriddenDataSet = new OverridenDataSet(articleSet.getOverrides(), dataSet);
        List<Rendering> rendering = articleRenderer.render(overriddenDataSet, String.valueOf(template.getId()));

        article.setContent(rendering.get(dataSetIndex).getOutput());
        article.setTitle(dataSet.getDataItems().get(dataSetIndex).getTitle());

        if(article.isNew()) {
            article = articleRepository.save(article);
        }

        return article;
    }
}
