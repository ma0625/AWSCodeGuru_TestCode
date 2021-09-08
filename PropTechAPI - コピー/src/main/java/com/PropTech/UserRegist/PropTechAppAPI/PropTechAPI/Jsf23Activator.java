package com.PropTech.UserRegist.PropTechAppAPI.PropTechAPI;

import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.PropTech.AWSCognito.Logic.AWSCognitoLogic;
import com.PropTech.UserRegist.PropTechAppAPI.PropTechAPI.EntityDTO.Request;
import com.PropTech.UserRegist.PropTechAppAPI.PropTechAPI.EntityDTO.RequestBody;
import com.PropTech.UserRegist.PropTechAppAPI.PropTechAPI.Logic.UserRegistrationLogic;

/**This bean is required to activate JSF 2.3.
 * See https://github.com/eclipse-ee4j/mojarra#user-content-activating-cdi-in-jakarta-faces-30
 * 
 * Remove this class if you don't need JSF.
 */
@RequestScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("PropTechAPI")
public class Jsf23Activator {
	
	@Inject
	UserRegistrationLogic Regist = new UserRegistrationLogic();
	
	AWSCognitoLogic Logic = new AWSCognitoLogic();
	
	@POST
	@Path("/NewRegist")
	public Response UserRegist(@BeanParam Request request, RequestBody body) {
		return Regist.CRUDService(request, body);
	}
	
	@GET
	@Path("/AWSCognito")
	public Response AWSCognito() {
		return Logic.CRUDServiceAWS();
	}
}