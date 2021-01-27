// --== CS400 File Header Information ==--
// Name: Deming Xu
// Email: dxu227@wisc.edu
// Team: CG
// TA: Yeping Wang
// Lecturer: Gary Dahl
// Notes to Grader: No note

import java.util.NoSuchElementException;

public interface MapADT<KeyType,ValueType> {
    public boolean put(KeyType key, ValueType value);
    public ValueType get(KeyType key) throws NoSuchElementException;
    public int size();
    public boolean containsKey(KeyType key);
    public ValueType remove(KeyType key);
    public void clear();
}
