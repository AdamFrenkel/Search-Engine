package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.Stack;

import java.util.LinkedList;

public class StackImpl<T> implements Stack<T> {
    LinkedList linkedList;
    int stackSize = 0;
    public StackImpl(){
        linkedList = new LinkedList();
    }
    /**
     * @param element object to add to the Stack
     */
    @Override
    public void push(T element){
        linkedList.addFirst(element);
        this.stackSize++;
    }

    /**
     * removes and returns element at the top of the stack
     * @return element at the top of the stack, null if the stack is empty
     */
    @Override
    public T pop(){
        if (stackSize == 0) {
            return null;
        }
        T returnT = (T) linkedList.removeFirst();
        this.stackSize--;
        return returnT;
    }

    /**
     *
     * @return the element at the top of the stack without removing it
     */
    @Override
    public T peek(){
        if (stackSize == 0) {
            return null;
        }
        return (T) linkedList.getFirst();
    }

    /**
     *
     * @return how many elements are currently in the stack
     */
    @Override
    public int size(){
        return this.stackSize;
    }
}
