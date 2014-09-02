package wikiboot.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * A {@link HandlerExceptionResolver} that handles all exception not in the {@code servletPathPrefixesToExclude} exclusion list.
 * <p>
 * A prefix to exclude could be {@code /api} - a REST api with it's own exception handlers. Note that the excludes must
 * include the leading slash ("/"). It will match against the leading part of {@link javax.servlet.http.HttpServletRequest#getServletPath()},
 * and although that method may return an empty String, an empty String is not a valid exclusion path here.
 *
 * @author Bjørn Erik Pedersen
 */
public class PathExcludingExceptionResolver implements HandlerExceptionResolver, Ordered {

    private static final String EXCEPTION_ATTRIBUTE = "exception";
    private static final String STATUS_ATTRIBUTE = "status";
    private static final int DEFAULT_STATUS_CODE = 500;
    private static final Logger logger = LoggerFactory.getLogger(PathExcludingExceptionResolver.class);
    private final String[] servletPathPrefixesToExclude;
    /**
     * need to be early - before the others
     */
    private int order = Ordered.HIGHEST_PRECEDENCE;
    private Map<String, ?> defaultModelAttributes;
    private final Map<Class<? extends Exception>, HttpStatus> exceptionStatusCodeMapping;


    public PathExcludingExceptionResolver(Map<Class<? extends Exception>, HttpStatus> exceptionStatusCodeMapping, String... servletPathPrefixesToExclude) {
        Assert.notEmpty(servletPathPrefixesToExclude, "'servletPathPrefixesToExclude' must be provided");
        for (String path : servletPathPrefixesToExclude) {
            Assert.state(path != null && path.length() > 1 && path.startsWith("/"), "Exclusion path must not be empty and must start with leading slash");
        }
        this.servletPathPrefixesToExclude = servletPathPrefixesToExclude;
        this.exceptionStatusCodeMapping = exceptionStatusCodeMapping;
    }

    public PathExcludingExceptionResolver(Map<Class<? extends Exception>, HttpStatus> exceptionStatusCodeMapping, Map<String, ?> defaultModelAttributes,
                                          String... servletPathPrefixesToExclude) {
        this(exceptionStatusCodeMapping, servletPathPrefixesToExclude);
        this.defaultModelAttributes = defaultModelAttributes;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        for (String pathToExclude : servletPathPrefixesToExclude) {
            if (request.getServletPath().startsWith(pathToExclude)) {
                return null;
            }
        }

        HttpStatus status = exceptionStatusCodeMapping.get(ex.getClass());
        Integer statusCode = status != null ? status.value() : DEFAULT_STATUS_CODE;
        applyStatusCodeIfPossible(request, response, statusCode);
        return new ModelAndView("error", createModelMap(statusCode, ex));
    }

    private ModelMap createModelMap(int statusCode, Exception ex) {
        ModelMap model = new ModelMap();
        if (defaultModelAttributes != null) {
            model.addAllAttributes(defaultModelAttributes);
        }
        model.addAttribute(EXCEPTION_ATTRIBUTE, ex);
        model.addAttribute(STATUS_ATTRIBUTE, statusCode);
        return model;
    }

    /**
     * Apply the specified HTTP status code to the given response if not executing within an include request.
     */
    protected void applyStatusCodeIfPossible(HttpServletRequest request, HttpServletResponse response, int statusCode) {
        if (!WebUtils.isIncludeRequest(request)) {
            logger.debug("Applying HTTP status code " + statusCode);
            response.setStatus(statusCode);
            request.setAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE, statusCode);
        }
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
