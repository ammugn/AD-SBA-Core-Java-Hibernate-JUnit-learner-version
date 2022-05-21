package sba.sms.services;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseServiceTest {
    static CourseService courseService;
    static Course testcourse;
    @BeforeAll
    static void beforeAll() {
        courseService = new CourseService();
        CommandLine.addData();
        testcourse= new Course("Testing","Ammu Nair");
    }
    @Test
    @Order(1)
    void getAllCourses() {

        List<Course> expected = new ArrayList<>(Arrays.asList(
                new Course(1,"Java", "Phillip Witkin"),
                new Course(2,"Frontend", "Kasper Kain"),
                new Course(3,"JPA", "Jafer Alhaboubi"),
                new Course(4,"Spring Framework","Phillip Witkin"),
                new Course(5,"SQL", "Phillip Witkin")
        ));

        assertThat(courseService.getAllCourses()).hasSameElementsAs(expected);

    }
    @Test
    @Order(2)
    void createCourse(){
     //   Course testcourse= new Course("Testing","Ammu Nair");
        courseService.createCourse(testcourse);
        assertThat(testcourse).extracting(Course::getName).isEqualTo("Testing");
        assertThat(courseService.getAllCourses()).isNotEmpty().hasSize(6);
    }

    @Test
    @Order(3)
    void getCourseById(){

       // courseService.createCourse(testcourse);
        courseService.getCourseById(6);
        assertThat(testcourse).extracting(Course::getName).isEqualTo("Testing");

    }
}
