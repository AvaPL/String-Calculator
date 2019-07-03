import junit.framework.AssertionFailedError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PrivateMethodTester {
    private Method methodToBeTested;

    public PrivateMethodTester(Class<?> targetClass, String name, Class<?>... parameterTypes) {
        try {
            methodToBeTested = targetClass.getDeclaredMethod(name, parameterTypes);
            methodToBeTested.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new AssertionFailedError(e.getMessage());
        }
    }

    public Object safeInvoke(Object obj, Object... args) {
        try {
            return methodToBeTested.invoke(obj, args);
        } catch (IllegalAccessException e) {
            throw new AssertionFailedError(e.getMessage());
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof RuntimeException)
                throw (RuntimeException) e.getCause();
            else
                throw new AssertionFailedError(e.getMessage());
        }
    }
}
