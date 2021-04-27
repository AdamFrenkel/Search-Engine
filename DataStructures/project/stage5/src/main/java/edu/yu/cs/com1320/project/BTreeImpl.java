package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.BTree;
import edu.yu.cs.com1320.project.stage5.PersistenceManager;

import java.util.ArrayList;
import java.util.Arrays;

/******************************************************************************
 * Limitations: Assumes MAX is even and MAX >= 4
 * NOTE: Edited by Judah Diament to increase readability (renamed variables, added comments, formatting, etc.). Also added the capabilities for getOrderedEntries, getMaxEntry, getMinEntry
 * original java file: http://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/BTree.java
 *
 ******************************************************************************/

/**
 * The {@code BTree} class represents an ordered symbol table of generic
 * key-value pairs. It supports the <em>put</em>, <em>get</em>,
 * <em>contains</em>, <em>size</em>, and <em>is-empty</em> methods. A symbol
 * table implements the <em>associative array</em> abstraction: when associating
 * a value with a key that is already in the symbol table, the convention is to
 * replace the old value with the new value. Unlike {@link java.util.Map}, this
 * class uses the convention that values cannot be {@code null}—setting the
 * value associated with a key to {@code null} is equivalent to deleting the key
 * from the symbol table.
 * <p>
 * This implementation uses a B-tree. It requires that the key type implements
 * the {@code Comparable} interface and calls the {@code compareTo()} and method
 * to compare two keys. It does not call either {@code equals()} or
 * {@code hashCode()}. The <em>get</em>, <em>put</em>, and <em>contains</em>
 * operations each make log<sub><em>m</em></sub>(<em>n</em>) probes in the worst
 * case, where <em>n</em> is the number of key-value pairs and <em>m</em> is the
 * branching factor. The <em>size</em>, and <em>is-empty</em> operations take
 * constant time. Construction takes constant time.
 * <p>
 * For additional documentation, see
 * <a href="http://algs4.cs.princeton.edu/62btree">Section 6.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */

public class BTreeImpl<Key extends Comparable<Key>, Value> implements BTree<Key, Value> {
    @Override
    public Value get(Key k) {
        return null;
    }

    @Override
    public Value put(Key k, Value v) {
        return null;
    }

    @Override
    public void moveToDisk(Key k) throws Exception {

    }

    @Override
    public void setPersistenceManager(PersistenceManager<Key, Value> pm) {

    }
}
