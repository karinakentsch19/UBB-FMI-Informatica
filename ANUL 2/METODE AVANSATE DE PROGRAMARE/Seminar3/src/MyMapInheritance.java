import java.util.*;

public class MyMapInheritance extends TreeMap<Integer,List<Student>> {

    //poate fi accesata clasa fara a folosi instante a parintelui sau fara a folosi metode din clasa parinte
    private static class GradeComparator implements Comparator<Integer>{
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    public void add(Student student){
        int grade = Math.round(student.getGrade());
        List<Student> foundList = super.get(grade);
        if (foundList != null)
            foundList.add(student);
        else {
            ArrayList<Student> newList = new ArrayList<>();
            newList.add(student);
            put(grade, newList);
        }
    }

    public Set<Map.Entry<Integer,List<Student>>> getEntries(){
        ArrayList<Map.Entry<Integer,List<Student>>> sortedStudents  = new ArrayList<>(super.entrySet());
       Collections.sort(sortedStudents, new Comparator<Map.Entry<Integer, List<Student>>>() {
           @Override
           public int compare(Map.Entry<Integer, List<Student>> o1, Map.Entry<Integer, List<Student>> o2) {
               return o1.getKey().compareTo(o2.getKey());
           }
       });
       return new TreeSet<>(sortedStudents);

    }

}
