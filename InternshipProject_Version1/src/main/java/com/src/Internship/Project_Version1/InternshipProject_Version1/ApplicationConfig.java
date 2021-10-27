package com.src.Internship.Project_Version1.InternshipProject_Version1;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class ApplicationConfig extends Application {
	
	@Override
	public Set<Class<?>> getClasses(){
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(com.src.Internship.Project_Version1.InternshipProject_Version1.Resource.CRUDResource.class);
		return classes;
	}

}
