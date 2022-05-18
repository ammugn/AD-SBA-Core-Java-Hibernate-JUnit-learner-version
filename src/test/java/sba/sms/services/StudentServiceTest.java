package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class StudentServiceTest {

    static StudentService studentService;
    static Student actual;

    @BeforeAll
    static void beforeAll() {
        studentService = new StudentService();
        actual = new Student("testing@gmail.com", "test case","testpassword");
        CommandLine.addData();
    }

    @Test
    @Order(1)
    void getAllStudents() {

        List<Student> expected = new ArrayList<>(Arrays.asList(
                new Student("reema@gmail.com", "reema brown", "password"),
                new Student("annette@gmail.com", "annette allen", "password"),
                new Student("anthony@gmail.com", "anthony gallegos", "password"),
                new Student("ariadna@gmail.com", "ariadna ramirez", "password"),
                new Student("bolaji@gmail.com", "bolaji saibu", "password")
        ));

        assertThat(studentService.getAllStudents()).hasSameElementsAs(expected);

    }
    @Test
    @Order(2)
    void createStudent(){
        int size=studentService.getAllStudents().size();
       // Student actual = new Student("testing@gmail.com", "test case","testpassword");
        studentService.createStudent(actual);
        int newsize=studentService.getAllStudents().size();
        assertThat(actual).extracting(Student::getName).isEqualTo("test case");
        assertThat(newsize).isEqualTo(size+1);
    }
    @Test
    void getStudentByEmail(){
      //  Student actual = new Student("testing@gmail.com", "test case","testpassword");
       // studentService.createStudent(actual);
        studentService.getStudentByEmail("testing@gmail.com");
        assertThat(actual).extracting(Student::getName).isEqualTo("test case");
       // assertThat(studentService.getStudentByEmail("reema@gmail.com")).extracting(Student::getEmail).isEqualTo("reema@gmail.com");
    }
    @Test
    public void validateStudent(){
      //  Student actual = new Student("testing@gmail.com", "test case","testpassword");
        studentService.validateStudent("testing@gmail.com","testpassword");
        assertThat(actual).extracting(Student::getEmail).isEqualTo("testing@gmail.com");
        assertThat(actual).extracting(Student::getPassword).isEqualTo("testpassword");


    }
}