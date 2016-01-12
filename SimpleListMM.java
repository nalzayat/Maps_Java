// A concrete list-based MiniMap which is not optimized for any fast
// operations. The internal key list is not kept in sorted order so
// indexOf() must scan using linear search.  put(key,value) may simply
// add new entries to the end of the lists so long as the give key is
// not already present.
public class SimpleListMM<K,V> extends AbstractListMM<K,V>{

  // No special parameters required
  public SimpleListMM(){
    super();
  }

  // Scan through the list of keys linearly searching for the given
  // key. If not present, return a negative number.
  //
  // TARGET COMPLEXITY: O(N)
  // N: Number of key/value associations
  public int indexOf(K key){
    for(int x = 0; x < keys.size(); x++){
      if(keys.get(x).equals(key)){
        return x;
      }
    }
    return -1;
  }

  // Locate the given key and replace its binding with the given
  // value. If not present, add the key and value onto the end of
  // their respective lists.
  // 
  // TARGET COMPLEXITY: O(N)
  // N: Number of key/value associations
  public V put(K key, V value){
    int index = indexOf(key);
    if(index == -1){
      super.keys.add(key);
      super.vals.add(value);
      return null;
    }
    else{
      V temp = super.vals.get(index);
      super.vals.set(index, value);
      return temp;
    }
  }

}