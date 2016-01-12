import java.util.*;

// A concrete list-based MiniMap which is optimized for fast get(key)
// operations. The key type must be either Comparable or a Comparator
// must be provided when creating instances. The key list is kept in
// sorted order (via comparable/comparator). Binary search is used on
// the key list to locate the index of the (key,value) pair.
public class FastGetListMM<K,V> extends AbstractListMM<K,V>{

  // Comparator used to sort elements; may be null if elements are
  // Comparable
  public final Comparator<K> cmp;    

  // Assume elements must be comparable
  public FastGetListMM(){
    // Calls the super class to initialize fields
    super();
    // Assigns the cmp to null incase one is not given 
    this.cmp = null;
  }

  // Use the given comparator to sort the keys
  public FastGetListMM(Comparator<K> cmp){
    // Will update cmp when a comparator is given 
    this.cmp = cmp;
  }

  // Ensure that the FastGetListMM is set up properly to work with the
  // given key; either
  //
  // 1. key must be an instance of Comparable OR
  // 2. The comparator field cmp must not be null.
  //
  // If both are false, throw a RuntimeException.
  public void ensureComparable(K key){
    // Checks if the key is not an instance of comparable and cmp has not been changed which means
    // there is not one given
    if((key instanceof Comparable == false) && cmp == null){
      // If so throw RunTimeException
      throw new RuntimeException();
    }
  }
  

  // Use binary search to locate where the given key is located.  If
  // not present, return a negative number.  Check to make sure either
  // a comparator was used to initialize this instance of
  // FastGetListMM or that the keys are Comparable. During binary
  // search, use the comparator if present and otherwise rely on the
  // keys being comparable.
  // 
  // TARGET COMPLEXITY: O(log N)
  // N: Number of key/value associations
  @SuppressWarnings("unchecked")
  public int indexOf(K key){
    // Variable index starts at zero
    int index = 0;
    // Calls the method ensureComparable found above to ensure that 
    // the key is an instance of comparable and that if given a cmp it is not null
    // If both are false it will end and throw a RuntimeException
    ensureComparable(key);
    
    // If the comparator is null then there was no cmp given and it is a simple 
    // type
    if(cmp == null){
     // Casting given to us in the specifications
     // Creates a list to be searched called temp1
     List<Comparable<K>> temp1 = (List<Comparable<K>>) super.keys;
     // Updates the int value of index to the binary search using the list temp1
     // in the parameter as well as the key given 
     // public static <T> int binarySearch(List<? extends Comparable<? super T>> list,T key)
     index = Collections.binarySearch(temp1, key);
     }
    else{
      // If it is not a simple type then use binary search with the list to be searched, the key to be searched for
      // and the comparator by which the list is ordered
      index = Collections.binarySearch(super.keys, key, cmp);
    }
    // Return index
    return index;
  }


  // Locate the given key and replace its binding with the given
  // value. If not present, add the key in sorted order into the list
  // of keys and add the value at the same location in the list of
  // values.  Binary search should be used to quickly locate where the
  // key should be and replace an existing association.  If the key is
  // not present, list elements will need to be shifted so this method
  // will take longer.
  // 
  // TARGET COMPLEXITY:
  //   O(log N) if key is already present (use binary search)
  //   O(N)     otherwise as a key may need to be inserted and elements shifted
  // N: Number of key/value associations
  public V put(K key, V value){
    // Calls the method indexOf sending in the key given to find its index
    // Where it is or where it belongs
    int temp = indexOf(key);
    // If the temp is greater then zero or equal then that is its index
    if(temp >= 0){
      // Get the value at the index of the key known as temp, assign to temp2
      V temp2 = super.vals.get(temp);
      // Update the value to 
      super.vals.set(temp, value);
      // Return the old value
      return temp2;
    }
    // If it is negative then it is not already existent and then it is negative and where it belongs
    else{
      // Docs.oracle.com - (-(insertion point) - 1)
      int newIndex = temp * -1;
      super.keys.add(newIndex-1, key);
      super.vals.add(newIndex-1, value);
      // Return null because no prior value
      return null;
    }
  }

}