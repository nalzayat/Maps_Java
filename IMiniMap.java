import java.util.List;

interface IMiniMap<K,V>{
   // Return how many key/value bindings are in the map
   public int size();

   // Returns true if there are no key/val bindings present and false
   // otherwise
   public boolean isEmpty();

   // Returns true if the map cotains a value associated with the given
   // key.
   public boolean contains(K key);

   // Associate the given key with the given value in the map. If there
   // is no value already associated with the key, return null.  If
   // there is already a value associated with the given key, replace
   // it with the new value and return the old value.
   public V put(K key, V value);

   // Return the value associated with the given key. If there is no
   // association, return null.
   public V get(K key);

   // Remove association of given key from the map. If a value is
   // associated with the key, return it. Otherwise return null.
   public V remove(K key);

   // Return a list of the keys in the map. The list returned is
   // completely distinct from the map so any changes in one will not
   // affect the other.
   public List<K> keys();

   // Return a list of the values in the map. The list returned is
   // completely distinct from the map so any changes in one will not
   // affect the other.
   public List<V> values();
}
