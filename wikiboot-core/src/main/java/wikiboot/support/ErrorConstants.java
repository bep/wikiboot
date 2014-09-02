package wikiboot.support;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import wikiboot.ResourceNotFound;

import java.util.HashMap;
import java.util.Map;

/**
 * Error related constants.
 *
 * @author Bjørn Erik Pedersen
 */
public class ErrorConstants {

    public static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_STATUS_CODE_MAPPING = new HashMap<>();


    static {
        EXCEPTION_STATUS_CODE_MAPPING.put(IllegalArgumentException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_CODE_MAPPING.put(RuntimeException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        EXCEPTION_STATUS_CODE_MAPPING.put(NoHandlerFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS_CODE_MAPPING.put(ResourceNotFound.class, HttpStatus.NOT_FOUND);
    }

    private ErrorConstants() {
    }


}
