package com.toybethsystems.dokto.data.main

import com.toybethsystems.dokto.base.data.preference.AppPreference
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val preference: AppPreference
) : MainRepository {


}