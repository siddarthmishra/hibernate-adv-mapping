package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class GetInstructorDetailDemo {

	public static void main(String[] args) {

		// create session factory with annotated classes
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class).buildSessionFactory();

		Session session = null;
		Transaction transaction = null;

		try {
			// create session
			session = factory.getCurrentSession();

			// start a transaction
			transaction = session.beginTransaction();

			// get the instructor detail object using primary key/id
			int id = 2;
			InstructorDetail instructorDetail = session.get(InstructorDetail.class, id);

			// print the instructor detail
			System.out.println("InstructorDetail : " + instructorDetail);

			// print the associated instructor
			if (instructorDetail != null)
				System.out.println("Instructor : " + instructorDetail.getInstructor());

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
