package com.learningPath.hibernate;

import java.util.List;
import org.hibernate.Query; 
import org.hibernate.Session; 
import org.hibernate.SessionFactory; 
import org.hibernate.Transaction; 
import org.hibernate.cfg.Configuration; 
import org.hibernate.service.ServiceRegistry; 
import org.hibernate.service.ServiceRegistryBuilder; 

public class TestLauncher {
	
	SessionFactory factory; 
	 
 	public void setup(){ 
 		Configuration configuration = new Configuration(); 
 		configuration.configure(); 
 		ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder(); 
 		srBuilder.applySettings(configuration.getProperties()); 
 		ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry(); 
 		factory = configuration.buildSessionFactory(serviceRegistry); 
 	}

}
