package wikiboot;

/**
 * Exception thrown when a resource is not found.
 *
 * @author Bjørn Erik Pedersen
 */
public class ResourceNotFound extends RuntimeException {

    private final String resource;
    private final Object id;

    public ResourceNotFound(String resource, Object id) {
        super(String.format("Resource '%s' with id '%s' not found", resource, id));
        this.id = id;
        this.resource = resource;
    }

    public Object getId() {
        return id;
    }

    public String getResource() {
        return resource;
    }
}
