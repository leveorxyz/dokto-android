package com.toybethsystems.dokto.base.data.preference

import com.toybethsystems.dokto.base.data.model.DoktoUser

interface AppPreference {

    var user: DoktoUser

    var isFirstTimeUser: Boolean
}