package com.toybeth.docto.base.data.preference

import com.toybeth.docto.base.data.model.DoktoUser

interface AppPreference {

    var user: DoktoUser?

    var isFirstTimeUser: Boolean
}