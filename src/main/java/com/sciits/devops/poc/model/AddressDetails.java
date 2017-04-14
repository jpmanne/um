package com.sciits.devops.poc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sciits.devops.poc.web.model.WebAddress;

@Entity
@Table(name = "address_details")
public class AddressDetails extends BaseData {
	private static final long serialVersionUID = 6713694844288504432L;

	@Id
	@GeneratedValue
	@Column(name = "address_details_id", length = 20, nullable = false)
	private Long addressDetailsId;

	@Column(name = "address_type", length = 1, nullable = false)
	private String addressType;

	@Column(name = "address_line1", length = 150, nullable = false)
	private String addressLine1;

	@Column(name = "address_line2", length = 150, nullable = true)
	private String addressLine2;

	@Column(name = "city", length = 100, nullable = false)
	private String city;

	@Column(name = "state", length = 100, nullable = false)
	private String state;

	@Column(name = "country", length = 5, nullable = false)
	private String country;

	@Column(name = "postal_code", length = 10, nullable = false)
	private String postalCode;

	@Column(name = "status", length = 1, nullable = false)
	private String status = "1";

	@ManyToOne
	private UserDetails userDetails;

	public AddressDetails() {

	}

	/**
	 * @param addressDetailsId
	 * @param addressType
	 * @param addressLine1
	 * @param addressLine2
	 * @param city
	 * @param state
	 * @param country
	 * @param postalCode
	 * @param status
	 * @param userDetails
	 */
	public AddressDetails(Long addressDetailsId, String addressType, String addressLine1, String addressLine2,
			String city, String state, String country, String postalCode, String status, UserDetails userDetails) {
		super();
		this.addressDetailsId = addressDetailsId;
		this.addressType = addressType;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.postalCode = postalCode;
		this.status = status;
		this.userDetails = userDetails;
	}

	/**
	 * @return the addressDetailsId
	 */
	public Long getAddressDetailsId() {
		return addressDetailsId;
	}

	/**
	 * @param addressDetailsId
	 *            the addressDetailsId to set
	 */
	public void setAddressDetailsId(Long addressDetailsId) {
		this.addressDetailsId = addressDetailsId;
	}

	/**
	 * @return the addressType
	 */
	public String getAddressType() {
		return addressType;
	}

	/**
	 * @param addressType
	 *            the addressType to set
	 */
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * @param addressLine1
	 *            the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * @param addressLine2
	 *            the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode
	 *            the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the userDetails
	 */
	public UserDetails getUserDetails() {
		return userDetails;
	}

	/**
	 * @param userDetails
	 *            the userDetails to set
	 */
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public WebAddress getWebAddress() {

		WebAddress webAddress = new WebAddress();
		webAddress.setAddressDetailsId(addressDetailsId);
		webAddress.setAddressLine1(addressLine1);
		webAddress.setAddressLine2(addressLine2);
		webAddress.setAddressDetailsId(addressDetailsId);
		webAddress.setAddressType(addressType);
		webAddress.setStatus(status);
		webAddress.setState(state);
		webAddress.setState(state);
		webAddress.setCity(city);
		webAddress.setCountry(country);
		webAddress.setPostalCode(postalCode);
		return webAddress;

	}

}
