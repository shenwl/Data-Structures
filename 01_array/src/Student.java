/**
 * 测试类
 */
public class Student {
    private String name;
    private int score;

    public Student(String studentName, int studentScore) {
        name = studentName;
        score = studentScore;
    }

    @Override
    public String toString() {
        return String.format("Student(name: %s, score: %d)", name, score);
    }

    public static void main(String[] args) {
        Array<Student> arr = new Array<Student>();
        arr.addLast(new Student("Alice", 60));
        arr.addLast(new Student("Bob", 90));
        arr.addLast(new Student("Allen", 100));
        System.out.println(arr);
    }
}
