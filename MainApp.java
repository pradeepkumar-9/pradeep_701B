import java.io.*;
import java.util.*;

// Part A: Sum of Integers Using Autoboxing and Unboxing
class SumIntegers {
    public void calculateSum() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();

        System.out.println("Enter integers separated by space:");
        String input = sc.nextLine();
        String[] strNumbers = input.split(" ");

        for (String str : strNumbers) {
            int num = Integer.parseInt(str); // parse string to int
            numbers.add(num); // autoboxing
        }

        int sum = 0;
        for (Integer number : numbers) {
            sum += number; // unboxing
        }

        System.out.println("Sum of integers: " + sum);
    }
}

// Part B: Serialization and Deserialization of a Student Object
class Student implements Serializable {
    int studentID;
    String name;
    double grade;

    public Student(int studentID, String name, double grade) {
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
    }

    public void display() {
        System.out.println("Student ID: " + studentID + ", Name: " + name + ", Grade: " + grade);
    }
}

class StudentSerialization {
    private static final String FILE_NAME = "student.dat";

    public void serializeStudent() {
        Student student = new Student(101, "Alice", 9.5);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(student);
            System.out.println("Student object serialized to " + FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserializeStudent() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Student student = (Student) ois.readObject();
            System.out.println("Deserialized Student:");
            student.display();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

// Part C: Menu-Based Employee Management System
class Employee implements Serializable {
    int id;
    String name;
    String designation;
    double salary;

    public Employee(int id, String name, String designation, double salary) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.salary = salary;
    }

    public void display() {
        System.out.println("ID: " + id + ", Name: " + name +
                ", Designation: " + designation + ", Salary: " + salary);
    }
}

class EmployeeManagement {
    private static final String FILE_NAME = "employees.dat";

    public void menu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Employee Management System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addEmployee(sc);
                    break;
                case 2:
                    displayEmployees();
                    break;
                case 3:
                    System.out.println("Exiting Employee Management...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 3);
    }

    private void addEmployee(Scanner sc) {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Designation: ");
        String designation = sc.nextLine();
        System.out.print("Enter Salary: ");
        double salary = sc.nextDouble();

        Employee emp = new Employee(id, name, designation, salary);

        try (FileOutputStream fos = new FileOutputStream(FILE_NAME, true);
             AppendableObjectOutputStream oos = new AppendableObjectOutputStream(fos)) {
            oos.writeObject(emp);
            System.out.println("Employee added successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayEmployees() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("\n--- Employee Records ---");
            while (true) {
                Employee emp = (Employee) ois.readObject();
                emp.display();
            }
        } catch (EOFException e) {
            // End of file reached
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No employee records found.");
        }
    }

    // Helper class for appending objects
    private static class AppendableObjectOutputStream extends ObjectOutputStream {
        public AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }
}

// Main Class to call all parts
public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("--- Part A: Sum of Integers ---");
        SumIntegers sumObj = new SumIntegers();
        sumObj.calculateSum();

        System.out.println("\n--- Part B: Student Serialization ---");
        StudentSerialization studentObj = new StudentSerialization();
        studentObj.serializeStudent();
        studentObj.deserializeStudent();

        System.out.println("\n--- Part C: Employee Management ---");
        EmployeeManagement empObj = new EmployeeManagement();
        empObj.menu();

        sc.close();
    }
}