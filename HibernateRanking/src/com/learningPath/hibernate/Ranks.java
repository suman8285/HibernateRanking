package com.learningPath.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ranks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne
	Student subject;
	
	@ManyToOne
	Student observer;
	
	@ManyToOne
	Skill skill;
	
	@Column
	Integer rating;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getSubject() {
		return subject;
	}

	public void setSubject(Student subject) {
		this.subject = subject;
	}

	public Student getObserver() {
		return observer;
	}

	public void setObserver(Student observer) {
		this.observer = observer;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
	
	public Ranks() {
		super();
	}
	
}
