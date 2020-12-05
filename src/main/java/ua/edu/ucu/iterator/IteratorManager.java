package ua.edu.ucu.iterator;

import org.reflections.Reflections;
import ua.edu.ucu.tries.RWayTrie;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class IteratorManager {
    private StringIterator iter;
    private RWayTrie collection;

    public IteratorManager(String code, RWayTrie list) {
        this.collection = list;
        this.iter = findIterator(code);
    }

    public void changeIterator(String code) {
        this.iter = findIterator(code);
    }

    public StringIterator getIterator() {
        return this.iter;
    }

    private StringIterator findIterator(String code) {
        // Define reflections to find the needed iterator
        Reflections ref = new Reflections("ua.edu.ucu.iterator.templates");
        // Find all classes that are iterators in iterator directory
        Set<Class< ? >> iteratorCollection =
                ref.getTypesAnnotatedWith(IteratorAnnotation.class);

        if (iteratorCollection.size() == 0) {
            // If there is no available iterator
            System.out.println("Cannot create an iterator");
            return null;
        }

        // Find the class with the needed id from iteratorCollection
        // and create its instance
        StringIterator iterator = null;
        for (Class< ? > iteratorClass : iteratorCollection) {
            IteratorAnnotation presentAnnotation =
                    iteratorClass.getAnnotation(IteratorAnnotation.class);

            if (presentAnnotation.iteratorCode().equals(code)) {
                try {
                    iterator = (StringIterator) iteratorClass
                            .getConstructor(RWayTrie.class)
                            .newInstance(this.collection);
                } catch (NoSuchMethodException | InstantiationException
                        | IllegalAccessException
                        | InvocationTargetException e) {
                    System.out.println("Wrong iterator constructor");
                    break;
                }
                // The iterator has been successfully found
                break;
            }
        }
        return iterator;
    }
}
