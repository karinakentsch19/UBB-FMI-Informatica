import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    private static final List<String> list = Arrays.asList("asf", "bcd", "asd", "bed", "bbb");

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//       list.stream().filter(x -> {System.out.println("filter " + x); return x.startsWith("b");}).
//               map(x -> {System.out.println("map " + x); return x.toUpperCase();}).
//               forEach(x -> {System.out.println("forEach " + x);});
//
//       List<String> list1 = list.parallelStream().filter(x -> {System.out.println(Thread.currentThread().getName() + "filter " + x); return x.startsWith("b");}).
//               map(x -> {System.out.println(Thread.currentThread().getName() + "map " + x); return x.toUpperCase();}).
//               collect(Collectors.toList());
//
//        list.parallelStream().filter(x -> {System.out.println(Thread.currentThread().getName() + "filter " + x); return x.startsWith("b");}).
//                map(x -> {System.out.println(Thread.currentThread().getName() + "map " + x); return x.toUpperCase();}).
//                forEachOrdered(x -> {
//                    System.out.println(Thread.currentThread().getName() + "forEach " + x);
//                });

//       list1.forEach(System.out::println);



//        new ForkJoinPool(2).submit(() -> list.parallelStream().filter(x -> {System.out.println(Thread.currentThread().getName() + "filter " + x); return x.startsWith("b");}).
//                map(x -> {System.out.println(Thread.currentThread().getName() + "map " + x); return x.toUpperCase();}).
//                forEachOrdered(x -> {
//                    System.out.println(Thread.currentThread().getName() + "forEach " + x);
//                })).get();


//        System.out.println(list.stream().filter(x -> x.startsWith("b")).
//                map(String::toUpperCase).
//                reduce("",(a,b) -> a + b));


//        Optional<String> optional = list.stream().filter(x -> x.startsWith("b")).
//                map(String::toUpperCase).
//                reduce((a,b) -> a + b);
//
//        System.out.println(optional.get());



    }
}