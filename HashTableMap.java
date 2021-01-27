// --== CS400 File Header Information ==--
// Name: Deming Xu
// Email: dxu227@wisc.edu
// Team: CG
// Role: Back End Developer 1
// TA: Yeping Wang
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class is a implementation of back_end.MapADT interface, which acts as a Hash Table
 *
 * @author Deming Xu
 */
public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

    private LinkedList<HashNode>[] hashTable;   // hashTable Array, holding key-value pairs
    private int size;                           // The number of elements in the hashTable
    private int capacity;                       // The maximum number of elements that can added to the hashTable

    /**
     * This class is hash node, stored in the hash table. Each node contains a pair of key and value.
     *
     * @author Deming Xu
     */
    private class HashNode {
        private KeyType key;                    // Key of the hash node
        private ValueType value;                // value of the hash node

        /**
         * HashNode default constructor
         */
        private HashNode() {
            key = null;
            value = null;
        }

        /**
         * HashNode constructor with key and value
         *
         * @param key
         * @param value
         */
        private HashNode(KeyType key, ValueType value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * back_end.HashTableMap default constructor
     */
    @SuppressWarnings("unchecked")
    public HashTableMap() {
        this.hashTable = (LinkedList<HashNode>[]) new LinkedList[10];
        this.size = 0;
        this.capacity = 10;
    }

    /**
     * back_end.HashTableMap constructor with capacity
     *
     * @param capacity
     */
    @SuppressWarnings("unchecked")
    public HashTableMap(int capacity) {
        this.hashTable = (LinkedList<HashNode>[]) new LinkedList[capacity];
        this.size = 0;
        this.capacity = capacity;
    }

    /**
     * Calculate the hashCode with input of key.
     *
     * @param key
     * @return hash code of the key
     */
    private int hashFunction(KeyType key) {
        // use hashCode() method to get the hashCode and take the absolute value % capacity
        return Math.abs(key.hashCode()) % capacity;
    }

    /**
     * Double the size of hash table when the load factor is greater or equal to 80%,
     * and rehash every elements
     */
    @SuppressWarnings("unchecked")
    private void expand() {
        // if the capacity is 0, expand the table by adding 1 to capacity
        if (capacity == 0) {
            capacity = 1;
            hashTable = new LinkedList[capacity];  // New hash table with capacity of 1
            return;
        }

        int originalCapacity = capacity;                // Capacity before expanding
        capacity *= 2;
        LinkedList<HashNode>[] newList = new LinkedList[capacity];  // New hash table with double capacity

        // iterate every element in the old hash table and rehash them back into the new hash table
        for (int i = 0; i < originalCapacity; i++) {
            // if the linked list in each node is not null, rehash all elements in it
            if (hashTable[i] != null) {
                for (HashNode node : hashTable[i]) {
                    KeyType tempKey = node.key;         // The node in the linked list
                    int newIndex = hashFunction(tempKey);   // index after rehashing the key
                    // If the new index's linked list is null, create one and add the node.
                    // Otherwise, directly add the node
                    if (newList[newIndex] == null) {
                        newList[newIndex] = new LinkedList<>();
                    }
                    newList[newIndex].add(new HashNode(node.key, node.value));
                }
            }
        }
        // assign the reference to the new hash table
        hashTable = newList;
    }

    /**
     * Put a key-value pair into the hashTable.
     *
     * @param key
     * @param value
     * @return If the pair successfully put in the table, return true.
     * If the key has already been exist, return false
     */
    @Override
    public boolean put(KeyType key, ValueType value) {
        // if the table's capacity is 0, expand the table first
        if (capacity == 0) {
            expand();
        }

        int index = hashFunction(key);                  // the index of the pair in the hashTable

        // If the linked list is null, initialize one. Otherwise, check whether the key has been exist
        if (hashTable[index] == null) {
            hashTable[index] = new LinkedList<>();
        } else {
            for (HashNode node : hashTable[index]) {
                if (node.key.equals(key)) {
                    return false;
                }
            }
        }

        // Put the pair, and check whether expand() is needed
        hashTable[index].add(new HashNode(key, value));
        size++;
        if ((double) size / capacity >= 0.8) {
            expand();
        }
        return true;
    }

    /**
     * get the value corresponding to the input of key
     *
     * @param key return If the key is found, return the value corresponding to the key.
     *            If the key is not found, throw an exception
     */
    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {
        // if capacity is 0, throw an exception
        if (capacity == 0) {
            throw new NoSuchElementException("No Match Found");
        }

        int index = hashFunction(key);                 // the index of the pair in the hashTable

        // If the linked list at the index is null, target is not exist, and throw an exception.
        if (hashTable[index] == null) {
            throw new NoSuchElementException("No Match Found");
        }

        // Find the target in the linked list. If found, return the value, if not found, throw an exception.
        for (HashNode node : hashTable[index]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        throw new NoSuchElementException("No Match Found");
    }

    /**
     * Get the size of the hash table
     *
     * @return size of the hash table
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * check if the key is contained in the hash table
     *
     * @param key
     * @return true if key contains. false is key's not exist.
     */
    @Override
    public boolean containsKey(KeyType key) {
        // if capacity is 0, return false
        if (capacity == 0) {
            return false;
        }

        int index = hashFunction(key);                  // the index of the pair in the hashTable

        // If the linked list at the index is null, target is not exist
        if (hashTable[index] == null) {
            return false;
        }

        // Find the key in the linked list. If found, return true. Otherwise return false.
        for (HashNode node : hashTable[index]) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove the pair with input of key
     *
     * @param key
     * @return the value corresponding to the removed key. Null if not found
     */
    @Override
    public ValueType remove(KeyType key) {
        // if capacity is 0, return null
        if (capacity == 0) {
            return null;
        }

        int index = hashFunction(key);                  // the index of the pair in the hashTable

        // If the linked list at the index is null, target is not exist, return null
        if (hashTable[index] == null) {
            return null;
        }

        int i = 0;                                      // index tracker
        boolean isFound = false;                        // record whether the key is found
        // find the key and record the index of the key (i)
        for (HashNode node : hashTable[index]) {
            if (node.key.equals(key)) {
                isFound = true;
                break;
            }
            i++;
        }

        // if the key is found, remove with index of i. Otherwise return null
        if (isFound) {
            size--;
            return hashTable[index].remove(i).value;
        } else {
            return null;
        }
    }

    /**
     * clear the hashTable, but keep the capacity
     */
    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        size = 0;
        hashTable = new LinkedList[capacity];
    }

    /**
     * get the capacity of the hashTable
     *
     * @return capacity of the hashtable
     */
    public int getCapacity() {
        return this.capacity;
    }
}
