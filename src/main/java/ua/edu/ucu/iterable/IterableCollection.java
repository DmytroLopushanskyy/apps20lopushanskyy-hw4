package ua.edu.ucu.iterable;

import ua.edu.ucu.iterator.StringIterator;

public interface IterableCollection {
    StringIterator createIterator(String code);
}
