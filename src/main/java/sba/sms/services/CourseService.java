package sba.sms.services;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;

import java.util.List;

public class CourseService implements CourseI {

    @Override
    public void createCourse(Course course) {
        Session s= HibernateUtil.getSessionFactory().openSession();
        Transaction t=null;

        try{
            t=s.beginTransaction();
            s.persist(course);
            t.commit();
        } catch (HibernateException e) {
            if (t!=null) t.rollback();
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    @Override
    public Course getCourseById(int courseId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Course course = s.get(Course.class,courseId);
        s.close();
        return course;
    }

    @Override
    public List<Course> getAllCourses() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Course> courselist = s.createQuery("from Course",Course.class).list();
        s.close();
        return courselist;
    }
}
