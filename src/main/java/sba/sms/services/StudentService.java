package sba.sms.services;

import jakarta.persistence.TypedQuery;
import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

@Log
public class StudentService implements StudentI {

    @Override
    public List<Student> getAllStudents() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Student> student = s.createQuery("from Student",Student.class).list();
        s.close();
        return student;
    }

    @Override
    public void createStudent(Student student) {
    Session s= HibernateUtil.getSessionFactory().openSession();
    Transaction t=null;

    try{
        t=s.beginTransaction();
        s.persist(student);
        t.commit();
    } catch (HibernateException e) {
        if (t!=null) t.rollback();
       e.printStackTrace();
    } finally {
        s.close();
    }

    }

    @Override
    public Student getStudentByEmail(String email) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Student student = s.get(Student.class,email);
        s.close();
        return student;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean isValid=false;
        try {
            tx = session.beginTransaction();
           Query q = session.createQuery("from Student where email = :email and password= :password", Student.class);
            q.setParameter("email", email);
            q.setParameter("password", password);

            if(q.uniqueResult()==null) {
                isValid = false;
            //    log.info("User id and/or  password invalid!");
            }
            else {
                isValid = true;
             //   log.info("User id and/or  password valid!");
            }
            tx.commit();
        } catch (HibernateException exception) {
            if (tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            session.close();
        }

        return isValid;
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {

        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean isEnrolled=false;
        try {
            tx = s.beginTransaction();
            Student student = s.get(Student.class, email);
            Course c = s.get(Course.class,courseId);
            //modification added to make sure student is not added twice to same course
            List<Course> lc=student.getCourses();
            for(Course course:lc)
            {

                if(course.getId()==courseId)
                { isEnrolled=true;
                    break;
                }
                else
                    isEnrolled=false;
            }
            if(isEnrolled) {
                System.out.println("Student already enrolled in this course");
            }
            else {

                c.addStudent(student);
                s.merge(c);
                tx.commit();
            }
        } catch (HibernateException exception) {
            if (tx!=null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<Course> courseList = null;
        try {
            tx = session.beginTransaction();

            NativeQuery q = session.createNativeQuery("select c.id, c.name, c.instructor from Course as c " +
                    "join student_courses as sc on c.id = sc.courses_id " +
                    "join Student as s on s.email = sc.student_email " +
                    "where s.email = :id",Course.class);
            q.setParameter("id",email);
            courseList = q.getResultList();
            tx.commit();
        } catch (HibernateException exception) {
            if (tx!=null) tx.rollback();
            exception.printStackTrace();
        } finally {
            session.close();
        }
        return courseList;
    }
    public boolean removeStudent(Student student){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = s.beginTransaction();
            if(student.getEmail() == null) throw new RuntimeException("ID equals zero");
            s.remove(student);
            tx.commit();
            return true;
        } catch (HibernateException exception) {
            if (tx!=null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }

        return false;

    }
}
