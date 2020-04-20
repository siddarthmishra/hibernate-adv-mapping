package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;
import com.luv2code.hibernate.demo.entity.Review;
import com.luv2code.hibernate.demo.entity.Student;

public class CreateCourseAndStudentsDemo {

	public static void main(String[] args) {

		// create session factory with annotated classes
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class).addAnnotatedClass(Course.class)
				.addAnnotatedClass(Review.class).addAnnotatedClass(Student.class).buildSessionFactory();

		Session session = null;
		Transaction transaction = null;

		try {
			// create session
			session = factory.getCurrentSession();
			// start a transaction
			transaction = session.beginTransaction();

			// create a course
			Course tempCourse = new Course("Pacman - How to Score One Million Points");

			// save the course
			System.out.println("Saving the course...");
			session.save(tempCourse);
			System.out.println("Saved the course : " + tempCourse);

			// create the students
			Student s1 = new Student("John", "Doe", "john@luv2code.com", null);
			Student s2 = new Student("Mary", "Public", "mary@luv2code.com", null);

			// add the students to the course
			tempCourse.add(s1);
			tempCourse.add(s2);

			// save the students
			System.out.println("Saving students...");
			session.save(s1);
			session.save(s2);
			System.out.println("Saved students : " + tempCourse.getStudents());

			// commit transaction
			transaction.commit();

			System.out.println("Done...");
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			System.out.println("Finally...");
			session.close();
			factory.close();
		}
	}
}
