package com.toybethsystems.dokto.data.registration.model

import com.google.gson.annotations.SerializedName

data class PatientRegistrationRequestBody(

	@SerializedName("referring_doctor_full_name")
	val referringDoctorFullName: String? = null,

	@SerializedName("insurance_policy_holder_name")
	val insurancePolicyHolderName: String? = null,

	@SerializedName("profile_photo")
	val profilePhoto: String,

	@SerializedName("gender")
	val gender: String,

	@SerializedName("identification_number")
	val identificationNumber: String,

	@SerializedName("identification_photo")
	val identificationPhoto: String,

	@SerializedName("city")
	val city: String? = null,

	@SerializedName("date_of_birth")
	val dateOfBirth: String,

	@SerializedName("referring_doctor_address")
	val referringDoctorAddress: String? = null,

	@SerializedName("identification_type")
	val identificationType: String,

	@SerializedName("insurance_number")
	val insuranceNumber: String? = null,

	@SerializedName("zip_code")
	val zipCode: String,

	@SerializedName("insurance_type")
	val insuranceType: String,

	@SerializedName("password")
	val password: String,

	@SerializedName("full_name")
	val fullName: String,

	@SerializedName("street")
	val street: String,

	@SerializedName("contact_no")
	val contactNo: String,

	@SerializedName("social_security_number")
	val socialSecurityNumber: String,

	@SerializedName("state")
	val state: String,

	@SerializedName("insurance_name")
	val insuranceName: String? = null,

	@SerializedName("referring_doctor_phone_number")
	val referringDoctorPhoneNumber: String? = null,

	@SerializedName("email")
	val email: String
)
