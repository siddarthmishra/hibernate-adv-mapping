package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class EagerLazyDemo {

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
			 * Change the fetch type (FetchType.EAGER/FetchType.LAZY) in Instructor.java on
			 * List<Course> courses and observe the console hibernate queries
			 */

			/*
			 * For FetchType.EAGER, Instructor, InstructorDetail and Course are fetched in
			 * single query. All the data is fetched here -> session.get(Instructor.class,
			 * id1) in single database call
			 */

			/*
			 * For FetchType.LAZY, Only Instructor and InstructorDetails are fetched using
			 * single query. Course details are fetched in another query making another call
			 * to database. Course details are fetched here (on demand) ->
			 * instructor1.getCourses(). Here two database call are made
			 */

			// get the instructor from db
			int id1 = 2;
			Instructor instructor1 = session.get(Instructor.class, id1);
			System.out.println("luv2code : Instructor : " + instructor1);

			// get courses for the instructor
			System.out.println("luv2code : Courses : " + instructor1.getCourses());

			// commit transaction
			transaction.commit();

			// NEXT : LazyExceptionDemo.java

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
