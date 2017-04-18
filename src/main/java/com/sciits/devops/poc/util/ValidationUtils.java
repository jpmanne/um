
package com.sciits.devops.poc.util;


import java.util.List;

import org.springframework.util.StringUtils;

import com.sciits.devops.poc.model.UserDetails;

public class ValidationUtils {

	private static final ValidationResponse SUCCESS = new ValidationResponse(true, null);

	//========================================================================
	
	public static ValidationResponse validateUser(UserDetails user) {

		if (StringUtils.isEmpty(user.getEmail())) {
			return new ValidationResponse(false, "User Email is empty");
		} else if (user.getPassword() == null ) {
			return new ValidationResponse(false, "Password is empty");
		} else if (user.getUserName() == null) {
			return new ValidationResponse(false, "user name is null");
		} else if (user.getFirstName() == null) {
			return new ValidationResponse(false, "first name is null");
		}else if (user.getLastName() == null) {
			return new ValidationResponse(false, "last name is null");
		}/*else if (user.getGender() == null) {
			return new ValidationResponse(false, "gender is null");
		}*/ else if (user.getPhoneNumber() == null) {
			return new ValidationResponse(false, "phone number is null");
		}
		return SUCCESS;
	}

	//========================================================================
	
	public static ValidationResponse validateLoginUser(UserDetails user) {
		if (StringUtils.isEmpty(user.getUserName())) {
			return new ValidationResponse(false, "User name is empty");
		} else if (user.getPassword() == null ) {
			return new ValidationResponse(false, "Password is empty");
		}
		return SUCCESS;
	}

	//========================================================================
	
	/*public static ValidationResponse validateVendorEmails(List<VendorDetails> vendors, VendorDetails vendorDetails) {
		
		for(VendorDetails vendorListItem : vendors){

			if(vendorListItem.getVendorDetailsId()!=vendorDetails.getVendorDetailsId()) {

				if( vendorDetails.getToEmail()!=null && vendorDetails.getToEmail().equals(vendorListItem.getToEmail())){
									
					return new ValidationResponse(false, "To Email is already exists");
				}
			}
		}
		return SUCCESS;
	}*/

	//========================================================================
}	

