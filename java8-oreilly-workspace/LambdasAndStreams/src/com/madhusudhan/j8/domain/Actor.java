package com.madhusudhan.j8.domain;

public class Actor {
	private String name = null;

	public Actor(String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Actor [name=" + name + "]";
	}
	
	
}
