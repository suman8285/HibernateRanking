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
 	
 	public Skill searchSkill(Session session, String name){ 
 		Query query = session.createQuery("from Skill s where s.name=:name"); 
 		query.setParameter("name", name); 
 		Skill skill = (Skill) query.uniqueResult(); 
 		return skill; 
 	}
 	
 	public Skill saveSkill(Session session, String name){ 
 		Skill skill = searchSkill(session,name);  
 		if (skill == null){ 
 			skill = new Skill(); 
 			skill.setName(name); 
 			session.save(skill); 
 		} 
 		return skill; 
 	}
 	
 	public Student searchStudent(Session session, String name){ 
 		Query query = session.createQuery("from Student s where s.name=:name"); 
 		query.setParameter("name", name); 
 		Student student = (Student) query.uniqueResult(); 
 		return student; 
 	} 
 	
 	public Student saveStudent(Session session, String name){ 
 		Student student = searchStudent(session,name);  
 		if (student == null){ 
 			student = new Student(); 
 			student.setName(name); 
 			session.save(student); 
 		} 
 		return student; 
 	}
 	
 	public void createData(Session session, String subjectName, String observerName, String skillName, int rank){
 		Student subject = saveStudent(session,subjectName);
 		Student observer = saveStudent(session,observerName);
 		Skill skill = saveSkill(session,skillName);
 		Ranks ranking = new Ranks();
 		ranking.setSubject(subject);
 		ranking.setObserver(observer);
 		ranking.setSkill(skill);
 		ranking.setRating(rank);
 		session.save(ranking);
 		}
 	
 	public void updateRank(Session session, String subjectName, String observerName, String skillName, int newRating){
 		Query query = session.createQuery("from Ranks r "
 		+ "where r.subject.name=:subject and "
 		+ "r.observer.name=:observer and "
 		+ "r.skill.name=:skill");
 		query.setString("subject", subjectName);
 		query.setString("observer",observerName);
 		query.setString("skill", skillName);
 		Ranks ranking = (Ranks) query.uniqueResult();
 		if(ranking == null){
 		System.out.println("No DATA AVAILABLE TO UPDATE");
 		}
 		ranking.setRating(newRating);
	}
 	
 	public void deleteRank(Session session, String subjectName, String observerName, String skillName){
	 		Query query = session.createQuery("from Ranks r "
	 		+ "where r.subject.name=:subject and "
	 		+ "r.observer.name=:observer and "
	 		+ "r.skill.name=:skill");
	 		query.setString("subject", subjectName);
	 		query.setString("observer",observerName);
	 		query.setString("skill", skillName);
	 		Ranks ranking = (Ranks) query.uniqueResult();
	 		if(ranking==null){
	 			System.out.println("NO DATA AVAILABLE TO DELETE");
	 		}
	 		else{
	 			session.delete(ranking);
	 			System.out.println("SUCCESSFULLY DELETED RANK OF "+subjectName);
 	     	}
		}
 	
 	public void findAverage(Session session,String subjectName,String skillName){
			Query query = session.createQuery("Select avg(r.rating) from Ranks r "
						  + "where r.subject.name=:subject and "
						  + "r.skill.name=:skill");
			query.setString("subject", subjectName);
			query.setString("skill", skillName);
			List<Integer> average=(List<Integer>) query.list();
			if(average.get(0)==null){
				System.out.println("NO RATING AVAILABLE FOR " +subjectName+" ON"+skillName);
			}
			else{
				System.out.println("AVERAGE OF "+subjectName+" ON THE SKILL "+skillName+" IS "+average.get(0));
			}
 	}
 	
 	public void top(Session session,String skillName){
			Query query = session.createQuery("Select r.subject from Ranks r where r.skill.name=:skill and r.rating=(Select max(ra.rating) from Ranking ra where ra.skill.name=:skill )");
			query.setString("skill", skillName);
			List<Student> topper=(List<Student>) query.list();
			if(topper.size()==0){
				System.out.println("NO DATA FOR "+skillName+" skill");
			}
			else{
				System.out.println("TOPPER FOR THE SKILL "+skillName.toUpperCase()+" IS "+topper.get(0).getName().toUpperCase());
			}
	}
 	
 	public void sortStudents(Session session,String skillName){
			Query query = session.createQuery("Select DISTINCT(r.subject.name) from Ranking r where r.skill.name=:skill order by(r.subject.name)");
            query.setString("skill", skillName);
            List<String> srt=(List<String>) query.list();
			if(srt.size()==0){
				System.out.println("NO DATA FOR "+skillName+" skill");
			}else{
				for(String value:srt){
					System.out.println(value);
			    }
			}
	}
 	
 	public static void main(String[] args) { 
 		
 		TestLauncher testLauncher = new TestLauncher(); 
 		testLauncher.setup(); 
 		
 		 
 		Session session = testLauncher.factory.openSession(); 
 		Transaction tx = session.beginTransaction();
 		
 		Student s1 = testLauncher.saveStudent(session, "Suman Sharma"); 
 		Student s2 = testLauncher.saveStudent(session, "Santosh Pandey");
 		
 		Skill skill = testLauncher.saveSkill(session, "Spring");
 		
 		Ranks ranking = new Ranks(); 
 		ranking.setSubject(s1); 
 		ranking.setObserver(s2); 
 		ranking.setSkill(skill); 
 		ranking.setRating(7); 
 		session.save(ranking);
 		
 		//Add ranks
 		testLauncher.createData(session, "Suman","sharma","Python",5);
 		testLauncher.createData(session, "Kumar","swamy","LINIX",9);
 		testLauncher.createData(session, "Ajit","sahu","SPRING",8);
 		testLauncher.createData(session, "suman","murthy","Python",12);
 		testLauncher.createData(session, "subba","rao","Python",9);
 		testLauncher.createData(session, "Amit","dobe","Python",8);
 		testLauncher.createData(session, "Daksha","yagna","Python",14);
 		testLauncher.createData(session, "vasnath","bhandari","SPRING",7);
 		testLauncher.createData(session, "sharath","kumar","Python",8);
 		
 	    // UPDATING RATING assigned by Suman Sharma to Santosh Pandey
 		testLauncher.changeRank(session, "Suman Sharma","Santosh Pandey","Spring",12);
 		
 		
 		
 		//REMOVING RANK (Both success and failure case tested) 
 		testLauncher.deleteRank(session,"suman","murthy","Python");
 		testLauncher.deleteRank(session,"Amit","shankar","Python");

 		
 		//GETTING AVERAGE
 		testLauncher.findAverage(session,"suman","Python"); //(Success case tested)
 		testLauncher.findAverage(session,"Alexander","Python"); //(Failure case tested)
 		
 		//FINDING TOPPER
 		testLauncher.top(session,"Python");  //(Success case tested)
 		testLauncher.top(session,"Angular");  //(Failure case tested)
 		
 		//SORTING STUDENTS
 		testLauncher.sortStudents(session,"Python");   //(sucess case tested)
 		testLauncher.sortStudents(session,"SPRING");  // (Failure case tested)
 	 		
 	 		
 	
 		
 		tx.commit();
 	    session.close();
 		
 	}

}
