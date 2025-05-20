import com.sun.source.tree.Tree;

import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Student s1= new Student("Dan", 4.5f);
        Student s2= new Student("Ana", 8.5f);
        Student s3= new Student("Dan", 4.5f);

        Set<Student> students = new HashSet<Student>();
        students.add(s1);
        students.add(s2);
        students.add(s3);

        System.out.println(students);

        TreeSet<Student> students1 = new TreeSet<>(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o2.getName().compareTo(o1.getName());
            }
        });
        //TreeSet<Student> students1 = new TreeSet<>((o1, o2) -> o2.getName().compareTo(o1.getName()));
        //daca instantiam colectia cu un comparator nu mai trebuie ca student sa mosteneasca comparable
        students1.add(s1);
        students1.add(s2);
        students1.add(s3);

        System.out.println(students1);

//        Map<String, Student> studentMap = new HashMap<>();
        Map<String, Student> studentMap = new TreeMap<>();
        studentMap.put(s1.getName(), s1);
        studentMap.put(s2.getName(), s2);
        studentMap.put(s2.getName(), s2);

        for(String name: studentMap.keySet())
            System.out.println(studentMap.get(name));

        for (Map.Entry<String, Student> element : studentMap.entrySet())
            System.out.println(element.getValue());


        MyMap myMap = new MyMap();
        myMap.add(s1);
        myMap.add(s2);
        myMap.add(s3);

        for (Map.Entry<Integer, List<Student>> entry : myMap.getEntries()){
            List<Student> studentList = entry.getValue();
            Collections.sort(studentList);
            System.out.println(studentList);
        }
        System.out.println("/////////////////////");
//        MyMapInheritance myMapInheritance = new MyMapInheritance();
//        myMapInheritance.add(s1);
//        myMapInheritance.add(s2);
//        myMapInheritance.add(s3);
//
//        for (Map.Entry<Integer, List<Student>> entry : myMapInheritance.getEntries()){
//            List<Student> studentList = entry.getValue();
//            Collections.sort(studentList);
//            System.out.println(studentList);
//        }
    }
}