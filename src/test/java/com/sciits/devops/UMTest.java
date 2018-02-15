package com.sciits.devops;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.OneToOne;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.google.gson.Gson;
import com.sciits.devops.poc.base.SessionBase;
import com.sciits.devops.poc.base.UMDao;
import com.sciits.devops.poc.controller.UserController;
import com.sciits.devops.poc.model.RoleDetails;
import com.sciits.devops.poc.model.UserAuthCodeDetails;
import com.sciits.devops.poc.model.UserDetails;
import com.sciits.devops.poc.model.UserInfo;

import jdk.nashorn.internal.ir.annotations.Ignore;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/api-servlet.xml")
@ActiveProfiles("local")
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UMTest  extends UMDao<Serializable, SessionBase>{
	
	private MockMvc mockMvc;
	public static String  superUserAuthCode;
	@Autowired(required = true)
	@InjectMocks
	UserController userController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController)
				// .addFilters(new CORSFilter())
				.build();
		
		
	}
	
	
	
	
	
	/*public void login(String roleCode) throws Exception {
		String userRoleCode = roleCode;
		
		UserInfo userInfo=new UserInfo();
		Session session = getSession();
		Criteria roleCriteria = session.createCriteria(RoleDetails.class);
		roleCriteria.add(Restrictions.eq("role", userRoleCode));
		RoleDetails role = (RoleDetails) roleCriteria.uniqueResult();

		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.add(Restrictions.eq("roleDetails.roleId", role.getRoleId()));
		criteria.add(Restrictions.eq("Status", "Y"));
		List list = criteria.list();
		if (list.size() != 0) {

			for (int i = 0; i <= list.size() - 1;) {

				RoleDetails roleDetails = (RoleDetails) list.get(i);
				Criteria userDetails = session.createCriteria(UserDetails.class);
				userDetails.add(Restrictions.eq("roleId", roleDetails.getRoleId()));
 
				
				if (userDetails != null) {
					
					
					
					
						mockMvc.perform(
								post("/api/user/login?roleId=4").contentType(MediaType.APPLICATION_JSON).content(json))
								.andExpect(status().isOk())
								.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
								.andExpect(jsonPath("$.success", is(true)))
								.andExpect(jsonPath("$.message", is("User login is successful")))
								.andExpect(jsonPath("$.responseCode", is("200")))
								.andExpect(jsonPath("responsePayload.roleId", is(role.getRoleId().intValue())))
								.andExpect(jsonPath("responsePayload.userId",
										is(userLoginTbl.getUserTblByUserId().getUserId().intValue())))
								.andExpect(jsonPath("responsePayload.firstName",
										is(userLoginTbl.getUserTblByUserId().getFirstName())))
								.andExpect(jsonPath("responsePayload.lastName",
										is(userLoginTbl.getUserTblByUserId().getLastName())));
						UpdateUserValidationsTest.userId = userLoginTbl.getUserTblByUserId().getUserId();
						break;

					} else {
						i++;
					}

				}
			}

		}

	}
*/
	// ================================================================ ROCA
	// SUPER USER
	// LOGIN===============================================================================================
	/*@Ignore
	@Test
	public void userLogin() throws Exception {
		String roleCode = "SUPERUSER";
		//login(roleCode);
		Session session = getSession();
		Criteria authCodeCriteria = session.createCriteria(UserAuthCodeDetails.class);
		authCodeCriteria.add(Restrictions.eq("status", "Y"));
		List userAuthCodelist = authCodeCriteria.list();
		UserAuthCodeDetails userAuthCode = (UserAuthCodeDetails) userAuthCodelist.get(userAuthCodelist.size() - 1);
		superUserAuthCode = userAuthCode.getAuthCode();

	}*/
	
	
	public UserDetails createUser()  throws Exception{
		Session session=null;
		UserDetails userDetails= new UserDetails();
		userDetails.setUserDetailsId(3l);
		userDetails.setFirstName("User");
		userDetails.setMiddleName("Name");
		userDetails.setLastName("One");
		userDetails.setUserName("UserName");
		userDetails.setFilePath("");
		Date date=new Date(1991, 10, 18);
		userDetails.setDateOfBirth(date);
		userDetails.setEmail("user@gmail.com");
		userDetails.setAddressLine1("Hyderabad");
		userDetails.setAddressLine2("Tirupathi");
		userDetails.setCity("Hyderabad");
		userDetails.setCountry("India");
		userDetails.setCcAddress("wqerrh");
		userDetails.setPhoneNumber("99999999");
		userDetails.setPassword("user123@");
		userDetails.setPostalCode("500072");
		userDetails.setState("AP");
		userDetails.setStatus("1");
		userDetails.setIsDefaultPasswordChanged("0");
		userDetails.setVZID("213vhgf");
		 	 session = getSession();
		Criteria roleCriteria = session.createCriteria(RoleDetails.class);
	    roleCriteria.add(Restrictions.eq("roleCode", "SUPERUSER"));
	   RoleDetails role = (RoleDetails) roleCriteria.uniqueResult();
	    if(role != null){
	    	RoleDetails role1= new RoleDetails();
		role1.setRoleId(role.getRoleId());
		userDetails.setRoleDetails(role1);
	    }
	    return userDetails;
	
	}
	
	/*@Test
	public void AddUser()  throws Exception{
		Session session=null;
		UserDetails userDetails= new UserDetails();
		userDetails.setUserDetailsId(3l);
		userDetails.setFirstName("User");
		userDetails.setMiddleName("Name");
		userDetails.setLastName("One");
		userDetails.setUserName("UserName");
		userDetails.setFilePath("");
		Date date=new Date(1991, 10, 18);
		userDetails.setDateOfBirth(date);
		userDetails.setEmail("user@gmail.com");
		userDetails.setAddressLine1("Hyderabad");
		userDetails.setAddressLine2("Tirupathi");
		userDetails.setCity("Hyderabad");
		userDetails.setCountry("India");
		userDetails.setCcAddress("wqerrh");
		userDetails.setPhoneNumber("99999999");
		userDetails.setPassword("user123@");
		userDetails.setPostalCode("500072");
		userDetails.setState("AP");
		userDetails.setStatus("1");
		userDetails.setIsDefaultPasswordChanged("0");
		userDetails.setVZID("213vhgf");
		 	 session = getSession();
		Criteria roleCriteria = session.createCriteria(RoleDetails.class);
	    roleCriteria.add(Restrictions.eq("roleCode", "SUPERUSER"));
	   RoleDetails role = (RoleDetails) roleCriteria.uniqueResult();
	    if(role != null){
	    	RoleDetails role1= new RoleDetails();
		role1.setRoleId(role.getRoleId());
		userDetails.setRoleDetails(role1);
	    }
		Random ran = new Random();
		int code = (1000 + ran.nextInt(999));

		String userName ="SUPERUSER";
				userName = userName+code;
				Criteria userNameCriteria = session.createCriteria(UserInfo.class);
				userNameCriteria.add(Restrictions.eq("username", userName));
				List userNamesList = userNameCriteria.list();
				if(userNamesList.size() != 0){
					UserInfo userNameCheck = (UserInfo) userNamesList.get(0);
			
					userDetails.setUserName(userNameCheck.getUserName()+"s");
					}
					else{
					userDetails.setUserName(userName);
					}
		userDetails.setPassword("password");
		Gson gson = new Gson();
		String json = gson.toJson(userDetails);
		mockMvc.perform(post("/api/user/signup")
				.header("authcode", "wRu9sHe1hBf91493120394215")
		        .contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.success", is(true)))
				.andExpect(jsonPath("$.message", is("User registration is successful")))
				.andExpect(jsonPath("$.responseCode", is("200")));
		//return userDetails;
	}*/
	@Test
	public void getUSer() throws Exception
	{
		
		Session session=null;
		session=getSession();
		Criteria criteria=session.createCriteria(UserDetails.class);
		UserDetails userDetails=(UserDetails)criteria.list().get(0);
	    Long userDetailsId=	userDetails.getUserDetailsId();
		
		mockMvc.perform(get("/api/user/get?userDetailsId="+27)
				.header("authcode", "wRu9sHe1hBf91493120394215"));
				//.andExpect(status().isOk())
				//.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				//.andExpect(jsonPath("$.success", is(true)))
				//.andExpect(jsonPath("$.message", is("User details")));
			//	.andExpect(jsonPath("$.responseCode", is("400")));
	}
	/*@Test
	public void getUserWithNullUserDetails() throws Exception
	{
		Session session=null;
		session=getSession();
		Criteria criteria=session.createCriteria(UserDetails.class);
		criteria.add(Restrictions.eq("state","AndhraPradesh"));
		UserDetails userDetails=(UserDetails)criteria.uniqueResult();
	   Long userDetailsId=null;
	    if(userDetails==null){
	    mockMvc.perform(get("/api/user/get?userDetailsId="+userDetailsId)
				.header("authcode", "wRu9sHe1hBf91493120394215"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.success", is(true)))
				.andExpect(jsonPath("$.message", is("User details")))
				.andExpect(jsonPath("$.responseCode", is("400")));
	    }
	}
	@Test
	public void updateUser() throws Exception
	{
		Session session=null;
		session=getSession();
		Criteria criteria=session.createCriteria(UserDetails.class);
		UserDetails userDetails=(UserDetails)criteria.list().get(2);
	    Long userDetailsId=	userDetails.getUserDetailsId();
		userDetails.setAddressLine1("vizag");
		userDetails.setPostalCode("899625476");
		Gson gson=new Gson();
		String json=gson.toJson(userDetails);
		mockMvc.perform(post("/api/user/update")
				.header("authcode", "wRu9sHe1hBf91493120394215")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.success", is(true)))
				.andExpect(jsonPath("$.message", is("User is updated successfully")));
				//.andExpect(jsonPath("$.responseCode", is("400")));
	}
	*/

}
