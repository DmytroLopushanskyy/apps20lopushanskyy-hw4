package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

/**
 *
 * @author andrii and and Dmytro Lopushanskyy
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trieInput) {
        this.trie = trieInput;
    }

    public int load(String... strings) {
        int counter = 0;
        for (String string: strings) {
            String[] strArray;
            if (string.contains(" ")) {
                strArray = string.split("\\s+");
            } else {
                strArray = new String[] {string};
            }
            for (String word: strArray) {
                if (word.length() > 2){
                    System.out.println(word);
                    this.trie.add(new Tuple(word, word.length()));
                    counter++;
                }
            }
        }
        return counter;
    }

    public boolean contains(String word) {
        return this.trie.contains(word);
    }

    public boolean delete(String word) {
        return this.trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return this.trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        this.trie.getIteratorManager().changeIterator("get kth size");
        return this.trie.getIteratorManager().getIterator().iterator(pref, k);
    }

    public int size() {
        return this.trie.size();
    }
}
