package ua.edu.ucu.tries;

import ua.edu.ucu.iterator.IteratorManager;

public interface Trie {
    IteratorManager getIteratorManager();

    public void add(Tuple word);

    public boolean contains(String word);

    public boolean delete(String word);

    public Iterable<String> words();

    public Iterable<String> wordsWithPrefix(String pref);

    public int size();
}
