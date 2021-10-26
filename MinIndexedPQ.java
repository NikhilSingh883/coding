import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MinIndexedPQ<T extends Comparable<T>> {

  private int sz;
  private final int N,D;
  private final int[] ch, par,pm,im;

  public final Object[] val;

  public MinIndexedPQ(int degree, int maxSize) {
    if (maxSize <= 0) throw new IllegalArgumentException("maxSize <= 0");

    D = max(2, degree);
    N = max(D + 1, maxSize);

    im = new int[N];
    pm = new int[N];
    ch = new int[N];
    par = new int[N];
    val = new Object[N];

    for (int i = 0; i < N; i++) {
      par[i] = (i - 1) / D;
      ch[i] = i * D + 1;
      pm[i] = im[i] = -1;
    }
  }

  public int size() {
    return sz;
  }

  public boolean isEmpty() {
    return sz == 0;
  }

  public boolean contains(int ki) {
    keyInBoundsOrThrow(ki);
    return pm[ki] != -1;
  }

  public int peekMinKeyIndex() {
    isNotEmptyOrThrow();
    return im[0];
  }

  public int pollMinKeyIndex() {
    int minki = peekMinKeyIndex();
    delete(minki);
    return minki;
  }

  public T peekMinValue() {
    isNotEmptyOrThrow();
    return (T) val[im[0]];
  }

  public T pollMinValue() {
    T minValue = peekMinValue();
    delete(peekMinKeyIndex());
    return minValue;
  }

  public void insert(int ki, T value) {
    if (contains(ki)) throw new IllegalArgumentException("index already exists; received: " + ki);
    valueNotNullOrThrow(value);
    pm[ki] = sz;
    im[sz] = ki;
    val[ki] = value;
    swim(sz++);
  }


  public T valueOf(int ki) {
    keyExistsOrThrow(ki);
    return (T) val[ki];
  }


  public T delete(int ki) {
    keyExistsOrThrow(ki);
    final int i = pm[ki];
    swap(i, --sz);
    sink(i);
    swim(i);
    T value = (T) val[ki];
    val[ki] = null;
    pm[ki] = -1;
    im[sz] = -1;
    return value;
  }

  private void sink(int i) {
    for (int j = minch(i); j != -1; ) {
      swap(i, j);
      i = j;
      j = minch(i);
    }
  }

  private void swim(int i) {
    while (less(i, par[i])) {
      swap(i, par[i]);
      i = par[i];
    }
  }

  private int minch(int i) {
    int index = -1, from = ch[i], to = min(sz, from + D);
    for (int j = from; j < to; j++) if (less(j, i)) index = i = j;
    return index;
  }

  private void swap(int i, int j) {
    pm[im[j]] = i;
    pm[im[i]] = j;
    int tmp = im[i];
    im[i] = im[j];
    im[j] = tmp;
  }


  private boolean less(int i, int j) {
    return ((Comparable<? super T>) val[im[i]]).compareTo((T) val[im[j]]) < 0;
  }


  private boolean less(Object obj1, Object obj2) {
    return ((Comparable<? super T>) obj1).compareTo((T) obj2) < 0;
  }

  @Override
  public String toString() {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(im[i]);
    return lst.toString();
  }

  private void isNotEmptyOrThrow() {
    if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
  }

  private void keyExistsAndValueNotNullOrThrow(int ki, Object value) {
    keyExistsOrThrow(ki);
    valueNotNullOrThrow(value);
  }

  private void keyExistsOrThrow(int ki) {
    if (!contains(ki)) throw new NoSuchElementException("Index does not exist; received: " + ki);
  }

  private void valueNotNullOrThrow(Object value) {
    if (value == null) throw new IllegalArgumentException("value cannot be null");
  }

  private void keyInBoundsOrThrow(int ki) {
    if (ki < 0 || ki >= N)
      throw new IllegalArgumentException("Key index out of bounds; received: " + ki);
  }

    // Testing
    
  public boolean isMinHeap() {
    return isMinHeap(0);
  }

  private boolean isMinHeap(int i) {
    int from = ch[i], to = min(sz, from + D);
    for (int j = from; j < to; j++) {
      if (!less(i, j)) return false;
      if (!isMinHeap(j)) return false;
    }
    return true;
  }
}
