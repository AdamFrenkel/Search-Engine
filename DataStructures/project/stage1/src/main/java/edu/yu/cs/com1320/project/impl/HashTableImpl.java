package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.HashTable;

public class HashTableImpl<Key, Value> implements HashTable<Key, Value> {


    private class objAndNextObj {
        Value value;
        Key key;
        objAndNextObj nextObj = null;

        private objAndNextObj(Key key, Value value){
            this.key = key;
            this.value = value;
        }
        private Value getValue(){
            return this.value;
        }
        private Key getKey(){
            return this.key;
        }
        private void addNextObj(objAndNextObj nextObj){
            this.nextObj = nextObj;
        }
        private objAndNextObj getNextObj(){
            return this.nextObj;
        }
    }

    private Object[] array = new Object[5]; // BeH this works!


    /**
     * @param k the key whose value should be returned
     * @return the value that is stored in the HashTable for k, or null if there is no such key in the table
     */
    public Value get(Key k){
        int i = Math.abs(k.hashCode()) % 5; //This is the slot in the array in which the value might be stored
        if (array[i] == null) {
            return null;
        }
        if(((objAndNextObj)array[i]).getKey().equals(k)){
            return ((objAndNextObj)array[i]).getValue();
        }else{
            objAndNextObj currentObj = (objAndNextObj)array[i];
            while(currentObj.getNextObj() != null){
                currentObj = currentObj.getNextObj();
                if(currentObj.getKey().equals(k)){
                    return currentObj.getValue();
                }
            }
        }
        return null;
    }

    /**
     * @param k the key at which to store the value
     * @param v the value to store.
     * To delete an entry, put a null value.
     * @return if the key was already present in the HashTable, return the previous value stored for the key. If the key was not already present, return null.
     */
    public Value put(Key k, Value v){
        /**
         * My own note explaining what this method does:
         * The array that is the base of this table should contain lists.
         * In this method we are creating the members of the list and placing them in their appropriate spots.
         */
        //This first if statement is for when put is actually delete.
        if(v==null){
            return this.deleteKey(k);
        }
        //Put will either replace the current value stored at k, or if k is new it will add it to the end of the proper list.
        Value returnValue = this.get(k);
        if(returnValue != null){
            this.replaceKey(k,v);
        }else {
            this.addNewKey(k,v);
        }
        return returnValue;
    }

    /**
     * My own method to make the put method shorter.
     */
    private Value deleteKey(Key k){
        int i = Math.abs(k.hashCode()) % 5; //This is the slot in the array in which the value might be stored
        if (array[i] == null) {
            return null;
        }
        if(((objAndNextObj)array[i]).getKey().equals(k)){
            Value returnValue = ((objAndNextObj)array[i]).getValue();
            array[i] = ((objAndNextObj)array[i]).getNextObj();
            return returnValue;
        }else{
            objAndNextObj currentObj = (objAndNextObj)array[i];
            while(currentObj.getNextObj() != null){
                objAndNextObj previousObj = currentObj;
                currentObj = currentObj.getNextObj();
                if(currentObj.getKey().equals(k)){
                    previousObj.addNextObj(currentObj.getNextObj());
                    return currentObj.getValue();
                }
            }
        }
        return null;
    }

    /**
     * My own method to make the put method shorter.
     */
    private void addNewKey(Key k, Value v){
        objAndNextObj newObj = new objAndNextObj(k, v);
        int i = Math.abs(k.hashCode()) % 5; //This is the slot in the array in which to store value
        if (array[i] == null) {
            array[i] = newObj;
        } else {
            objAndNextObj currentObj = (objAndNextObj) array[i];
            boolean placedNewObj = false;
            while (!placedNewObj) {
                if (currentObj.getNextObj() == null) {
                    currentObj.addNextObj(newObj);
                    placedNewObj = true;
                } else {
                    currentObj = currentObj.getNextObj();
                }
            }
        }
    }

    /**
     * My own method to make the put method shorter.
     */
    private void replaceKey(Key k, Value v){
        objAndNextObj newObj = new objAndNextObj(k, v);
        int i = Math.abs(k.hashCode()) % 5; //This is the slot in the array in which the value is stored
        if(((objAndNextObj)array[i]).getKey().equals(k)){
            newObj.addNextObj(((objAndNextObj)array[i]).getNextObj());
            array[i] = newObj;
        }else{
            objAndNextObj currentObj = (objAndNextObj)array[i];
            while(currentObj.getNextObj() != null){
                objAndNextObj previousObj = currentObj;
                currentObj = currentObj.getNextObj();
                if(currentObj.getKey().equals(k)){
                    newObj.addNextObj(currentObj.getNextObj());
                    previousObj.addNextObj(newObj);
                }
            }
        }
    }


}

