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
    private Predicate<T> filter; // Non final, perchè setIterationPolicy lo cambia

    /**
    * 
    * @param elements the array used to conserve the elements passed by the test class
    * @param filter the predicate used to filter out the elements in the collection during the iteration
    */
    public IterableWithPolicyImpl(final T[] elements, final Predicate<T> filter) {
        this.elements = elements;
        this.filter = filter;
    }

    /* La lambda è una scorciatoia per scrivere tutto in una riga quando l'interfaccia ha un solo metodo, elem->true si legge
    dato un elemento restituisci true */
    public IterableWithPolicyImpl(final T[] elements) {
        this(elements, elem -> true); // Richiama l'altro costruttore della stessa classe passandogli questi argomenti 
        // elem -> true sarebbe il filtro "accetta tutto", this deve essere la prima istruzione del costruttore 
    }

    @Override
    public void setIterationPolicy(final Predicate<T> filter) {
        this.filter = filter;
    }

    @Override 
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {
        private int index = 0;

        /* Finchè non sono arrivato in fondo e l'elemento corrente viene rifiutato dal filtro, vai avanti di uno,
        filter.test() chiede al filtro se l'elemento corrente va bene (true) o no(false), test è quello dell'interfaccia
        inoltre ! davanti nega, quindi la condizione è vera se l'elemento è rifiutato  */
        private void skipRejected() {
            while (index < elements.length && !filter.test(elements[index])) {
                index++;
            }
        }

        /* Il metodo hasNext risponde alla domanda: "c'è ancora qualcosa da leggere?" senza spostare il segna posto.
        negli array Java, length è un campo che contiene il numero di elementi, pertanto con index < elements.length
        significa se ci sono ancora elementi da leggere, se l'array è vuoto allora la lunghezza è 0, quindi 
        return false */
        @Override
        public boolean hasNext() {
            skipRejected();
            return index < elements.length;
        }

        /* next() restituisce l'elemento corrente e incrementa index, così si prepara al successivo */
        @Override 
        public T next() {
            skipRejected();
            return elements[index++];
        }
    }
}