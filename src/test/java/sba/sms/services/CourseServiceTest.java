package sba.sms.services;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import sba.sms.utils.CommandLine;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseServiceTest {
    static CourseService courseService;

    @BeforeAll
    static void beforeAll() {
        courseService = new CourseService();
        CommandLine.addData();
    }


}
