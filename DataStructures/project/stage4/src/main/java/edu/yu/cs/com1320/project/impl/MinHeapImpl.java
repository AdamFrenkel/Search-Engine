package edu.yu.cs.com1320.project.impl; //hmmm... maybe in wrong spot

import edu.yu.cs.com1320.project.MinHeap;

import java.lang.reflect.Array;

public class MinHeapImpl<E extends Comparable<E>> extends MinHeap<E> {
    public MinHeapImpl(){
        elements = (E[]) new Comparable[5];
    }
    @Override
    public void reHeapify(E element) {
        int k = this.getArrayIndex(element);
        if(this.isGreater(k/2,k)){
            upHeap(k);
        }
        else{
            if(2*k+1<= elements.length) {
                if (this.isGreater(k, 2 * k) || this.isGreater(k, 2 * k + 1)) {
                    downHeap(k);
                }
            }
        }
    }

    @Override
    protected int getArrayIndex(E element) {
        if(element == null){
            throw new IllegalArgumentException("null element");
        }
        for(int i = 0; i<elements.length; i++){
            if(elements[i] != null)
            if (elements[i].equals(element)){
                return i;
            }
        }
        throw new IllegalArgumentException("element not in array");
    }

    @Override
    protected void doubleArraySize() {
        E[] elementsCopy = elements;
        elements = (E[]) new Comparable[elements.length *2];
        for(int i = 0; i<elementsCopy.length; i++){
            elements[i] = elementsCopy[i];
        }
    }
}
