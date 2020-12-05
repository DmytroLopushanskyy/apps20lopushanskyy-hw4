package ua.edu.ucu.iterator_templates;

import ua.edu.ucu.iterator.StringIterator;
import ua.edu.ucu.iterator.iteratorAnnotation;
import ua.edu.ucu.queue.Queue;
import ua.edu.ucu.tries.RWayTrie;

import java.util.Iterator;

@iteratorAnnotation(iteratorCode = "get kth size")
public class KthSizeIterator implements StringIterator {
    private int currentIndex = 0;
    private RWayTrie collection;

    public KthSizeIterator(RWayTrie trie) {
        this.collection = trie;
    }

    private int collect(RWayTrie.Node x, String pre, Queue q, int value, int depth) {
        int counter = 0;
        if (x == null) {
            return 0;
        }
        if (depth > value) {
            return 0;
        }
        if (x.val != null && x.val.equals(value)) {
            System.out.println(pre);
            q.enqueue(pre);
            counter++;
        }
        for (int c = 0; c < RWayTrie.R; c++) {
            counter += collect(x.next[c], pre + RWayTrie.positionToChar(c), q, value, depth + 1);
        }
        return counter;
    }

    public Iterable<String> iterator(Object... params) {
        if (!(params[0] instanceof String) || !(params[1] instanceof Integer)) {
            System.out.println("Wrong parameter");
            return null;
        }
        String pre = (String) params[0];
        Integer limit = (Integer) params[1];

        Queue q = new Queue(new String[0]);

        int size = pre.length();
        if (size == 2) {
            size = 3;
        }

        int sizesUsed = 0;
        do {
            int collectedSize = collect(collection.get(collection.root, pre, 0), pre, q, size, pre.length());
            // System.out.println("size: " + size + ". count: " + collectedSize);
            if (collectedSize != 0) {
                sizesUsed++;
            }
            size++;
        } while (sizesUsed != limit && size < 45); // All english words have less length than 45


        return () -> new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return currentIndex < q.size() &&
                        q.queueList.get(currentIndex) != null;
            }

            @Override
            public String next() {
                return (String) q.queueList.get(currentIndex++);
            }
        };
    }
}
