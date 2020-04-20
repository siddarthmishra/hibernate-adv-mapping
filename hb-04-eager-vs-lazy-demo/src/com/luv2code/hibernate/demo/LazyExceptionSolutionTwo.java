package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class LazyExceptionSolutionTwo {

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
			// 2. Query with HQL(Hibernate Query Language) JOIN FETCH

			// get the instructor from db
			int id1 = 2;
			// Instructor instructor1 = session.get(Instructor.class, id1);
			Query<Instructor> query = session.createQuery(
					"SELECT i FROM Instructor i JOIN FETCH i.courses WHERE i.id = :theInstructorId", Instructor.class);

			// set parameter on query
			query.setParameter("theInstructorId", id1);

			// execute query and get the instructor
			Instructor instructor1 = query.getSingleResult();

			System.out.println("luv2code : Instructor : " + instructor1);

			// commit transaction
			transaction.commit();
			// close the session
			session.close();
			System.out.println("\n\nluv2code : Session is now closed\n\n");

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
