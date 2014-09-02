package wikiboot;

import org.junit.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Utils used for testing.
 * <p>
 * todo move this to common test jar.
 *
 * @author Bjørn Erik Pedersen
 */
public class TestUtils {

    private TestUtils() {

    }

    /**
     * Verifies that a utility class is well defined.
     *
     * @param clazz utility class to verify.
     */
    public static void assertUtilityClassWellDefined(final Class<?> clazz) throws Exception {
        String name = clazz.getName();
        assertTrue(String.format("Class %s must be final", name),
                Modifier.isFinal(clazz.getModifiers()));
        assertEquals(String.format("There must be only one constructor in %s", name), 1,
                clazz.getDeclaredConstructors().length);
        final Constructor<?> constructor = clazz.getDeclaredConstructor();
        if (constructor.isAccessible()
                || !Modifier.isPrivate(constructor.getModifiers())) {
            fail(String.format("Constructor in %s is not private", name));
        }
        constructor.setAccessible(true);
        constructor.newInstance();
        constructor.setAccessible(false);
        for (final Method method : clazz.getMethods()) {
            if (!Modifier.isStatic(method.getModifiers())
                    && method.getDeclaringClass().equals(clazz)) {
                fail(String.format("There exists a non-static method %s in %s", method, name));
            }
        }
    }

}
