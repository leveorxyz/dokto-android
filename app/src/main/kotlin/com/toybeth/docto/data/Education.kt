package com.toybeth.docto.data

import android.graphics.Bitmap
import android.net.Uri

data class Education(
    var college: College = College(),
    var courseStudied: CourseStudied = CourseStudied(),
    var graduationYear: GraduationYear = GraduationYear(),
    var certificate: Certificate = Certificate(),
    var certificateUri: CertificateUri = CertificateUri()
)

class College : Property<String>()
class CourseStudied : Property<String>()
class GraduationYear : Property<String>()
class Certificate : Property<Bitmap>()
class CertificateUri: Property<Uri>()
