package it.unibo.inner.impl;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

import java.util.Iterator;

/* La mia classe tiene l'array che le passa il Test(nel costruttore) e sa fornire un iteratore
che lo scorre, quindi Test crea l'array e lo passa alla mia classe. Inner class (ArrayIterator) tiene 
il segna posto (index) e implementa hasNext() e next() */

/** 
* This class receives an array by the class TestIterablePlain and then provides an iterator
* @param <T> the type of the elements
*/
public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {
        
    private final T[] elements;

    /**
    * 
    * @param elements the array used to conserve the elements passed by the test class
    * 
    */
    public IterableWithPolicyImpl(T[] elements) {
        this.elements = elements;
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        // Vuoto per ora
    }

    @Override 
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {
        private int index = 0;

        /* Il metodo hasNext risponde alla domanda: "c'è ancora qualcosa da leggere?" senza spostare il segna posto.
        negli array Java, length è un campo che contiene il numero di elementi, pertanto con index < elements.length
        significa se ci sono ancora elementi da leggere, se l'array è vuoto allora la lunghezza è 0, quindi 
        return false */
        @Override
        public boolean hasNext() {
            return index < elements.length;
        }

        /* next() restituisce l'elemento corrente e incrementa index, così si prepara al successivo */
        @Override 
        public T next() {
            return elements[index++];
        }
    }
}