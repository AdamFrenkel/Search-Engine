package edu.yu.cs.com1320.project.impl;


 //no clue if this is accurate
import edu.yu.cs.com1320.project.HashTable;
import edu.yu.cs.com1320.project.stage1.impl.DocumentImpl;

 //no clue if this is accurate

public class HashTableImpl<Key, Value> implements HashTable<Key, Value> {


    private class objAndNextObj {
        Value value;
        Key key;
        objAndNextObj nextObj = null;

        private objAndNextObj(Key key, Value value){
            this.value = value;
            this.key = key;
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
            return nextObj;
        }
    }


    private objAndNextObj[] array = new objAndNextObj[5];

    /**
     * @param k the key whose value should be returned
     * @return the value that is stored in the HashTable for k, or null if there is no such key in the table
     */
    public Value get(Key k){
        int i  = ;//Do some stuff to find out where key goes into array
        return array[i];
    }

    /**
     * @param k the key at which to store the value
     * @param v the value to store
     * @return if the key was already present in the HashTable, return the previous value stored for the key. If the key was not already present, return null.
     */
    public Value put(Key k, Value v){
        /**
         * My own note explaining what this method does:
         * The array that is the base of this table should contain lists.
         * In this method we are creating the members of the list and placing them in their appropriate spots.
         */
        objAndNextObj newObj = new objAndNextObj(k,v);
        int i  = ;//Do some stuff to find out where key goes into array
        if(array[i] = null) {
            array[i] = newObj;
            return null;
        }else{
            objAndNextObj currentObj = array[i];
            boolean placedNewObj = false;
            while(!placedNewObj) {
                if (currentObj.getNextObj() == null) {
                    currentObj.addNextObj(newObj);
                    placedNewObj = true;
                    return (Value) array[i];  //not really sure what a value is
                }else{
                    currentObj = currentObj.getNextObj();
                }
            }
        }
    }


}

