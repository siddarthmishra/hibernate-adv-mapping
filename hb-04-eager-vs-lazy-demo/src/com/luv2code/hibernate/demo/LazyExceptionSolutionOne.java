package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class LazyExceptionSolutionOne {

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

			/*
			 * FetchType.LAZY in Instructor.java on List<Course> courses and observe the
			 * console hibernate queries
			 */

			// NEXT : How can lazy loading issue be resolved?
			// 1. call getter method while the session is open

			// get the instructor from db
			int id1 = 2;
			Instructor instructor1 = session.get(Instructor.class, id1);
			System.out.println("luv2code : Instructor : " + instructor1);

			// get courses for the instructor
			// calling getter method while the session is open
			// courses only loaded on demand (lazy)
			System.out.println("luv2code : Courses : " + instructor1.getCourses());

			// commit transaction
			transaction.commit();
			// close the session
			session.close();
			System.out.println("\n\nluv2code : Session is now closed\n\n");

			/*
			 * trying to access courses after the session is closed in case of lazy fetch.
			 * No database call is made here since db call happened once above and data is
			 * already present. so we don't get exception
			 */
			// get courses for the instructor
			System.out.println("luv2code : Courses : " + instructor1.getCourses());

			System.out.println("luv2code : Done...");
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
