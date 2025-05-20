import java.util.*;

public class MyMap {

    //poate fi accesata clasa fara a folosi instante a parintelui sau fara a folosi metode din clasa parinte
    private static class GradeComparator implements Comparator<Integer>{
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }
    //private Map<Integer, List<Student>> studentMap = new TreeMap<>((o1,o2) -> Integer.compare(o2,o1));
    private Map<Integer, List<Student>> studentMap = new TreeMap<>(new GradeComparator());

    public void add(Student student){
        int grade = Math.round(student.getGrade());
        List<Student> foundList = studentMap.get(grade);
        if (foundList != null)
            foundList.add(student);
        else {
            ArrayList<Student> newList = new ArrayList<>();
            newList.add(student);
            studentMap.put(grade, newList);
        }
    }

    public Set<Map.Entry<Integer,List<Student>>> getEntries(){
        return studentMap.entrySet();
    }

}
