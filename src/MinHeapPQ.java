import java.util.ArrayList;
import java.util.HashMap;


/**
 * Priority queue that supports decreasing priority value in logarithmic time.
 *
 * @author Vincent Cicirello
 * @version CSIS4463
 */
public class MinHeapPQ<E>  {

    private class PQEntry {

        E element;
        int value;

        PQEntry(E element, int value) {
            this.element = element;
            this.value = value;
        }
    }

    private ArrayList<PQEntry> heap;
    private HashMap<E,Integer> index;

    /**
     * Constructs an empty priority queue.
     */
    public MinHeapPQ() {
        heap = new ArrayList<PQEntry>();
        index = new HashMap<E,Integer>();
    }

    /**
     * Adds an element to the priority queue provided either: (a) the MinHeapPQ doesn't contain
     * another identical element, or (b) the MinHeapPQ does contain an identical
     * element, but the new one has lower priority value.
     * In the latter case, the existing element's priority is decreased, and MinHeapPQ is restructured
     * accordingly.
     *
     * @param e The element to add to priority queue
     * @param v The priority value of e.
     * @return true if the element was added, or false if it was not added due to existing element
     * but with lower priority value.
     */
    public boolean offer(E e, int v) {

        int i = heap.size();
        if (index.containsKey(e)) {
            i = index.get(e);
            if (v >= heap.get(i).value) {
                return false;
            }
            heap.get(i).value = v;
        } else {
            heap.add(new PQEntry(e,v));
        }
        while (i > 0 && heap.get((i-1)/2).value > heap.get(i).value) {
            PQEntry parent = heap.get((i-1)/2);
            PQEntry child = heap.get(i);
            heap.set((i-1)/2, child);
            heap.set(i, parent);
            index.put(parent.element,i);
            i = (i-1)/2;
        }
        index.put(e,i);

        return true;
    }

    /**
     * Accesses the top of the MinHeapPQ.  Does not change the MinHeapPQ
     * @return The element with lowest priority value.  Returns null if empty.
     */
    public E peek() {
        return (heap.size()>0) ? heap.get(0).element : null;
    }

    /**
     * Accesses and returns the priority value of the top of the MinHeapPQ.  Does not change the MinHeapPQ.
     * @return The value of the element with lowest priority value.  Throws a bounds exception if empty.
     */
    public int peekPriority() {
        return heap.get(0).value;
    }

    /**
     * Gets the priority value of an element that is in the priority queue.
     * @param element the element whose priority to get.  throws an exception if the element is not in the MinHeapPQ
     * @return the priority of element
     */
    public int getPriority(E element) {
        return heap.get(index.get(element)).value;
    }

    /**
     * Checks if an element is in the MinHeapPQ
     * @param element The element to check.
     * @return true if the MinHeapPQ contains the element, and false otherwise
     */
    public boolean inPQ(E element) {
        return index.containsKey(element);
    }

    /**
     * Accesses and removes the top of the MinHeapPQ.
     * @return The element with lowest priority value.  Returns null if empty.
     */
    public E poll() {
        if (heap.size()==0) return null;
        PQEntry top = heap.get(0);
        index.remove(top.element);

        if (heap.size()>1) {
            heap.set(0,heap.remove(heap.size()-1));
            index.put(heap.get(0).element, 0);
            int p = 0;
            while (2*p+1 < heap.size()) {
                int c = 2*p+1;
                if (c+1 < heap.size() && heap.get(c).value > heap.get(c+1).value) {
                    c = c+1;
                }
                if (heap.get(p).value > heap.get(c).value) {
                    PQEntry child = heap.get(c);
                    PQEntry parent = heap.get(p);
                    heap.set(c, parent);
                    heap.set(p, child);
                    index.put(parent.element, c);
                    index.put(child.element, p);
                } else {
                    break;
                }
                p = c;
            }
        } else {
            heap.remove(0);
        }

        return top.element;
    }


    /**
     * Gets the number of elements in this MinHeapPQ.
     * @return number of elements in the MinHeapPQ.
     */
    public int size() {
        return heap.size();
    }

    /**
     * Checks if the MinHeapPQ is empty.
     */
    public boolean isEmpty() {
        return heap.size()==0;
    }


    protected String ignoreHereForTesting() {
        String result = "";
        for (PQEntry e : heap) {
            result += "(" + e.element + ", " + e.value + ", " + index.get(e.element) + "); ";
        }
        result += "\n";
        return result;
    }

}