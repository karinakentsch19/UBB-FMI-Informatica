import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    private static <E> void printArea(List<E> list, Area<E> area){
        for (E e: list){
            System.out.println(area.compute(e));
        }
    }

    private static <E> void printList(List<E> list, Predicate<E> predicate){
        for (E e: list){
            if (predicate.test(e))
                System.out.println(e);
        }
    }

    public static <E> List<E> genericFilter(List<E> list, Predicate<E> predicate){
        return list.stream().filter(predicate).toList();
    }


    public static <E> List<E> genericFilterComparator(List<E> list, Predicate<E> predicate, Comparator<E> comparator){
        return list.stream().filter(predicate).sorted(comparator).toList();
    }



    public static void main(String[] args) {
        Circle circle = new Circle(10);
        Circle circle2 = new Circle(15);
        List<Circle> circleList = Arrays.asList(circle,circle2);

        //Anonymous Class
        printArea(circleList, new Area<Circle>() {
            @Override
            public double compute(Circle circle) {
                return Math.PI * circle.getRadius() * circle.getRadius();
            }
        });

        printArea(circleList, c -> Math.PI * c.getRadius() * c.getRadius());

        Predicate<Circle> smallCirclesPredicate = c -> c.getRadius() < 12;
        Predicate<Circle> largeCirclesPredicate = smallCirclesPredicate.negate();
        Predicate<Circle>smallEvenCirclesPredicate = smallCirclesPredicate.and(c->c.getRadius() % 2 == 0);
        printList(circleList, smallCirclesPredicate);
        printList(circleList, largeCirclesPredicate);
        printList(circleList, smallEvenCirclesPredicate);


//        Function<String, Integer> function = s -> Integer.valueOf(s);
        Function<String, Integer> function = Integer::valueOf;
        Function<String, Integer> functionPower = function.andThen(c -> c * c);
        System.out.println(function.apply("123"));
        System.out.println(functionPower.apply("12"));

        Supplier<LocalDateTime> date = LocalDateTime::now;
        System.out.println(date.get());

        Supplier<List<String>> emptyStringList = ArrayList::new;
        System.out.println(emptyStringList.get());

        Comparator<Integer> descending = (i1,i2) -> i2 - i1;
        Comparator<Integer> ascending = descending.reversed();
        Comparator<Integer> ascending2 = Integer::compare;

        List<Integer> integerList = Arrays.asList(1,2,3,4);
        integerList.sort(descending);
        System.out.println(integerList);


        List<Integer> list = genericFilter(integerList, x -> x %2 == 0);
        List<Integer> list2 = genericFilterComparator(integerList, x -> x % 2 == 0, Integer::compare);
        System.out.println(list);
        System.out.println(list2);

    }
}