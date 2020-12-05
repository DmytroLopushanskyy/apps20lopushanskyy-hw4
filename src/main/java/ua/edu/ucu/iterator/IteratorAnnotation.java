package ua.edu.ucu.iterator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface IteratorAnnotation {
    String iteratorCode();
}
