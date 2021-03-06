package ua.edu.ucu.iterator.templates;

import ua.edu.ucu.iterator.StringIterator;
import ua.edu.ucu.iterator.IteratorAnnotation;
import ua.edu.ucu.queue.Queue;
import ua.edu.ucu.tries.RWayTrie;

import java.util.Iterator;
import java.util.NoSuchElementException;

@IteratorAnnotation(iteratorCode = "get all items")
public class GetAllIItemsIterator implements StringIterator {
    private int currentIndex = 0;
    private RWayTrie collection;

    public GetAllIItemsIterator(RWayTrie trie) {
        this.collection = trie;
    }

    private void collect(RWayTrie.Node x, String pre, Queue q) {
        if (x == null) {
            return;
        }
        if (x.getVal() != null) {
            q.enqueue(pre);
        }
        for (int c = 0; c < RWayTrie.R; c++) {
            collect(x.getNext()[c], pre + RWayTrie.positionToChar(c), q);
        }
    }

    public Iterable<String> iterator(Object... params) {
        if (!(params[0] instanceof String)) {
            System.out.println("Wrong parameter");
            return null;
        }
        String param = (String) params[0];

        Queue q = new Queue(new String[0]);
        collect(collection.get(collection.getRoot(), param, 0), param, q);

        return () -> new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return currentIndex < q.size()
                        && q.getQueueList().get(currentIndex) != null;
            }

            @Override
            public String next() {
                String nextEl = (String) q.getQueueList().get(currentIndex++);
                if (nextEl == null) {
                    throw new NoSuchElementException();
                }
                return nextEl;
            }
        };
    }
}
