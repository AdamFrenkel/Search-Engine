package edu.yu.cs.com1320.project.impl;

import static org.junit.jupiter.api.Assertions.*;


import edu.yu.cs.com1320.project.HashTable;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.stage1.Document;
//import org.junit.jupiter.api.Test;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class HashTableImplTest {
    @Test
    public void hashTableImplSimplePutAndGet() {
        HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
        hashTable.put(1,2);
        hashTable.put(3,6);
        hashTable.put(7,14);
        int x = hashTable.get(1);
        int y = hashTable.get(3);
        int z = hashTable.get(7);
        assertEquals(2, x);
        assertEquals(6, y);
        assertEquals(14, z);



    }


}