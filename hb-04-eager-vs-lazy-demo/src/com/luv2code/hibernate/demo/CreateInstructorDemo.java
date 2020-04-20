package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class CreateInstructorDemo {

	public static void main(String[] args) {

		// create session factory with annotated classes
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class).addAnnotatedClass(Course.class).buildSessionFactory();

		Session session = null;
		Transaction transaction = null;

		try {
			// create the object
			Instructor tempInstructor1 = new Instructor("Chad", "Darby", "darby@luv2code");
			Instructor tempInstructor2 = new Instructor("Madhu", "Patel", "madhu@luv2code");

			InstructorDetail tempInstructorDetail1 = new InstructorDetail("http://www.luv2code.com/youtube",
					"Luv 2 code!!!");
			InstructorDetail tempInstructorDetail2 = new InstructorDetail("http://www.youtube.com/madhu.patel",
					"Guitar");

			// associate the object
			tempInstructor1.setInstructorDetail(tempInstructorDetail1);
			tempInstructor2.setInstructorDetail(tempInstructorDetail2);

			// create session
			session = factory.getCurrentSession();
			// start a transaction
			transaction = session.beginTransaction();

			// save the object
			System.out.println("Saving the Instructor object... " + tempInstructor1);
			System.out.println("Saving the Instructor object... " + tempInstructor2);

			// save the instructor
			// NOTE: this will also save the detail object because of Cascadetype.ALL
			session.save(tempInstructor1);
			session.save(tempInstructor2);

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
