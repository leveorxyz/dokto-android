package com.toybethsystems.dokto.data.registration.model

import com.google.gson.annotations.SerializedName

data class DoctorRegistrationRequestBody(

    @SerializedName("country")
    val country: String,

    @SerializedName("specialty")
    val specialty: List<String>,

    @SerializedName("education")
    val education: List<EducationItemInDoctorRegistrationRequestBody>,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("identification_number")
    val identificationNumber: String,

    @SerializedName("city")
    val city: String? = null,

    @SerializedName("date_of_birth")
    val dateOfBirth: String,

    @SerializedName("identification_type")
    val identificationType: String,

    @SerializedName("identification_photo")
    val identificationPhoto: String,

    @SerializedName("language")
    val language: List<String>,

    @SerializedName("professional_bio")
    val professionalBio: String,

    @SerializedName("experience")
    val experience: List<ExperienceItemInDoctorRegistrationRequestBody>,

    @SerializedName("zip_code")
    val zipCode: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("street")
    val street: String?,

    @SerializedName("contact_no")
    val contactNo: String,

    @SerializedName("state")
    val state: String?,

    @SerializedName("twitter_url")
    val twitterUrl: String? = null,

    @SerializedName("email")
    val email: String,

    @SerializedName("facebook_url")
    val facebookUrl: String? = null,

    @SerializedName("accepted_insurance")
    val acceptedInsurance: List<String>,

    @SerializedName("profile_photo")
    val profilePhoto: String,

    @SerializedName("license_file")
    val licenseFile: String,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("awards")
    val awards: String?,

    @SerializedName("linkedin_url")
    val linkedinUrl: String? = null,

//    @SerializedName("username")
//    val username: String? = null
)

data class ExperienceItemInDoctorRegistrationRequestBody(

	@SerializedName("end_date")
	val endDate: String? = null,

	@SerializedName("job_description")
	val jobDescription: String,

	@SerializedName("job_title")
	val jobTitle: String,

	@SerializedName("establishment_name")
	val establishmentName: String,

	@SerializedName("start_date")
	val startDate: String
)

data class EducationItemInDoctorRegistrationRequestBody(

	@SerializedName("college")
	val college: String,

	@SerializedName("year")
	val year: String,

	@SerializedName("certificate")
	val certificate: String,

	@SerializedName("course")
	val course: String,
)
