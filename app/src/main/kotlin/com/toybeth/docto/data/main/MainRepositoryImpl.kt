package com.toybeth.docto.data.main

import com.toybeth.docto.base.data.preference.AppPreference
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val preference: AppPreference
) : MainRepository {


}