package ua.edu.ucu.queue;

public class Queue {
    private ImmutableLinkedList queueList;

    public Queue(Object[] objects) {
        queueList = new ImmutableLinkedList(objects);
    }

    public Object peek() {
        // Returns the object at the beginning of the Queue without removing it
        return queueList.getFirst();
    }

    public Object dequeue() {
        // Removes and returns the object at the beginning of the Queue.
        Object firstElement = queueList.getFirst();
        try {
            queueList = queueList.removeFirst();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return firstElement;
    }

    public void enqueue(Object e) {
        // Adds an object to the end of the Queue.
        this.queueList = queueList.addLast(e);
    }

    public int size() {
        return this.queueList.size();
    }

    public ImmutableLinkedList getQueueList() {
        return this.queueList;
    }
}
