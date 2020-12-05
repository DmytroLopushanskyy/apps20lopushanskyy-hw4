package ua.edu.ucu.tries;

import ua.edu.ucu.iterator.IteratorManager;

public interface Trie {
    IteratorManager getIteratorManager();

    void add(Tuple word);

    boolean contains(String word);

    boolean delete(String word);

    Iterable<String> words();

    Iterable<String> wordsWithPrefix(String pref);

    int size();
}
