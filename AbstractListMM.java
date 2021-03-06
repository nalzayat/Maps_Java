import java.util.*;

// A partial list-based implementation of MiniMap. The class and all
// descendants keep parallel lists of keys/values and searches through
// them for IMiniMap operations. All obvious common methods are
// implemented here.  Child classes should override the put(key,val)
// and indexOf(key) methods to provide operations for target
// complexities.
public abstract class AbstractListMM<K,V> implements IMiniMap<K,V>{

  // Lists of keys and values
  // Array list of keys
  protected ArrayList<K> keys;
  // Array list of vals
  protected ArrayList<V> vals;

  // Initialize the lists of keys and values with a concrete instance
  public AbstractListMM(){
    this.keys = new ArrayList<K>();
    this.vals = new ArrayList<V>();
  }

  // Return the number of bindings based on the size of the key list
  public int size(){
    return this.keys.size();
  }

  // Based on the lists size
  public boolean isEmpty(){
    // If the size of the keys is zero then it is empty, return true
    if(this.keys.size() == 0){
      return true;
    }
    // Otherwise return false
    else{
      return false;
    }
  }

  // Make a (shallow) copy of the keys list and return it
  public List<K> keys(){
    // Array list of type K with variable name shallowKeys
    List<K> shallowKeys = new ArrayList<K>();
    // For loop through the size of the keys array
    for(int x = 0; x < keys.size(); x++){
      // For every index of keys add it to the shallowKeys array list copy
      shallowKeys.add(keys.get(x));
    }
    // Return shallow copy of the keys list 
    return shallowKeys;
  }

  // Make a (shallow) copy of the vals list and return it
  public List<V> values(){
    // Creates a list of type V called shallowValues
    List<V> shallowValues = new ArrayList<V>();
    // For loop through the size of the vals array list
    for(int x = 0; x < vals.size(); x++){
      // Add the value to the shallowValues list
      shallowValues.add(vals.get(x));
    }
    return shallowValues;
  }

  // Use this.indexOf() to locate the given key as quickly as possible
  public boolean contains(K key){
    if(this.keys.indexOf(key) >= 0){
      return true;
    }
    else{
      return false;
    }
  }

  // Use this.indexOf() to determine if a given key is present and
  // return its associated value; return null if the key is not
  // present
  //
  // TARGET COMPLEXITY: Same speed as indexOf()
  public V get(K key){
    boolean checkContain = contains(key);
    if(checkContain == false){
      return null;
    }
    else{
      int index = this.keys.indexOf(key);
      return this.vals.get(index);
    }
  }

  // Use this.indexOf() to determine the location of the given
  // key/value and remove it from the corresponding lists
  //
  // TARGET COMPLEXITY: O(N) due to list elements shifting
  public V remove(K key){
    if(keys.contains(key)){
       int index = this.keys.indexOf(key);
       V tempAns = this.vals.get(index);
       this.keys.remove(index);
       this.vals.remove(index);
       return tempAns;
    }
    else{
      return null;
    }
  }

  // Find the numeric index of the key as quickly as possible based on
  // the type of ListMM being implemented. Return a negative number if
  // the given key is not present.
  public abstract int indexOf(K key);

  // Associate the given key with the given value in the
  // lists. Creates an ordering of keys that is compatible with how
  // indexOf() works to locate keys.
  public abstract V put(K key, V value);

}