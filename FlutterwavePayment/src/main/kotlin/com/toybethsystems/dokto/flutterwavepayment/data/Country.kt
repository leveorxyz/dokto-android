package com.toybethsystems.dokto.flutterwavepayment.data

sealed class Country(val code: String, val currency: String) {
    object Kenya : Country("KE", "KES")
    object Nigeria : Country("NG", "NGN")
    object Ghana : Country("GH", "GHS")
    object Uganda : Country("UG", "UGX")
    object Zambia : Country("NG", "ZMW")
    object Rwanda : Country("NG", "RWF")
    object SouthAfrica : Country("ZA", "ZAR")
    object UnitedKingdom : Country("NG", "GBP")
    object UnitedStates : Country("US", "USD")
}
