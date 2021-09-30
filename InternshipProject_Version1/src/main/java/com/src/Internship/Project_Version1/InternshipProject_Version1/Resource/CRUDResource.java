package com.src.Internship.Project_Version1.InternshipProject_Version1.Resource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

import com.src.Internship.Project.Internship_Project.Entity.Request;
import com.src.Internship.Project.Internship_Project.Entity.RequestBody;
import com.src.Internship.Project_Version1.Internship_Project_Version1.Logic.UserQuestionnaireLogic;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("OkinawaInternshipProject")
public class CRUDResource {
	
	@Inject
	UserQuestionnaireLogic Questionnaire = new UserQuestionnaireLogic();
	
	@POST
    @Path("/UserQuestionnaire")
    public Response UserQuestionnaire(@BeanParam Request request, RequestBody body) {
    	return Questionnaire.CRUDService(request, body);
    }

}
