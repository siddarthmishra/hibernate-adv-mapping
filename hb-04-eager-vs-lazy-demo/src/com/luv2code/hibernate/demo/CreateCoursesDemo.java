package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class CreateCoursesDemo {

	public static void main(String[] args) {

		// create session factory with annotated classes
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class).addAnnotatedClass(Course.class).buildSessionFactory();

		Session session = null;
		Transaction transaction = null;

		try {
			// create session
			session = factory.getCurrentSession();
			// start a transaction
			transaction = session.beginTransaction();

			// get the instructor from db
			int id1 = 1, id2 = 2;
			Instructor instructor1 = session.get(Instructor.class, id1);
			Instructor instructor2 = session.get(Instructor.class, id2);

			// create some courses
			Course course1 = new Course("Air Guitar - The Ultimate Guide");
			Course course2 = new Course("The Pinball Masterclass");
			Course course3 = new Course("Spring & Hibernate for Beginners");
			Course course4 = new Course("Spring Boot for Beginners");

			// add courses to instructor
			instructor1.add(course4);
			instructor1.add(course3);
			instructor2.add(course2);
			instructor2.add(course1);

			// save the courses
			session.save(course1);
			session.save(course2);
			session.save(course3);
			session.save(course4);

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
