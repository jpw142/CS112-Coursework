/*
 * ChainedHashTable.java
 *
 * Computer Science 112, Boston University
 * 
 * Modifications and additions by:
 *     name: Jack Weber
 *     email: jrw142@bu.edu
 */

import java.util.*;     // to allow for the use of Arrays.toString() in testing

/*
 * A class that implements a hash table using separate chaining.
 */
public class ChainedHashTable implements HashTable {
    /* 
     * Private inner class for a node in a linked list
     * for a given position of the hash table
     */
    private class Node {
        private Object key;
        private LLQueue<Object> values;
        private Node next;
        
        private Node(Object key, Object value) {
            this.key = key;
            values = new LLQueue<Object>();
            values.insert(value);
            next = null;
        }
    }
    
    private Node[] table;      // the hash table itself
    private int numKeys;       // the total number of keys in the table
        
    /* hash function */
    public int h1(Object key) {
        int h1 = key.hashCode() % table.length;
        if (h1 < 0) {
            h1 += table.length;
        }
        return h1;
    }
    
    /*** Add your constructor here ***/
    public ChainedHashTable(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException();
        }
        table = new Node[size];
        numKeys = 0;
    }
    
    
    /*
     * insert - insert the specified (key, value) pair in the hash table.
     * Returns true if the pair can be added and false if there is overflow.
     */
    public boolean insert(Object key, Object value) {
        /** Replace the following line with your implementation. **/
        if (key == null) {
            throw new IllegalArgumentException("key must be non-null");
        }
        int i = h1(key);

        // Find where I can add a link
        // If theres no chain
        if (table[i] == null) {
            table[i] = new Node(key, value);
        }
        else {
            Node trav = table[i];
            while (trav.next != null) {
                // If the key is already in there
                if (key == trav) {
                    trav.values.insert(value);
                    return true;
                }
                trav = trav.next;
            }
            trav.next = new Node(key, value);
        }

        numKeys += 1;
        return true;
    }
    
    /*
     * search - search for the specified key and return the
     * associated collection of values, or null if the key 
     * is not in the table
     */
    public Queue<Object> search(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key must be non-null");
        }
        int i = h1(key);

        Node trav = table[i];
        do {
            if (trav.key == key) {
                return trav.values;
            }
            trav = trav.next;
        } while (trav != null);
        return null;
    }
    
    /* 
     * remove - remove from the table the entry for the specified key
     * and return the associated collection of values, or null if the key 
     * is not in the table
     */
    public Queue<Object> remove(Object key) {
        /** Replace the following line with your implementation. **/
        if (key == null) {
            throw new IllegalArgumentException("key must be non-null");
        }
        int i = h1(key);
        Node parent = null;
        Node trav = table[i];
        // Special case if it hits the first step of the chain
        if (trav.key == key) {
            table[i] = trav.next;
            numKeys -= 1;
            return trav.values;
        }
        // Go through array and try to find it
        do {
            if (trav.key == key) {
                // If we find it just skip over it
                parent.next = trav.next;
                numKeys -= 1;
                return trav.values;
            }
            parent = trav;
            trav = trav.next;
        } while (trav != null);
        return null;
    }
    
    
    /*** Add the other required methods here ***/
    /// Returns number of keys
    public int getNumKeys() {
        return numKeys;
    }

    /// Returns load % for the table
    public double load() {
        return numKeys/(double)table.length;
    }
    
    /// Returns an array with all the keys
    public Object[] getAllKeys() {
        // Accumulating array
        Object[] array = new Object[numKeys];
        int arrIndex = 0;
        // Loop through each entry in the table
        for (int i = 0; i < table.length; i++) {
            Node trav = table[i];
            // Go down the chain and add all the objects
            while (trav != null) {
                array[arrIndex] = trav.key;
                arrIndex += 1;
                trav = trav.next;
            }
        }
        return array;
    }


    /// Resizes the table and rehashes all the keys
    public void resize(int size) {
        if (size < table.length) {
            throw new IllegalArgumentException("key must be non-null");
        }
        if (size == table.length) {
            return;
        }
        Node[] oldTable = table;
        // Reset the table to the new table
        numKeys = 0;
        table = new Node[size];
        for (int j = 0; j < oldTable.length; j++) {
            Node trav = oldTable[j];
            // Go down the chain and add all the objects
            while (trav != null) {
                int i = h1(trav.key);

                // Essentially is just inserting but instead of 1 value, all of the values
                if (table[i] == null) {
                    table[i] = new Node(trav.key, 0);
                    table[i].values = trav.values;
                }
                else {
                    Node trail = table[i];
                    while (trail.next != null) {
                        trail = trail.next;
                    }
                    trail.next = new Node(trav.key, 0);
                    trail.next.values = trav.values;
                }

                numKeys += 1;
                trav = trav.next;
            }
        }
    }
    
    
    
    /*
     * toString - returns a string representation of this ChainedHashTable
     * object. *** You should NOT change this method. ***
     */
    public String toString() {
        String s = "[";
        
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                s += "null";
            } else {
                String keys = "{";
                Node trav = table[i];
                while (trav != null) {
                    keys += trav.key;
                    if (trav.next != null) {
                        keys += "; ";
                    }
                    trav = trav.next;
                }
                keys += "}";
                s += keys;
            }
        
            if (i < table.length - 1) {
                s += ", ";
            }
        }       
        
        s += "]";
        return s;
    }

    public static void main(String[] args) {
        /** Add your unit tests here **/
        ChainedHashTable table = new ChainedHashTable(5);
        table.insert("howdy", 15);
        table.insert("goodbye", 10);
        System.out.println(table.insert("apple", 5));
        System.out.println(table.getNumKeys());
        System.out.println(table);
        System.out.println(table.search("apple"));
        System.out.println(Arrays.toString(table.getAllKeys()));
        System.out.println(table.load());
        System.out.println(table.remove("apple"));
        System.out.println(table.getNumKeys());
        System.out.println(Arrays.toString(table.getAllKeys()));
        System.out.println(table.load());
        table.insert("apple", 5);
        table.resize(7);
        System.out.println(table);
    }
}
