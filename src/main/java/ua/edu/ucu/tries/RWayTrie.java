package ua.edu.ucu.tries;

import ua.edu.ucu.iterable.IterableCollection;
import ua.edu.ucu.iterator.IteratorManager;
import ua.edu.ucu.iterator.StringIterator;


public class RWayTrie implements Trie, IterableCollection {
    public final static int R = 26;  // radix
    private static char[] alphabet = new char[] {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z'};
    private Node root;          // root of trie
    private IteratorManager iteratorManager;

    public static class Node {
        private Object val;
        private Node[] next = new Node[R];

        public Object getVal() {
            return val;
        }

        public Node[] getNext() {
            return next;
        }
    }

    public RWayTrie() {
        this.iteratorManager = new IteratorManager("get all items", this);
    }

    @Override
    public IteratorManager getIteratorManager() {
        return this.iteratorManager;
    }

    @Override
    public void add(Tuple t) {
        root = put(root, t.term, t.weight, 0);
    }

    private Node put(Node x, String key, int val, int d) {
        // Change value associated with key if in subtrie rooted at x.
        Node node = x;
        if (x == null) {
            node = new Node();
        }
        if (d == key.length()) {
            node.val = val;
            return node;
        }
        // Use dth key char to identify subtrie.
        int c = charToPosition(key.charAt(d));
        node.next[c] = put(node.next[c], key, val, d+1);
        return node;
    }

    @Override
    public boolean contains(String word) {
        Node x = get(root, word, 0);
        return x != null;
    }

    public Node get(Node x, String key, int d) {
        // Return value associated with key in the subtrie
        // rooted at x.
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            return x;
        }
        // Use dth key char to identify subtrie.
        int c = charToPosition(key.charAt(d));
        return get(x.next[c], key, d+1);
    }

    @Override
    public boolean delete(String word) {
        root = delete(root, word, 0);
        return root != null;
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) {
            return null;
        }

        if (d == key.length()) {
            x.val = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d+1);
        }
        if (x.val != null) {
            return x;
        }
        for (char c = 0; c < R; c++){
            if (x.next[c] != null) {
                return x;
            }
        }
        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        this.iteratorManager.changeIterator("get all items");
        return this.iteratorManager.getIterator().iterator(s);
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        int cnt = 0;
        if (x.val != null) {
            cnt++;
        }
        for (char c = 0; c < R; c++) {
            cnt += size(x.next[c]);
        }
        return cnt;
    }

    private static int charToPosition(Character c) {
        return new String(alphabet).indexOf(c);
    }

    public static char positionToChar(int pos) {
        return alphabet[pos];
    }

    public StringIterator createIterator(String code) {
        return this.iteratorManager.getIterator();
    }

    public Node getRoot() {
        return root;
    }
}
