package ua.edu.ucu.queue;


public class ImmutableLinkedList implements ImmutableList {
    static class Node {
        private Object value;
        private Node prevNode;
        private Node nextNode;

        private Node(Object val) {
            value = val;
            prevNode = null;
            nextNode = null;
        }

        private Node(Object val, Node prevVal, Node nextVal) {
            value = val;
            prevNode = prevVal;
            nextNode = nextVal;
        }

        public void setValue(Object val) {
            this.value = val;
        }

        public void setPrevNode(Node prevN) {
            this.prevNode = prevN;
        }

        public void setNextNode(Node nextN) {
            this.nextNode = nextN;
        }

        public Object getValue() {
            return value;
        }

        public Node getPrevNode() {
            return prevNode;
        }

        public Node getNextNode() {
            return nextNode;
        }
    }

    private Node first;
    private Node last;
    private int linkedListSize;

    public ImmutableLinkedList(Object[] objects) {
        linkedListSize = objects.length;

        if (objects.length == 0) {
            first = null;
            return;
        }

        first = new Node(objects[0]);

        Node current = first;
        int count = 0;
        for (Object obj: objects) {
            if (count == 0) {
                count++;
                continue;
            }
            Node newNode = new Node(obj, current, null);
            current.setNextNode(newNode);
            current = newNode;
            count++;
        }

        last = current;
    }

    public ImmutableLinkedList(Node firstNode, Node lastNode, int size) {
        if (firstNode == null) {
            this.first = null;
            this.last = null;
            this.linkedListSize = size;
            return;
        }
        this.first = new Node(firstNode.getValue());
        Node oldCurrent = firstNode;
        Node newCurrent = this.first;
        while (oldCurrent.getNextNode() != null) {
            newCurrent.setNextNode(
                    new Node(oldCurrent.getNextNode().getValue()));
            newCurrent.getNextNode().setPrevNode(newCurrent);
            newCurrent = newCurrent.getNextNode();
            oldCurrent = oldCurrent.getNextNode();
        }
        this.last = newCurrent;
        this.linkedListSize = size;
    }

    @Override
    public ImmutableList add(Object e) {
        return add(linkedListSize, e);
    }

    @Override
    public ImmutableList add(int index, Object e) {
        return addAll(index, new Object[] {e});
    }

    @Override
    public ImmutableList addAll(Object[] c) {
        return addAll(linkedListSize, c);
    }

    private boolean addElementsAtPositionIfNeeded(int counter, int index,
                                                  Node curr, Object[] e) {
        Node current = curr;
        if (counter == index) {
            Node newNode = new Node(e[0], current, null);  // Create new Node
            Node continueNode;
            if (current.getNextNode() != null) {
                // Node to link to after addition is finished
                continueNode = new Node(current.getNextNode().getValue(),
                        null, current.getNextNode().getNextNode());
            } else {
                continueNode = null;
            }
            // Set new element as next
            current.setNextNode(newNode);
            current = newNode;
            int innerCount = 0;
            for (Object obj: e) {
                if (innerCount == 0) {
                    innerCount++;
                    continue;
                }
                current.setNextNode(new Node(obj, current, null));
                current = current.getNextNode();
                innerCount++;
            }
            if (continueNode != null) {
                current.setNextNode(continueNode);
                continueNode.setPrevNode(current);
            }
            return true;
        }
        return false;
    }

    @Override
    public ImmutableList addAll(int index, Object[] c) {
        if (index > linkedListSize) {
            throw new IndexOutOfBoundsException();
        }

        if (first == null) {
            return new ImmutableLinkedList(c);
        }

        ImmutableLinkedList newList = new ImmutableLinkedList(
                first, last, linkedListSize + c.length);

        Node current = newList.first;

        for (int counter = 0; counter < linkedListSize; counter++) {
            if (addElementsAtPositionIfNeeded(counter, index - 1, current, c)) {
                break;
            }
            if (counter != linkedListSize - 1) {
                current = current.getNextNode();
            }
        }
        if (addElementsAtPositionIfNeeded(linkedListSize, index, current, c)) {
            newList.last = current.getNextNode();
        }
        return newList;
    }

    @Override
    public Object get(int index) {
        if (index >= linkedListSize || isEmpty()) {
            throw new IndexOutOfBoundsException();
        }

        Node current = first;
        int counter = 0;
        while (current.getNextNode() != null) {
            if (counter == index) {
                break;
            }
            current = current.getNextNode();
            counter++;
        }
        return current.getValue();
    }

    @Override
    public ImmutableList remove(int index) {
        if (index >= linkedListSize || isEmpty()) {
            throw new IndexOutOfBoundsException();
        }

        ImmutableLinkedList newList = new ImmutableLinkedList(
                first, last, linkedListSize - 1);

        if (index == 0) {  // Corner case. Remove first
            newList.first = newList.first.getNextNode();
            return newList;
        } else if (index == linkedListSize - 1) {  // Corner case. Remove last
            newList.last = newList.last.getPrevNode();
            newList.last.setNextNode(null);
            return newList;
        }

        int counter = 0;
        Node current = newList.first;

        while (current != null) {
            if (counter == index) {
                current.getPrevNode().setNextNode(current.getNextNode());
                current.getNextNode().setPrevNode(current.getPrevNode());
                break;
            }
            current = current.getNextNode();
            counter++;
        }
        return newList;
    }

    @Override
    public ImmutableList set(int index, Object e) {
        if (index >= linkedListSize) {
            throw new IndexOutOfBoundsException();
        }

        ImmutableLinkedList newList = new ImmutableLinkedList(
                first, last, linkedListSize);

        Node current = newList.first;
        int counter = 0;
        while (current.getNextNode() != null) {
            if (counter == index) {
                current.setValue(e);
                break;
            }
            current = current.getNextNode();
            counter++;
        }
        return newList;
    }

    @Override
    public int indexOf(Object e) {
        Node current = first;
        int counter = 0;
        while (current.getNextNode() != null) {
            if (current.getValue().equals(e)) {
                return counter;
            }
            current = current.getNextNode();
            counter++;
        }
        return -1;
    }

    @Override
    public int size() {
        return linkedListSize;
    }

    @Override
    public ImmutableList clear() {
        return new ImmutableLinkedList(new Object[0]);
    }

    @Override
    public boolean isEmpty() {
        return linkedListSize == 0;
    }

    @Override
    public Object[] toArray() {
        Object[] objArray = new Object[linkedListSize];
        Node current = first;

        for (int counter = 0; counter < linkedListSize; counter++) {
            objArray[counter] = current.getValue();
            current = current.getNextNode();
        }
        return objArray;
    }

    public ImmutableLinkedList addFirst(Object e) {
        ImmutableLinkedList newList = new ImmutableLinkedList(
                first, last, linkedListSize + 1);

        Node newNode = new Node(e, null, newList.first);
        if (newList.first != null) {
            newList.first.setPrevNode(newNode);
            newList.first = newNode;
        } else {
            newList.first = newNode;
            newList.last = newNode;
        }

        return newList;
    }

    public ImmutableLinkedList addLast(Object e) {
        ImmutableLinkedList newList = new ImmutableLinkedList(
                first, last, linkedListSize + 1);

        Node newNode = new Node(e, newList.last, null);
        if (newList.last != null) {
            newList.last.setNextNode(newNode);
            newList.last = newNode;
        } else {
            newList.first = newNode;
            newList.last = newNode;
        }

        return newList;
    }

    public Object getFirst() {
        if (first == null) {
            return null;
        }
        return first.getValue();
    }

    public Object getLast() {
        if (last == null) {
            return null;
        }
        return last.getValue();
    }

    public ImmutableLinkedList removeFirst() {
        return (ImmutableLinkedList) remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return (ImmutableLinkedList) remove(linkedListSize - 1);
    }
}
