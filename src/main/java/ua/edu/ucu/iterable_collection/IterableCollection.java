package ua.edu.ucu.iterable_collection;

import ua.edu.ucu.iterator.StringIterator;

public interface IterableCollection {
    StringIterator createIterator(String code);
}
