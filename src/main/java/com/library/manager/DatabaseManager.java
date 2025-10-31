package com.library.manager;


import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DatabaseManager {
	
	private static SessionFactory sessionFactory;
	
	public static void setup() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception ex) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
	
	public static SessionFactory getSessionFactory() {	
		return sessionFactory;
	}
	
	public static void exit() {
		if (sessionFactory != null) {		
			sessionFactory.close();		
		}
	}
	
	
}
