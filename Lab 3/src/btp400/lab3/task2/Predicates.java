/**********************************************
 * Workshop #
 * Course:BTP400NBB - Semester 4
 * Last Name: Thaker
 * First Name: Soham
 * ID: 011-748-159
 * Section: NBB
 * This assignment represents my own work in accordance with Seneca Academic Policy.
 * Signature Date: 27/03/2022
 * **********************************************/

package btp400.lab3.task2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Defines 4 functions that are being tested by the TestPredicates class.
 * @see Predicate
 * @see Collection
 * @see ArrayList
 * @see Iterator
 * @see List
 */
public class Predicates {

    /**
     * Removes the element from the collection for which the predicate returns true.
     * It uses iterators to iterate over the collection to avoid
     * ConcurrentModificationException.
     * @param coll A collection of type T elements.
     * @param pred A Predicate instance that returns a boolean based on satisfied condition.
     * @param <T> Template of type Integer but could be of any other type.
     */
    public static <T> void remove(Collection<T> coll, Predicate<T> pred){
        for (Iterator<T> tIt = coll.iterator(); tIt.hasNext();) {
            T t = tIt.next();

            if(pred.test(t)){
                tIt.remove();
            }
        }
    }

    /**
     * Removes the element from the collection for which the predicate returns false.
     * It uses iterators to iterate over the collection to avoid
     * ConcurrentModificationException.
     * @param coll A collection of type T elements.
     * @param pred A Predicate instance that returns a boolean based on satisfied condition.
     * @param <T> Template of type Integer but could be of any other type.
     */
    public static <T> void retain(Collection<T> coll, Predicate<T> pred) {
        for (Iterator<T> tIt = coll.iterator(); tIt.hasNext();) {
            T t = tIt.next();

            if(!pred.test(t)){
                tIt.remove();
            }
        }
    }

    /**
     * Collects all elements into another collection for which predicate
     * is true which is to be returned to the caller.
     * @param coll A collection of type T elements.
     * @param pred A Predicate instance that returns a boolean based on satisfied condition.
     * @param <T> Template of type Integer but could be of any other type.
     * @return A list of type T elements that contains all the elements for which the predicate returned true value.
     */
    public static <T> List<T> collect(Collection<T> coll, Predicate<T> pred) {
        List<T> list = new ArrayList<T>();

        for (T elem: coll) {
            if(pred.test(elem)){
                list.add(elem);
            }
        }

        return list;
    }

    /**
     * Finds and returns first index for which the predicate returns truthy value else returns -1.
     * @param list A collection of type T elements.
     * @param pred A Predicate instance that returns a boolean based on satisfied condition.
     * @param <T> Template of type Integer but could be of any other type.
     * @return Index of the first element of which the predicate returns a truthy value or -1 if predicate
     *         gives false value for all elements in the collection.
     */
    public static <T> int find(ArrayList<T> list, Predicate<T> pred) {
        for(T elem: list) {
            if (pred.test(elem)) {
                return list.indexOf(elem);
            }
        }
        return -1;
    }
}
