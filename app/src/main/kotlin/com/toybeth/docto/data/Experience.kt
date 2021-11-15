package com.toybeth.docto.data

data class Experience(
    var establishmentName: EstablishmentName = EstablishmentName(),
    var jobTitle: JobTitle = JobTitle(),
    var startDate: StartDate = StartDate(),
    var endDate: EndDate = EndDate(),
    var jobDescription: JobDescription = JobDescription()
)

class EstablishmentName : Property<String>()
class JobTitle : Property<String>()
class StartDate : Property<String>()
class EndDate : Property<String>()
class JobDescription : Property<String>()
