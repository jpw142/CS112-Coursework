/*
 * Student First Name: Jack
 * Student Last Name: Weber
 * Student BU Number: U97268134
 * Purpose: A class that emulates the operations and structure of a set
 */

public class Set  {
    private static final int DEFAULT_SIZE = 10; // default size of initial set              
    private int[] set;      // array referece to the set
    private int next;       // index to next available slot in the set array
    
    /*
     * Constructors
     */
    //No argument constructor; initialize this set as an
    //instance of the empty set.
    public Set() {
        set = new int[DEFAULT_SIZE];
        next = 0;
    }

    // Initialize the set from the elements passed through
    // the array referenced by arr. Note that the array referenced by 
    // arr may contain duplicate values, but the array representing the 
    // set should not.
    // In addiion, the array passed to the constructor can be of an arbitrary length 
    // (i.e. it may be larger than DEFAUL_SIZE).
    public Set(int[] arr) {
        next = 0;
        int[] newArr = new int[arr.length];
        // Go through new array
        for (int i = 0; i < arr.length; i++) {
            boolean truth = true;
            // Check to make sure its not already in set
            for (int j = 0; j < next; j++) {
                if (newArr[j] == arr[i]) {
                    truth = false;
                    break;
                }   
            }
            // If its not already in the set then add it
            if (truth) {
                newArr[next] = arr[i];
                next += 1;
            }
        }
        set = newArr;
    }

    // Return an exact copy of this set
    public Set clone() {
        // Just creates a new set using inner array
        Set newSet = new Set();
        for (int i = 0; i < next; i++) {
            newSet.insert(set[i]);
        }
        return newSet;
    }

    // Return a string representation of the set,
    // e.g., [2,3,7,-3]  or [] for the empty set; 
    // you may not use `Array.toString` to do this, you must build the
    // resulting string.
    public String toString() {
        String str = "[";
        for (int i = 0; i < next; i++) {
            str = str + set[i];
            // If its not the last element
            if (i < (next - 1)) {
                str += ",";
            }
        }
        str += "]";
        return str;
    }  

    // Return the number of elements in this set. 
    public int cardinality() {
        // Next will always correspond to the index + 1 of last element, which is length
        return next;
    }

    // Return true if this is the empty set has
    // no members and false otherwise.
    public  boolean isEmpty() {
        if (next == 0) {
            return true;
        }
        return false;
    } 

    // Return true if n is in the set and false otherwise. 
    public boolean member(int n) {
        // Loops through the entire set
        if (this.cardinality() == 0) {
            return false;
        }
        for (int i = 0; i < next; i++) {
            // If there is a match return true
            if (set[i] == n) {
                return true;
            }
        }
        return false;
    } 

    // Returns true if this set is a subset of S,
    // that is, every member of this set is a member of S, and false
    // otherwise.
    public boolean subset(Set S) {
        // Checks to see if any member of this set is not member of S
        for (int i = 0; i < next; i++) {
            // If true then it's not a subset
            if (!S.member(set[i])) {
                return false;
            } 
        }
        // Everythings checked and is A OK
        return true;
    }

    // Returns true if this set and S have
    // exactly the same members.
    public boolean equal(Set S) {
        // Can't have exactly the same if not equal
        if (next != S.cardinality()) {
            return false;
        }
        // Guard statement determing every single element is equal
        for (int i = 0; i < next; i++) {
            if (!S.member(set[i])) {
                return false;
            }
        }
        // If they have the same length and every member has a match then they are the same
        return true;
    }

   
    // If the integer k is a member of the set
    // already, do nothing, otherwise, add to the set; if this would cause an
    // ArrayOutOfBoundsException, you must grow the array before inserting the new element.
    public void insert(int k) {
        // if it's a member do nothing
        if (this.member(k)) {
            return;
        }
        // If we don't have any more room grow the array
        if (next == set.length) {
            this.grow();
        }
        // Add the k and increase next by 1 
        set[next] = k;
        next += 1;
    }   

    //If the integer k is not a member, do nothing; 
    // else remove it from the set; you must shift all elements which occur
    // after k in the array to the left by one slot.
    public void delete(int k) {
        // if it's 0 then theres nothing to do
        if (!this.member(k)) {
            return;
        }
        // find index of k
        int index = 0;
        for (int i = 0; i < next; i++) {
            if (set[i] == k) {
               index = i;
                break;
            }
        }

        // Move elements after desired removed position backwards
        for (int i = index + 1; i < next; i++) {
            set[i - 1] = set[i];
        }
        // set last element to 0 because it is removed, sort of bubbles up
        set[next - 1] = 0;
        // removed one element
        next -= 1;
    }
  
  
    //resize the array by growing it by the DEFAULT_SIZE.
    public void grow() {
        int[] newArr = new int[set.length + DEFAULT_SIZE];
        // copy all values
        for (int i = 0; i < next; i++) {
            newArr[i] = set[i];
        }
        set = newArr;
    }

    //Return a new set consisting of all of the
    public Set union(Set S) {
        Set newSet = S.clone();
        // If it can be inserted without being duplicated then do it!
        for (int i = 0; i < next; i++) {
            newSet.insert(set[i]);
        }
        return newSet;
    }

    // Return a new set consisting of the elements
    // that are common to both this set and S (without duplicates). Example:
    public Set intersection(Set S) {
        Set newSet = new Set();
        for (int i = 0; i < next; i++) {
            // If Set S and Set this both have it then it's in the intersection
            if (S.member(set[i])) {
                newSet.insert(set[i]);
            }
        }
        return newSet;
    }

    //Return a new set consistng of all the 
    //elements of this set that are not present in S.
    public Set setdifference(Set S) {
        Set newSet = this.clone();
        // Delete everything overlapping with Set S
        for (int i = 0; i < next; i++) {
            if (S.member(set[i])) {
                newSet.delete(set[i]);
            }
        }
        return newSet;
    } 

    public static void main(String[] args) {
        System.out.println("\nUnit Test for Set: note that your answers, when they are");
        System.out.println("  sets, could be in a different order (since order does");
        System.out.println("  not matter), this is the meaning of \"same set as...\"\n");

        Set A = new Set();
        Set B = new Set( new int[] { 5 } );
        Set C = new Set( new int[] { 5, 3, 7, 4, 1 } );
        Set D = new Set( new int[] { 4, 3, -3, 10, 8 } );
        Set E = new Set( new int[] { 8, 4, 10 } );
        Set F = new Set( new int[] { 10, 8, 4 } );
        Set Z = new Set( new int[] {1, 2, 3, 4, 0} );

        System.out.println("Test 00: Should be\n[1,2,3,4,0]");
        System.out.println(Z);
        System.out.println(); 

        System.out.println("Test 01: Should be\n[]");
        System.out.println(A);
        System.out.println(); 

        System.out.println("Test 02: Should be\n[5]");
        System.out.println(B);
        System.out.println(); 

        System.out.println("Test 03: Should be same set as\n[5,3,7,4,1]");
        System.out.println(C);
        System.out.println(); 

        System.out.println("Test 04: Should be\n[]");
        System.out.println(A.clone());
    // elements of this set and S combined (without duplicates). Example:
        System.out.println(); 
        
        System.out.println("Test 05: Should be same set as\n[5,3,7,4,1]");
        System.out.println(C.clone());
        System.out.println(); 
        
        System.out.println("Test 06: Should be\n0");
        System.out.println(A.cardinality());
        System.out.println(); 
        
        System.out.println("Test 07: Should be\n5");
        System.out.println(D.cardinality());
        System.out.println(); 
        
        System.out.println("Test 08: Should be\ntrue");
        System.out.println(A.isEmpty());
        System.out.println(); 
        
        System.out.println("Test 09: Should be\nfalse");
        System.out.println(F.isEmpty());
        System.out.println(); 
        
        System.out.println("Test 10: Should be\nfalse");
        System.out.println(A.member(4));
        System.out.println();
        
        System.out.println("Test 11: Should be\ntrue");
        System.out.println(C.member(1));
        System.out.println();       
        
        System.out.println("Test 12: Should be\nfalse");
        System.out.println(D.member(1));
        System.out.println();
        
        System.out.println("Test 13: Should be\ntrue");
        System.out.println(A.subset(D));
        System.out.println();
        
        System.out.println("Test 14: Should be\nfalse");
        System.out.println(D.subset(C));
        System.out.println();       
        
        System.out.println("Test 15: Should be\ntrue");
        System.out.println(E.subset(D));
        System.out.println();
        
        System.out.println("Test 16: Should be\nfalse");
        System.out.println(D.subset(E));
        System.out.println();
        
        System.out.println("Test 17: Should be\nfalse");
        System.out.println(D.equal(E));
        System.out.println();       
        
        System.out.println("Test 18: Should be\ntrue");
        System.out.println(E.equal(F));
        System.out.println();
        
        System.out.println("Test 19: Should be\ntrue");
        System.out.println(F.equal(E));
        System.out.println();
        
        System.out.println("Test 20: Should be\ntrue");
        System.out.println(A.equal(A));
        System.out.println();       
        
        System.out.println("Test 21: Should be same set as\n[4,6]");
        Set A1 = A.clone(); 
        A1.insert(4);
        A1.insert(6);
        A1.insert(4);
        System.out.println(A1);
        System.out.println();
        
        System.out.println("Test 22: Should be same set as\n[10,8,4,5]");
        Set F1 = F.clone(); 
        F1.insert(5);
        F1.insert(4);
        System.out.println(F1);
        System.out.println();       
        
        System.out.println("Test 23: Should be same set as\n[8,4,10]");
        Set E1 = E.clone(); 
        E1.insert(10);
        System.out.println(E1);
        System.out.println();
        
        System.out.println("Test 24: Should be\n[]");
        A1 = A.clone(); 
        A1.delete(5);
        System.out.println(A1);
        System.out.println();       
        
        System.out.println("Test 25: Should be\n[]");
        Set B1 = B.clone(); 
        B1.delete(5);
        System.out.println(B1);
        System.out.println();  
        
        System.out.println("Test 26: Should be same set as\n[8,4,10]");
        E1 = E.clone(); 
        E1.delete(5);
        System.out.println(E1);
        System.out.println(); 
        
        System.out.println("Test 27: Should be same set as\n[4,10]");
        E1 = E.clone(); 
        E1.delete(8);
        System.out.println(E1);
        System.out.println();
        
        System.out.println("Test 28: Should be same set as\n[3,4]");
        System.out.println(C.intersection(D));
        System.out.println();
        
        System.out.println("Test 29: Should be\n[8,4,10]");
        System.out.println(E.intersection(F));
        System.out.println();       
        
        System.out.println("Test 30: Should be same set as\n[]");
        System.out.println(A.intersection(F));
        System.out.println();
        
        System.out.println("Test 31: Should be same set as\n[]");
        System.out.println(B.intersection(F));
        System.out.println();
        
        System.out.println("Test 32: Should be same set as\n[4,3,-3,10,8,5,7,1]");
        System.out.println(C.union(D));
        System.out.println();
        
        System.out.println("Test 33: Should be same set as\n[10,8,4]");
        System.out.println(E.union(F));
        System.out.println();       
        
        System.out.println("Test 34: Should be same set as\n[10,8,4]");
        System.out.println(A.union(F));
        System.out.println();
        
        System.out.println("Test 35: Should be same set as\n[5,3,7,4,1]");
        System.out.println(C.union(B));
        System.out.println();
        
        System.out.println("Test 36: Should be same set as\n[5,7,1]");
        System.out.println(C.setdifference(D));
        System.out.println();       
        
        System.out.println("Test 37: Should be same set as\n[]");
        System.out.println(E.setdifference(F));
        System.out.println();
        
        System.out.println("Test 38: Should be same set as\n[5,3,7,4,1]");
        System.out.println(C.setdifference(A));
        System.out.println();
        
        System.out.println("Test 39: Should be same set as\n[]");
        System.out.println(C.setdifference(C));
        System.out.println();
        
        System.out.println("Test 40: Should be same set as\n[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31]");
        Set G = new Set();
        for(int i = 0; i < 32; ++i) {
            G.insert(i);
        }
        System.out.println(G);
        System.out.println();
    }
}
