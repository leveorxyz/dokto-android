package com.toybeth.docto.data.registration

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import com.toybeth.docto.R
import com.toybeth.docto.data.Country
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject

class RegistrationRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun getCountryStateCityList(): List<Country> {
        try {
            InputStreamReader(
                context.resources.openRawResource(R.raw.country_state_city)
            ).use { reader ->
                val countryListType = object : TypeToken<ArrayList<Country>>() {}.type
                val result: List<Country> = Gson().fromJson(reader, countryListType)
                return result
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return listOf()
        }
    }
}