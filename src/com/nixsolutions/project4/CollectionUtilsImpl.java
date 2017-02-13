package com.nixsolutions.project4;

import interfaces.task4.CollectionUtils;

import java.util.*;

/**
 * Created by annnikon on 27.01.17.
 */
public class CollectionUtilsImpl implements CollectionUtils {

    @Override
    /**Retuns a sorted list of elements.
     * Union(a,b) equals union (b,a).
     * Union can have duplicates.*/
    public List<Integer> union(Collection<Integer> a, Collection<Integer> b) {
        if (a == null || b == null) {
            throw new NullPointerException("Null parameter is given");
        }
        List<Integer> result = new LinkedList<Integer>(a);
        result.addAll(b);
        return getSortedList(result);
    }

    @Override
    /**Retuns a sorted list of elements.
     * Intersection(a,b) equals intersection(b,a).
     * Intersection can have duplicates.*/
    public List<Integer> intersection(Collection<Integer> a, Collection<Integer> b) {
        if (a == null || b == null) {
            throw new NullPointerException("Null parameter is given");
        }

        List<Integer> result = new LinkedList<>();
        for (Integer element : a) {
            if (contains(b, element)) {
                result.add(element);
            }
        }

        for(Integer element : b) {
            if (contains(a,element)) {
                result.add(element);
            }
        }

        return getSortedList(result);
    }

    /**
     * Elements will be added to the LinkedHashSet only if they are unique.
     */
    @Override
    public Set<Integer> intersectionWithoutDuplicate(Collection<Integer> a,
                                                     Collection<Integer> b) {
        return new LinkedHashSet<>(intersection(a, b));
    }

    /**
     * Elements of collection a, which are not present in collection b, in sorted order.
     */
    @Override
    public Collection<Integer> difference(Collection<Integer> a,
                                          Collection<Integer> b) {
        if (a==null || b==null) {
            throw new NullPointerException("Null collection given.");
        }
        LinkedList<Integer> result = new LinkedList<>();
        for (Integer element : a) {
            if (!contains(b, element)) {
                result.add(element);
            }

        }
        return getSortedList(result);
    }

    /**
     * Two collections of integers are equal if after sortig
     * their elements with the same indexes have the same values.
     */
    private boolean areEqual(Collection<Integer> a,
                             Collection<Integer> b) {

        if (a.size() != b.size()) {
            return false;
        }

        List<Integer> sortedA = getSortedList(a);
        List<Integer> sortedB = getSortedList(b);
        for (int i = 0; i < sortedA.size(); i++) {
            if (!sortedA.get(i).equals(sortedB.get(i))) {
                return false;
            }

        }
        return true;
    }

    /*Returns a list of integers, sorted by increasing.*/
    private List<Integer> getSortedList(Collection<Integer> original) {
        List<Integer> list = new ArrayList<Integer>(original);
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer left, Integer right) {
                return left - right;
            }
        };
        list.sort(comparator);
        return list;
    }


    /**
     * Checks if collection contains integers with the same value as given.
     * Standart method Collection.contains() isn`t good
     * because it compares references, not values.
     */
    private boolean contains(Collection<Integer> collection, Integer value) {
        for (Integer element : collection) {
            if (element.equals(value)) {
                return true;
            }
        }
        return false;
    }


    /*Allows quickly create Integer collections for test.*/
    private static Collection<Integer> create(Integer... args) {
        if (args == null || args.length == 0) {
            return null;
        }
        return Arrays.asList(args);
    }

    public static void testUnion() {
        System.out.println("\nTest union:");

        CollectionUtilsImpl utils = new CollectionUtilsImpl();
        Collection<Integer> a = create(1, 2, 4, 8, 5, 6, 5, 1);
        Collection<Integer> b = create(2, 3, 2, 2);
        Collection<Integer> c = create(1, 8, 2);
        Collection<Integer> d = create(1, 4);

        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("Union (a, b): " + utils.union(a, b));
        System.out.println("Union (b, a): " + utils.union(b, a));
        System.out.println("Commutative low: " + utils.areEqual(utils.union(a, b),
                utils.union(b, a))); //true
        System.out.println("c: " + c);
        System.out.println("d: " + d);
        System.out.println("Union (c, d): " + utils.union(c, d));
        try {
            System.out.print("Union (c, null): ");
            System.out.println(utils.union(c, null));
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            System.out.print("Union (null, d): ");
            System.out.println(utils.union(null, d));
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println();

    }

    public static void testIntersection() {
        System.out.println("\nTest intersection:");
        CollectionUtilsImpl utils = new CollectionUtilsImpl();
        Collection<Integer> a = create(1, 2, 4, 8, 5, 6, 5, 1);
        Collection<Integer> b = create(2, 2, 2, 3);
        Collection<Integer> c = create(2, 3, 2, 2);
        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("c: " + c);
        System.out.println("Intersection (a, b): " + utils.intersection(a, b));
        System.out.println("without duplicate: "
                + utils.intersectionWithoutDuplicate(a, b));
        System.out.println("Intersection (b, a): " + utils.intersection(b, a));
        System.out.println("without duplicate: "
                + utils.intersectionWithoutDuplicate(b, a));
        System.out.println("Intersection (b, c): " + utils.intersection(b, c));
        System.out.println("without duplicate: " +
                utils.intersectionWithoutDuplicate(b, c));
        try {
            System.out.print("Intersection (a, null): ");
            System.out.println(utils.intersection(a, null));
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void testDifference() {

        System.out.println("\nTest difference:");
        CollectionUtilsImpl utils = new CollectionUtilsImpl();
        Collection<Integer> a = create(4, 5, 6, 7);
        Collection<Integer> b = create(1, 2, 3, 4, 5);
        Collection<Integer> c = create(5, 7, 6, 4);
        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("c: " + c);
        System.out.println("Difference(a, b): " + utils.difference(a, b));
        System.out.println("Difference(b, a): " + utils.difference(b, a));
        System.out.println("Difference(a, c): " + utils.difference(a, c));

        try {
            System.out.print("Difference (a, null): ");
            System.out.println(utils.difference(a, null));
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void bugsTest() {
        CollectionUtilsImpl utils = new CollectionUtilsImpl();

        Collection<Integer> a = create(1, 2);
        Collection<Integer> b = create(1, 3);
        System.out.println("Intersection (a, b): " + utils.intersection(a, b));
        try {
            System.out.print("Difference (a, null): ");
            System.out.println(utils.difference(a, null));
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }



    }

    public static void main(String[] args) {

        //testUnion();
        //testIntersection();
        //testDifference();
        bugsTest();

    }
}
