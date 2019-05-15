package pp.pokemon.pm.common.util.collection;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

@Component
public class CollectionUtil {

    public static<T> boolean isEmpty(Collection<T> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    public static<T> boolean isNotEmpty(Collection<T> collection) {
        return !CollectionUtils.isEmpty(collection);
    }
}
