// CHANGELOG: 
// 
// Thu Apr 30 10:55:34 EDT 2015 : Minor bug fix in ensureComparable()
// tests.

/** Example of using unit tests for programming assignment 7.  This is
  * partially how your code will be graded.  Later in the class we will
  * write our own unit tests.  To run them on the command line, make
  * sure that the junit-cs211.jar is in the project directory.
  * 
  *  demo$ javac -cp .:junit-cs211.jar *.java     # compile everything
  *  demo$ java  -cp .:junit-cs211.jar P7Tests    # run tests
  * 
  * On windows replace : with ; (colon with semicolon)
  *  demo$ javac -cp .;junit-cs211.jar *.java     # compile everything
  *  demo$ java  -cp .;junit-cs211.jar P7Tests    # run tests
  */

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.io.*;

@SuppressWarnings("unchecked")
public class P7Tests {
  public static void main(String args[]){
    org.junit.runner.JUnitCore.main("P7Tests");
  }
  
  
  // Pair class
  class Pair<K,V>{
    K k; V v;
    public Pair(K kk, V vv){
      k=kk; v=vv;
    }
  }
  
  // Comprator that reverses normal ordering of ints
  class RevComp implements Comparator<Integer>{
    public int compare(Integer x, Integer y){
      return y-x;
    }
  }
  // A non-comparable type
  class Coord {
    int row,col;
    public Coord(int r, int c){
      this.row=r; this.col=c;
    }
    public boolean equals(Object o){
      if(!(o instanceof Coord)){ return false; }
      Coord c = (Coord) o;
      return this.row==c.row && this.col==c.col;
    }
    public String toString(){
      return String.format("(%s,%s)",row,col);
    }
  }
  // Comprator that sorts Coords by their magnitude
  class MagnitudeComp implements Comparator<Coord>{
    public int compare(Coord x, Coord y){
      int xmag = Math.abs(x.row)+Math.abs(x.col);
      int ymag = Math.abs(y.row)+Math.abs(y.col);
      return xmag-ymag;
    }
  }
  
  @SuppressWarnings("unchecked")
  public static <T> ArrayList<T> al(T... stuff){
    return new ArrayList<T>(Arrays.asList(stuff));
  }
  
  @Test(timeout=1000) public void simple_constructor(){
    IMiniMap<String,Integer> map = new SimpleListMM<String,Integer>();
    IMiniMap<Double,ArrayList<Object>> map2 = new SimpleListMM<Double,ArrayList<Object>>();
  }
  @Test(timeout=1000) public void fastget_constructor1(){
    IMiniMap<String,Integer> map = new FastGetListMM<String,Integer>();
    IMiniMap<Double,ArrayList<Object>> map2 = new FastGetListMM<Double,ArrayList<Object>>();
  }
  @Test(timeout=1000) public void fastget_constructor2(){
    IMiniMap<String,Integer> map  = new FastGetListMM<String,Integer>(String.CASE_INSENSITIVE_ORDER);
    IMiniMap<Integer,Double> map2 = new FastGetListMM<Integer,Double>(new RevComp());
  }
  
  @Test(timeout=1000) public void simple_puts_keys_vals_order1(){
    IMiniMap<String,Integer> map = new SimpleListMM<String,Integer>();
    map.put("Mario",1);
    map.put("Luigi",2);
    map.put("Peach",3);
    map.put("Toad", 4);
    assertEquals(al("Mario","Luigi","Peach","Toad"),map.keys());
    assertEquals(al(1,2,3,4), map.values());
  }
  
  @Test(timeout=1000) public void fastget_puts_keys_vals_order1(){
    IMiniMap<String,Integer> map = new FastGetListMM<String,Integer>();
    map.put("Mario",1);
    map.put("Luigi",2);
    map.put("Peach",3);
    map.put("Toad", 4);
    assertEquals(al("Luigi","Mario","Peach","Toad"),map.keys());
    assertEquals(al(2,1,3,4), map.values());
  }
  
  
  @Test(timeout=1000) public void simple_puts1(){
    IMiniMap<String,Integer> map = new SimpleListMM<String,Integer>();
    puts1(map);
  }
  @Test(timeout=1000) public void fastget_puts1(){
    IMiniMap<String,Integer> map = new FastGetListMM<String,Integer>();
    puts1(map);
  }
  public void puts1(IMiniMap<String,Integer> map){
    map.put("Mario",1);
    map.put("Luigi",2);
    map.put("Peach",3);
    map.put("Toad", 4);
    
    assertEquals(4, map.size());
    
    assertTrue(map.contains("Toad" ));
    assertTrue(map.contains("Luigi"));
    assertTrue(map.contains("Peach"));
    assertTrue(map.contains("Mario"));
    
    assertEquals(new Integer(3),map.get("Peach"));
    assertEquals(new Integer(1),map.get("Mario"));
    assertEquals(new Integer(4),map.get("Toad" ));
    assertEquals(new Integer(2),map.get("Luigi"));
  }
  
  @Test(timeout=1000) public void simple_put_remove1(){
    IMiniMap<String,Integer> map = new SimpleListMM<String,Integer>();
    put_remove1(map);
  }
  @Test(timeout=1000) public void fastget_put_remove1(){
    IMiniMap<String,Integer> map = new FastGetListMM<String,Integer>();
    put_remove1(map);
  }
  public void put_remove1(IMiniMap<String,Integer> map){
    map.put("Mario",1);
    map.put("Luigi",2);
    map.put("Peach",3);
    map.put("Toad", 4);
    
    Integer ret = map.remove("Peach");
    
    assertEquals(new Integer(3),ret);
    assertEquals(false, map.contains("Peach"));
    assertEquals(null, map.get("Peach"));
    
    assertTrue(map.contains("Toad" ));
    assertTrue(map.contains("Luigi"));
    assertTrue(map.contains("Mario"));
    
    assertEquals(new Integer(1),map.get("Mario"));
    assertEquals(new Integer(2),map.get("Luigi"));
    assertEquals(new Integer(4),map.get("Toad" ));
  }
  
  @Test(timeout=1000) public void simple_puts_keys_vals_order2(){
    IMiniMap<Integer,Double> map = new SimpleListMM<Integer,Double>();
    int [] ia   = { 8,  6,  7,  5,  3,  0,  9,-86,-75,-30,-90};
    double[] da = {.6,.07,.50,.33,.00,.90,.86,.75,.30,.90,.08};
    for(int i=0; i<ia.length; i++){
      assertEquals(null, map.put(ia[i], da[i]));
    }
    assertEquals(al( 8,  6,  7,  5,  3,  0,  9,-86,-75,-30,-90), map.keys());
    assertEquals(al(.6,.07,.50,.33,.00,.90,.86,.75,.30,.90,.08), map.values());
  }
  
  @Test(timeout=1000) public void fastget_puts_keys_vals_order2(){
    IMiniMap<Integer,Double> map = new FastGetListMM<Integer,Double>();
    int [] ia   = { 8,  6,  7,  5,  3,  0,  9,-86,-75,-30,-90};
    double[] da = {.6,.07,.50,.33,.00,.90,.86,.75,.30,.90,.08};
    for(int i=0; i<ia.length; i++){
      assertEquals(null, map.put(ia[i], da[i]));
    }
    ArrayList<Integer> expectKeys = al( 8,  6,  7,  5,  3,  0,  9,-86,-75,-30,-90);
    Collections.sort(expectKeys);
    ArrayList<Double> expectVals = new ArrayList<Double>();
    for(Integer k : expectKeys){
      for(int i=0; i<ia.length; i++){
        if(ia[i] == k){ expectVals.add(da[i]); break; }
      }
    }
    assertEquals(expectKeys, map.keys());
    assertEquals(expectVals, map.values());
  }
  
  @Test(timeout=1000) public void simple_put_remove2(){
    IMiniMap<Integer,Double> map = new SimpleListMM<Integer,Double>();
    put_remove2(map);
  }
  @Test(timeout=1000) public void fastget_put_remove2(){
    IMiniMap<Integer,Double> map = new FastGetListMM<Integer,Double>();
    put_remove2(map);
  }
  
  public void put_remove2(IMiniMap<Integer,Double> map){
    int [] ia   = { 8,  6,  7,  5,  3,  0,  9,-86,-75,-30,-90};
    double[] da = {.6,.07,.50,.33,.00,.90,.86,.75,.30,.90,.08};
    for(int i=0; i<ia.length; i++){
      assertEquals(null, map.put(ia[i], da[i]));
    }
    
    int expectSize = ia.length;
    assertEquals(expectSize, map.size());
    
    int [] remove = { 7, 1, 6, 4};
    for(int rdx : remove){
      assertEquals(true, map.contains(ia[rdx]));
      assertEquals(new Double(da[rdx]), map.get(ia[rdx]));
      assertEquals(new Double(da[rdx]), map.remove(ia[rdx]));
      assertEquals(false, map.contains(ia[rdx]));
      assertEquals(null, map.get(ia[rdx]));
      expectSize--;
      assertEquals(expectSize,map.size());
    }
  }
  
  
  // Create an Coord,String pair of the args given
  public Pair<Coord,String> cspr(Coord c, String s){ return new Pair<Coord,String>(c,s); }
  
  @Test(timeout=1000) public void simple_puts_keys_vals_order3(){
    IMiniMap<Coord,String> map = new SimpleListMM<Coord,String>();
    // Set of key value pairs
    Pair<Coord,String> [] kvs = (Pair<Coord,String>[]) new Pair[]{
      cspr( new Coord(0,0),  "Origin"   ), // 0
        cspr( new Coord(-3,2), "Q2"       ), // 5
        cspr( new Coord(9,6),  "Q1"       ), // 15
        cspr( new Coord(-7,-3),"Q3"       ), // 10
        cspr( new Coord(13,-7),"Q4"       ), // 20
        cspr( new Coord(11,-7),"Q4"       ), // 18
        cspr( new Coord(0,-7), "Boundary" ), // 7
        cspr( new Coord(12,13),"Q1"       ), // 25
        cspr( new Coord(-2,-7),"Q3"       ), // 9
        cspr( new Coord(11,-8),"Q4"       ), // 19
        cspr( new Coord(-1,7), "Q3"       ), // 8
        cspr( new Coord(1,0),  "Boundary" ), // 1
        cspr( new Coord(10,-2),"Q4"       ), // 12
    };
    List<Coord>  expectKeys = new ArrayList<Coord>();
    List<String> expectVals = new ArrayList<String>();
    for(Pair<Coord,String> kv : kvs){
      assertEquals(null, map.put( kv.k, kv.v));
      expectKeys.add(kv.k);
      expectVals.add(kv.v);
    }
    assertEquals(expectKeys, map.keys());
    assertEquals(expectVals, map.values());
  }
  @Test(timeout=1000) public void fastget_puts_keys_vals_order3(){
    Comparator<Coord> cmp = new MagnitudeComp();
    IMiniMap<Coord,String> map = new FastGetListMM<Coord,String>(cmp);
    // Set of key value pairs
    Pair<Coord,String> [] kvs = (Pair<Coord,String>[]) new Pair[]{
      cspr( new Coord(0,0),  "Origin"   ), // 0
        cspr( new Coord(-3,2), "Q2"       ), // 5
        cspr( new Coord(9,6),  "Q1"       ), // 15
        cspr( new Coord(-7,-3),"Q3"       ), // 10
        cspr( new Coord(13,-7),"Q4"       ), // 20
        cspr( new Coord(11,-7),"Q4"       ), // 18
        cspr( new Coord(0,-7), "Boundary" ), // 7
        cspr( new Coord(12,13),"Q1"       ), // 25
        cspr( new Coord(-2,-7),"Q3"       ), // 9
        cspr( new Coord(11,-8),"Q4"       ), // 19
        cspr( new Coord(-1,7), "Q3"       ), // 8
        cspr( new Coord(1,0),  "Boundary" ), // 1
        cspr( new Coord(10,-2),"Q4"       ), // 12
    };
    List<Coord>  expectKeys = new ArrayList<Coord>();
    List<String> expectVals = new ArrayList<String>();
    for(Pair<Coord,String> kv : kvs){
      assertEquals(null, map.put( kv.k, kv.v));
      expectKeys.add(kv.k);
    }
    Collections.sort(expectKeys, cmp);
    for(Coord k : expectKeys){
      for(int i=0; i<kvs.length; i++) {
        if(kvs[i].k.equals(k)){ expectVals.add(kvs[i].v); break; }
      }
    }
    assertEquals(expectKeys, map.keys());
    assertEquals(expectVals, map.values());
  }
  
  @Test(timeout=1000) public void simple_put_remove3(){
    IMiniMap<Coord,String> map = new SimpleListMM<Coord,String>();
    put_remove3(map);
  }
  
  public void put_remove3(IMiniMap<Coord,String> map){
    // Set of key value pairs
    Pair<Coord,String> [] kvs = (Pair<Coord,String>[]) new Pair[]{
      cspr( new Coord(0,0),  "Origin"   ), 
        cspr( new Coord(-3,2), "Q2"       ),
        cspr( new Coord(9,6),  "Q1"       ),
        cspr( new Coord(-7,-3),"Q3"       ),
        cspr( new Coord(13,-7),"Q4"       ),
        cspr( new Coord(11,-7),"Q4"       ),
        cspr( new Coord(0,-7), "Boundary" ),
        cspr( new Coord(12,13),"Q1"       ),
        cspr( new Coord(-2,-7),"Q3"       ),
        cspr( new Coord(11,-8),"Q4"       ),
        cspr( new Coord(-1,7), "Q3"       ),
        cspr( new Coord(1,0),  "Boundary" ),
        cspr( new Coord(10,-2),"Q4"       ),
    };
    for(Pair<Coord,String> kv : kvs){
      assertEquals(null, map.put( kv.k, kv.v));
    }
    
    int expectSize = kvs.length;
    assertEquals(expectSize, map.size());
    
    // Remove some keys
    int [] remove = { 10, 6, 2, 3};
    for(int rdx : remove){
      Pair<Coord,String> kv = kvs[rdx];
      assertEquals(true, map.contains(kv.k));
      assertEquals(kv.v, map.get(kv.k));
      assertEquals(kv.v, map.remove(kv.k));
      assertEquals(false, map.contains(kv.k));
      assertEquals(null, map.get(kv.k));
      expectSize--;
      assertEquals(expectSize,map.size());
    }
    
    // Verify all keys still present
    for(int i=0; i<kvs.length; i++){
      boolean removed = false;
      for(int rdx : remove){
        if(rdx == i){ removed = true; }
      }
      if(removed){ continue; }
      Pair<Coord,String> kv = kvs[i];      
      assertEquals(true, map.contains(kv.k));
      assertEquals(kv.v, map.get(kv.k));
      assertEquals(expectSize,map.size());
    }
  }
  
  // Create an String,Integer pair of the args given
  public Pair<String,Integer> sipr(String s, Integer i){ return new Pair<String,Integer>(s,i); }
  
  @Test(timeout=1000) public void simple_put_replaces1(){
    AbstractListMM<String,Integer> map = new SimpleListMM<String,Integer>();
    put_replaces1(map);
  }
  @Test(timeout=1000) public void fastget_put_replaces1(){
    AbstractListMM<String,Integer> map = new FastGetListMM<String,Integer>();
    put_replaces1(map);
  }
  public void put_replaces1(AbstractListMM<String,Integer> map){
    // Set of key value pairs
    Pair<String,Integer> [] kvs = (Pair<String,Integer>[]) new Pair[]{
      sipr("Mario",1),
        sipr("Luigi",2),
        sipr("Peach",3),
        sipr("Toad", 4),
    };
    for(Pair<String,Integer> kv : kvs){
      map.put(kv.k, kv.v);
    }
    // Set of replacements
    Pair<String,Integer> [] replacements = (Pair<String,Integer>[]) new Pair[]{
      sipr("Luigi",9),
        sipr("Peach",-2),
    };
    for(Pair<String,Integer> kv : replacements){
      Integer expect = map.get(kv.k);
      int expectIdx = map.indexOf(kv.k);
      assertEquals(expect, map.put(kv.k, kv.v));
      assertEquals(kv.v, map.get(kv.k));
      assertEquals(expectIdx, map.indexOf(kv.k));
    }
  }
  
  @Test(timeout=1000) public void simple_put_replaces2(){
    AbstractListMM<Coord,String> map = new SimpleListMM<Coord,String>();
    put_replaces2(map);
  }
  @Test(timeout=1000) public void fastget_put_replaces2(){
    Comparator<Coord> cmp = new MagnitudeComp();
    AbstractListMM<Coord,String> map = new FastGetListMM<Coord,String>(cmp);
    put_replaces2(map);
  }
  public void put_replaces2(AbstractListMM<Coord,String> map){
    // Set of key value pairs
    Pair<Coord,String> [] kvs = (Pair<Coord,String>[]) new Pair[]{
      cspr( new Coord(0,0),  "Origin"   ), 
        cspr( new Coord(-3,2), "Q2"       ),
        cspr( new Coord(9,6),  "Q1"       ),
        cspr( new Coord(-7,-3),"Q3"       ),
        cspr( new Coord(13,-7),"Q4"       ),
        cspr( new Coord(11,-7),"Q4"       ),
        cspr( new Coord(0,-7), "Boundary" ),
        cspr( new Coord(12,13),"Q1"       ),
        cspr( new Coord(-2,-7),"Q3"       ),
        cspr( new Coord(11,-8),"Q4"       ),
        cspr( new Coord(-1,7), "Q3"       ),
        cspr( new Coord(1,0),  "Boundary" ),
        cspr( new Coord(10,-2),"Q4"       ),
    };
    for(Pair<Coord,String> kv : kvs){
      assertEquals(null, map.put( kv.k, kv.v));
    }
    // Set of replacements
    Pair<Coord,String> [] replacements = (Pair<Coord,String>[]) new Pair[]{
      cspr( new Coord(-7,-3),"All negative"      ),
        cspr( new Coord(1,0),  "Between Q1 and Q4" ),
        cspr( new Coord(13,-7),"Lower right"       ),
        cspr( new Coord(0,0),  "Home sweet home"   ), 
        cspr( new Coord(10,-2),"Lower right"       ),
    };
    for(Pair<Coord,String> kv : replacements){
      String expect = map.get(kv.k);
      int expectIdx = map.indexOf(kv.k);
      assertEquals(expect, map.put(kv.k, kv.v));
      assertEquals(kv.v, map.get(kv.k));
      assertEquals(expectIdx, map.indexOf(kv.k));
    }
  }
  
  @Test(timeout=1000) public void simple_keys_vals_independent(){
    IMiniMap<String,Integer> map = new SimpleListMM<String,Integer>();
    keys_vals_independent(map);
  }  
  @Test(timeout=1000) public void fastget_keys_vals_independent(){
    IMiniMap<String,Integer> map = new FastGetListMM<String,Integer>();
    keys_vals_independent(map);
  }  
  public void keys_vals_independent(IMiniMap<String,Integer> map){
    // Set of key value pairs
    Pair<String,Integer> [] kvs = (Pair<String,Integer>[]) new Pair[]{
      sipr("Mario",1),
        sipr("Luigi",2),
        sipr("Peach",3),
        sipr("Toad", 4),
        sipr("Wario",5),
        sipr("Bowser",6),
        sipr("Bob-omb",7),
        sipr("Chain chomp",8),
    };
    for(Pair<String,Integer> kv : kvs){
      map.put(kv.k, kv.v);
    }
    
    List<String> expectKeys = map.keys();
    List<String> modKeys = map.keys();
    modKeys.remove(0);
    modKeys.remove(1);
    modKeys.remove(4);
    modKeys.add("Goomba");
    
    for(Pair<String,Integer> kv : kvs){
      assertEquals(kv.v, map.get(kv.k));
    }
    assertEquals(false, map.contains("Goomba"));
    assertEquals(null, map.get("Goomba"));
    assertEquals(expectKeys, map.keys());
    
    List<Integer> expectVals = map.values();
    List<Integer> modVals = map.values();
    modVals.remove(4);
    modVals.remove(3);
    modVals.remove(0);
    modVals.add(19);
    
    for(Pair<String,Integer> kv : kvs){
      assertEquals(kv.v, map.get(kv.k));
    }
    assertEquals(expectVals, map.values());
  }    
  
  
  @Test(timeout=1000) public void fastget_ensureComparable1(){
    FastGetListMM<Integer,String> map = new FastGetListMM<Integer,String>();
    map.ensureComparable(new Integer(1));
  }
  @Test(timeout=1000) public void fastget_ensureComparable2(){
    FastGetListMM<String,Integer> map = new FastGetListMM<String,Integer>(String.CASE_INSENSITIVE_ORDER);
    map.ensureComparable("Hello");
  }
  @Test(timeout=1000) public void fastget_ensureComparable3(){
    FastGetListMM<Coord,Integer> map = new FastGetListMM<Coord,Integer>(new MagnitudeComp());
    map.ensureComparable(new Coord(0,0));
  }
  @Test(timeout=1000) public void fastget_ensureComparable4_exception(){
    try{
      FastGetListMM<Coord,Integer> map = new FastGetListMM<Coord,Integer>();
      map.ensureComparable(new Coord(0,0));
    }
    catch(ClassCastException e){
      // Should NOT be casting to cause an error
      throw e;
    }
    catch(RuntimeException e){
      // Should be throwing a standard runtime exception on detecting
      // a problem
      return;
    }
    fail("A RuntimeException should have been thrown");
  }
  @Test(timeout=1000)
  public void fastget_ensureComparable5_exception(){
    try{
      FastGetListMM<Coord,Integer> map = new FastGetListMM<Coord,Integer>();
      map.put(new Coord(0,0), 5);
      map.put(new Coord(1,5), 7);
    }
    catch(ClassCastException e){
      // Should NOT be casting to cause an error
      throw e;
    }
    catch(RuntimeException e){
      // Should be throwing a standard runtime exception on detecting
      // a problem
      return;
    }
    fail("A RuntimeException should have been thrown");
  }
  
  @Test(timeout=1000) public void simple_comprehensive1(){
    comprehensive1(new SimpleListMM<String,Integer>());
  }
  @Test(timeout=1000) public void fastget_comprehensive1(){
    comprehensive1(new FastGetListMM<String,Integer>());
  }
  public void comprehensive1(IMiniMap<String,Integer> map){
    assertEquals(true, map.isEmpty());
    map.put("B",15);
    map.put("A",5);
    map.put("R",-5);
    map.put("D",55);
    map.put("Poems",128);
    map.put("Plays",256);
    assertEquals(6,map.size());
    
    map.put("Romance",64);
    map.put("Drama",32);
    assertEquals(new Integer(-5), map.get("R"));
    assertEquals(new Integer(55), map.get("D"));
    assertEquals(null, map.get("Z"));
    assertEquals(new Integer(128),map.get("Poems"  ));
    assertEquals(new Integer(256),map.get("Plays"  ));
    assertEquals(new Integer(64) ,map.get("Romance"));
    assertEquals(new Integer(32) ,map.get("Drama"  ));
    assertEquals(null, map.get("Bad"));
    
    assertEquals(new Integer(256), map.remove("Plays"));
    assertEquals(null, map.remove("Dud"));
    assertEquals(new Integer(-5), map.remove("R"));
    assertEquals(new Integer(15), map.remove("B"));
    
    assertEquals(false, map.isEmpty());
    assertEquals(5,map.size());
    
    List<String> keys = map.keys();
    List<Integer> vals = map.values();
    Collections.sort(keys);
    Collections.sort(vals);
    assertEquals(al("A", "D", "Drama", "Poems", "Romance"),keys);
    assertEquals(al(5, 32, 55, 64, 128),vals);
    
    assertEquals(null, map.put("Marlow",+1));
    assertEquals(new Integer(128), map.put("Poems",900));
    assertEquals(new Integer(5), map.put("A",42));
    assertEquals(null, map.put("Songs",32));
    assertEquals(null, map.put("B",0));
    assertEquals(null, map.remove("R"));
    assertEquals(new Integer(64), map.remove("Romance"));
    
    keys = map.keys();
    vals = map.values();
    Collections.sort(keys);
    Collections.sort(vals);
    assertEquals(al("A", "B", "D", "Drama", "Marlow", "Poems", "Songs"),keys);
    assertEquals(al(0, 1, 32, 32, 42, 55, 900),vals);
    
  }
  
  // This clas helps ensure the AbstractListMM contains most of the
  // methods that should be implemented
  class AlternatingListMM<K,V> extends AbstractListMM<K,V>{
    protected boolean addFront;
    public AlternatingListMM(){
      super();
      addFront = false;
    }
    @Override public int indexOf(K key){
      for(int i=this.keys.size()-1; i>=0; i--){
        if(key.equals(keys.get(i))){
          return i;
        }
      }
      return -1;
    }
    @Override public V put(K key, V val){
      int idx = this.indexOf(key);
      if(idx < 0){
        if(addFront){
          this.keys.add(0,key);
          this.vals.add(0,val);
        }
        else{
          this.keys.add(key);
          this.vals.add(val);
        }
        addFront = !addFront;
        return null;
      }
      else{
        V old = this.vals.get(idx);
        this.vals.set(idx,val);
        return old;
      }        
    }
  }
  
  // This test helps ensure that the proper methods have been defined 
  @Test(timeout=1000) public void AbstractListMM_methods(){
    AbstractListMM<Integer,String> map = new AlternatingListMM<Integer,String>();
    assertEquals(0, map.size());
    assertEquals(true, map.isEmpty());
    
    map.put(1,"hi");
    map.put(2,"bye");
    map.put(3,"sky");
    map.put(4,"rye");
    map.put(5,"whiskey");
    
    assertEquals(5, map.size());
    assertEquals(false, map.isEmpty());
    
    assertEquals(true, map.contains(4));
    assertEquals("rye", map.get(4));
    assertEquals(true, map.contains(1));
    assertEquals("hi", map.get(1));
    
    assertEquals(al(    4,    2,   1,    3,   5), map.keys());
    assertEquals(al("rye","bye","hi","sky","whiskey"), map.values());
    
    assertEquals("sky", map.remove(3));
    assertEquals(false, map.contains(3));
    
    assertEquals(4, map.size());
    assertEquals(false, map.isEmpty());
    
    assertEquals("rye", map.remove(4));
    assertEquals(false, map.contains(4));
    
    assertEquals(3, map.size());
    assertEquals(false, map.isEmpty());
    
    assertEquals(al(   2,   1,   5), map.keys());
    assertEquals(al("bye","hi","whiskey"), map.values());
  }
  
  /**
  
  // HONORS SECTION TESTS
  
  @Test (timeout=1000)
  public void honors_inner_pair1(){
    PairMap<String,Integer> apair = new PairMap<String,Integer>();
    // Dr. Snyder forgot to make the inner class static... so we need a PairMap object to make inner Pairs.
    PairMap<String,Integer>.Pair innerpair = apair.new Pair("name",21);
    assertEquals( "name",          innerpair.k );
    assertEquals( (Integer)21,     innerpair.v );
  }
  
  
  // check the first constructor sets up everything it ought to, and do a couple simple checks.
  @Test (timeout=1000)
    public void honors_pm1(){
    PairMap<String,Integer> apair = new PairMap<String,Integer>();
    assertNull(apair.cmp);
    assertNotNull(apair.pairs);
    assertEquals(0,apair.size());
    assertTrue(apair.isEmpty());
  }
  
  
  // check the second constructor sets up everything it ought to, and do a couple simple checks.
  @Test (timeout=1000)
    public void honors_pm2(){
    PairMap<String,Integer> apair = new PairMap<String,Integer>(new Comparator<String>(){
      @Override public int compare(String s1, String s2){ return s1.length() - s2.length();}
    });
    assertNotNull(apair.cmp);
    assertNotNull(apair.pairs);
    assertEquals(0,apair.size());
    assertTrue(apair.isEmpty());
  }
  
  
  @Test (timeout=1000)
  public void honors_pm_contains(){
    PairMap<String,Integer> apair = new PairMap<String,Integer>();

    assertFalse(apair.contains("hello"));
    // we'll manually add it in...
    apair.pairs.add(apair.new Pair("hello",5));
    assertTrue(apair.contains("hello"));
  }
  
  
  // put() test. relies upon contains().
  @Test (timeout=1000)
    public void honors_pm_put1(){
    PairMap<Integer,Double> apair = new PairMap<Integer,Double>();
    apair.put(10,10.5);
    assertTrue(apair.contains(10));
    apair.put(5,25.0);
    assertTrue(apair.contains(5));
    apair.put(100,2.0);
    assertTrue(apair.contains(100));
    
    // make sure they're still in there afterwards, too.
    assertTrue(apair.contains(5));
    assertTrue(apair.contains(10));
  }
  
  
  // put() test - do we receive the evicted value?
  @Test (timeout=1000)
    public void honors_pm_put2(){
    PairMap<Integer,Double> apair = new PairMap<Integer,Double>();
    
    Double d = apair.put(5,25.0);
    assertNull(d);
    
    d = apair.put(5,100.0);
    assertEquals(25.0,d,0);
  }
  
  
  // get() test. should return null for non-present keys.
  @Test (timeout=1000)
    public void honors_pm_get1(){
    PairMap<Integer,Integer> apair = new PairMap<Integer,Integer>();
    assertNull(apair.get(100));
    apair.pairs.add(apair.new Pair(2,4));
    assertNull(apair.get(4));
  }
  
  
  // get() test.
  @Test (timeout=1000)
  public void honors_pm_get2(){
    PairMap<Integer,String> apair = new PairMap<Integer,String>();
    
    assertNull(apair.get(100));
    // we'll manually add a few in... thus it must be done in-order.
    apair.pairs.add(apair.new Pair(1, "one"));
    apair.pairs.add(apair.new Pair(2, "two"));
    apair.pairs.add(apair.new Pair(3, "three"));
    
    assertEquals("two",   apair.get(2));
    assertEquals("one",   apair.get(1));
    assertEquals("three", apair.get(3));
    
  }
  
  // remove() test.
  @Test (timeout=1000)
  public void honors_pm_remove1(){
    PairMap<Integer,String> apair = new PairMap<Integer,String>();
    assertNull(apair.remove(5));

    // manually adding a few so that it's not put()-dependent.
    apair.pairs.add(apair.new Pair(1, "one"));
    apair.pairs.add(apair.new Pair(2, "two"));
    apair.pairs.add(apair.new Pair(3, "three"));
    apair.pairs.add(apair.new Pair(4, "four"));

    assertEquals("one",  apair.remove(1));
    assertNull(apair.remove(1));
    assertEquals("three",apair.remove(3));
    assertEquals("four", apair.remove(4));
    assertEquals("two",  apair.remove(2));
    assertNull(apair.remove(2));
    assertNull(apair.remove(3));
    assertNull(apair.remove(4));
  }
   
  // keys() test.
  @Test (timeout=1000)
  public void honors_pm_keys(){
    PairMap<Integer,String> apair = new PairMap<Integer,String>();

    // manually adding a few so that it's not put()-dependent.
    apair.pairs.add(apair.new Pair(1, "one"));
    apair.pairs.add(apair.new Pair(2, "two"));
    apair.pairs.add(apair.new Pair(3, "three"));
    apair.pairs.add(apair.new Pair(4, "four"));
    assertEquals(al(1,2,3,4),apair.keys());
  }
  // values() test.
  @Test (timeout=1000)
  public void honors_pm_values(){
    PairMap<Integer,String> apair = new PairMap<Integer,String>();

    // manually adding a few so that it's not put()-dependent.
    apair.pairs.add(apair.new Pair(1, "one"));
    apair.pairs.add(apair.new Pair(2, "two"));
    apair.pairs.add(apair.new Pair(3, "three"));
    apair.pairs.add(apair.new Pair(4, "four"));
    assertEquals(al("one","two","three","four"),apair.values());
  }

    // see before honors section tests for the definition of this.comprehensive1.
    @Test (timeout=1000)
    public void pairmap_comprehensive1(){
 comprehensive1(new PairMap<String,Integer>());
    }

*/

}