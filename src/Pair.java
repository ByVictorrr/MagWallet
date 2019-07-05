import java.io.Serializable;

public class Pair<K, V> implements Serializable {

    private final K key;
    private final V value;

    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }

    //public static <K, V> Pair <K, V> of(K a, V b){ return new Pair<>(a, b); }

    public K getKey() { return key; }

    public V getValue() { return value; }



    @Override
    public int hashCode() { return 31 * key.hashCode() + value.hashCode(); }
    @Override
    public boolean equals(Object obj) {
        if(this == obj) //Case these values are the same concrete object
            return true;
        if(obj == null || getClass() != obj.getClass()) //Case these object are not of the same class
            return false;
        Pair<?, ?> pair = (Pair<?, ?>) obj;
        if(!key.equals(pair.key))
            return false;
        return value.equals(pair.value);
    }
    @Override
    public String toString() { return "(" + key + ", " + value + ")"; }
}
