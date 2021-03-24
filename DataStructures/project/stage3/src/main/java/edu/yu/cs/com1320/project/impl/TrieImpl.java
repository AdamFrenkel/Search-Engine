package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.Trie;

import java.util.*;

public class TrieImpl<Value> implements Trie<Value> {

    private Node root; // root of trie
    private static final int alphabetSize = 256; // extended ASCII
    public TrieImpl() {
        root = new Node();
    }


    private class Node<Value> {
        Set<Value> val = new HashSet<>();
        TrieImpl.Node[] links = new TrieImpl.Node[alphabetSize];
        private Set<Value> getVal(){
            return val;
        }
        int amtOfVals = 0;
    }


    /**
     * add the given value at the given key
     * @param key
     * @param val
     */
    public void put(String key, Value val){
        if( key == null){
            throw new IllegalArgumentException("null in trie");
        }
        //Making lowercase so that the whole tree can be case insensitive
        String lowerCaseKey = key.toLowerCase();
        if (val != null) {
            this.root = put(this.root, lowerCaseKey, val, 0);
        }
    }

    /**
     *
     * @param x
     * @param key
     * @param val
     * @param d
     * @return
     */
    private Node put(Node x, String key, Value val, int d)
    {
        key = key.toLowerCase();
        //create a new node
        if (x == null) {
            x = new Node<Value>();
        }

        //we've reached the last node in the key,
        //set the value for the key and return the node
        if (d == key.length()) {
            x.amtOfVals++;
            x.val.add(val);
            return x;
        }

        //proceed to the next node in the chain of nodes that
        //forms the desired key
        char c = key.charAt(d);
        x.links[c] = this.put(x.links[c], key, val, d + 1);
        return x;
    }

    /**
     * get all exact matches for the given key, sorted in descending order.
     * Search is CASE INSENSITIVE.
     * @param key
     * @param comparator used to sort  values
     * @return a List of matching Values, in descending order
     */
    public List<Value> getAllSorted(String key, Comparator<Value> comparator){
        if( key == null || comparator == null){
            throw new IllegalArgumentException("null in trie");
        }
        key = key.toLowerCase();
        Node currentNode = root;
        for(int i = 0; i<key.length(); i++){
            if(currentNode.links[Character.toLowerCase(key.charAt(i))] == null){
                return new ArrayList<Value>();
            }
            currentNode = currentNode.links[Character.toLowerCase(key.charAt(i))];
        }
        if(currentNode.val.size() == 0){
            return new ArrayList<>();
        }
        List<Value> values = new ArrayList<>();
        values.addAll(currentNode.val);
        values.sort(comparator);
        return values;
    }

    /**
     * get all matches which contain a String with the given prefix, sorted in descending order.
     * For example, if the key is "Too", you would return any value that contains "Tool", "Too", "Tooth", "Toodle", etc.
     * Search is CASE INSENSITIVE.
     * @param prefix
     * @param comparator used to sort values
     * @return a List of all matching Values containing the given prefix, in descending order
     */
    public List<Value> getAllWithPrefixSorted(String prefix, Comparator<Value> comparator){
        if( prefix == null || comparator == null){
            throw new IllegalArgumentException("null in trie");
        }
        if (prefix == ""){
            return new ArrayList<>();
        }
        prefix = prefix.toLowerCase();
        Node currentNode = root;
        for(int i = 0; i<prefix.length(); i++){
            if(currentNode.links[Character.toLowerCase(prefix.charAt(i))] == null){
                return new ArrayList<>();
            }
            currentNode = currentNode.links[Character.toLowerCase(prefix.charAt(i))];
        }
        this.getAllWithPrefix(currentNode);
        List<Value> prefixList = new ArrayList<>();
        prefixList.addAll(prefixes);
        prefixList.sort(comparator);
        prefixes = new ArrayList<>();
        return prefixList;
    }

    private List<Value> prefixes = new ArrayList<>();
    private void getAllWithPrefix(Node x){
        for(int i = 0; i < x.links.length; i++){
            if(x.links[i] != null){
                getAllWithPrefix(x.links[i]);
            }
        }
        if(x.val != null){
            Set<Value> setofVals = x.val;
            for (Value v : setofVals){
                if(!prefixes.contains(v)){
                    prefixes.add(v);
                }
            }
        }
    }
    /**
     * Delete the subtree rooted at the last character of the prefix.
     * Search is CASE INSENSITIVE.
     * @param prefix
     * @return a Set of all Values that were deleted.
     */
    public Set<Value> deleteAllWithPrefix(String prefix){
        if( prefix == null){
            throw new IllegalArgumentException("null in trie");
        }
        if(prefix == ""){
            return new HashSet<>();
        }
        prefix = prefix.toLowerCase();
        Node currentNode = root;
        Node previousNode = null;
        for(int i = 0; i<prefix.length(); i++){
            if(currentNode.links[Character.toLowerCase(prefix.charAt(i))] == null){
                return new HashSet<>();
            }
            previousNode = currentNode;
            currentNode = currentNode.links[Character.toLowerCase(prefix.charAt(i))];
        }
        this.deleteAllWithPrefix(currentNode);
        previousNode.links[Character.toLowerCase(prefix.charAt(prefix.length()-1))] = null;
        Set<Value> returnSet = new HashSet<>();
        returnSet.addAll(deletedSet3);
        deletedSet3 = new HashSet<>();
        return returnSet;
    }
    private Set<Value> deletedSet3 = new HashSet<>();
    private void deleteAllWithPrefix(Node x){
        for(int i = 0; i < x.links.length; i++){
            if(x.links[i] != null){
                deleteAllWithPrefix(x.links[i]);
                x.links[i] = null;
            }
        }
        if(x.val != null){
            deletedSet3.addAll(x.val);
            x.val = new HashSet<Value>();
        }
    }

    /**
     * Delete all values from the node of the given key (do not remove the values from other nodes in the Trie)
     * @param key
     * @return a Set of all Values that were deleted.
     */
    public Set<Value> deleteAll(String key){
        if(key == null){
            throw new IllegalArgumentException("null in trie");
        }
        key = key.toLowerCase();
        this.root = deleteAll(this.root, key, 0); //I have no idea why we're setting the root = to this node
        Set<Value> set = new HashSet<>();
        set.addAll(deletedSet);
        deletedSet = new HashSet<>();
        return set;
    }
    private Set<Value> deletedSet = new HashSet<>();

    private Node deleteAll(Node x, String key, int d)
    {
        if (x == null) {
            return null;
        }
        //we're at the node to del - set the val to null
        if (d == key.length()) {
            deletedSet.addAll(x.getVal());
            x.amtOfVals = 0;
            x.val = new HashSet<Value>();
        }
        //continue down the trie to the target node
        else {
            char c = key.charAt(d);
            x.links[c] = this.deleteAll(x.links[c], key, d + 1);
        }
        //this node has a val – do nothing, return the node
        if (x.amtOfVals > 0) {
            return x;
        }
        //remove subtrie rooted at x if it is completely empty
        for (int c = 0; c <alphabetSize; c++)
        {
            if (x.links[c] != null)
            {
                return x; //not empty
            }
        }
        //empty - set this link to null in the parent
        return null;
    }

    /**
     * Remove the given value from the node of the given key (do not remove the value from other nodes in the Trie)
     * @param key
     * @param val
     * @return the value which was deleted. If the key did not contain the given value, return null.
     */
    public Value delete(String key, Value val){
        if(key == null || val == null){
            throw new IllegalArgumentException("null in trie");
        }
        key = key.toLowerCase();
        this.root = delete(this.root, key, 0, val);
        deletedSet2 = null;
        Value returnVal = deletedValue;
        deletedValue = null;
        return returnVal;
    }

    private Set<Value> deletedSet2 = new HashSet<>();
    private Value deletedValue = null;
    private Node delete(Node x, String key, int d, Value val){
        if (x == null) {
            return null;
        }
        //we're at the node to del - set the val to null
        if (d == key.length()) {
            deletedSet2 = x.getVal();
            boolean haventFoundVal = true;
            for(Value v : deletedSet2){
                if (v.equals(val)) {
                    x.amtOfVals--;
                    deletedValue = v;
                    x.val.remove(v);
                    haventFoundVal = false;
                    //v = null;
                }
                if(haventFoundVal == false) {
                    break;
                }
            }
        }
        //continue down the trie to the target node
        else {
            char c = key.charAt(d);
            x.links[c] = this.delete(x.links[c], key, d + 1,val); //was deleteAll
        }
        //this node has a val – do nothing, return the node
        if (x.val.size() >0) {
            return x;
        }
        //remove subtrie rooted at x if it is completely empty
        for (int c = 0; c <alphabetSize; c++)
        {
            if (x.links[c] != null)
            {
                return x; //not empty
            }
        }
        //empty - set this link to null in the parent
        return null;
    }

}
