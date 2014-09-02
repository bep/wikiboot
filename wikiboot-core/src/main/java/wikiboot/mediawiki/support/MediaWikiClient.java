package wikiboot.mediawiki.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import wikiboot.mediawiki.ParsedWikiText;

/**
 * Client for the MediaWiki API.
 * <p>
 * todo lots ...
 *
 * @author Bjørn Erik Pedersen
 */
@Service
public class MediaWikiClient {

    private static final Logger logger = LoggerFactory.getLogger(MediaWikiClient.class);

    public static final long CACHE_TTL = 120;
    public static final String CACHE_NAME = "mediaWikiClientCache";

    private final RestTemplate restTemplate = new RestTemplate();

    // todo categories
    private final String test = "http://nn.wikipedia.org/w/api.php?action=parse&format=json&prop=text";

    @Cacheable(CACHE_NAME)
    public ParsedWikiText previewArticle(String wikiText, String wikiTitle) {
        MultiValueMap<String, String> mvm = new LinkedMultiValueMap<>();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

        mvm.add("text", wikiText);
        mvm.add("title", wikiTitle);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(mvm, requestHeaders);

        logger.info("Getting wiki preview:  " + wikiTitle);
        ResponseEntity<ParsedWikiText> response = restTemplate.exchange(test, HttpMethod.POST, requestEntity, ParsedWikiText.class);

        return response.getBody();
    }
}
