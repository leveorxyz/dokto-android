package com.toybeth.docto.data

import android.graphics.Bitmap

data class Education(
    var college: College = College(),
    var courseStudied: CourseStudied = CourseStudied(),
    var graduationYear: GraduationYear = GraduationYear(),
    var certificate: Certificate = Certificate()
)

class College : Property<String>()
class CourseStudied : Property<String>()
class GraduationYear : Property<String>()
class Certificate : Property<Bitmap>()
