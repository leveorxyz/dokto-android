package com.toybethsystems.dokto.ui.features.registration.complete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toybethsystems.dokto.R
import com.toybethsystems.dokto.base.theme.DoktoPrimary
import com.toybethsystems.dokto.base.theme.DoktoSecondary
import com.toybethsystems.dokto.ui.common.components.DoktoButton

@Composable
fun RegistrationCompleteScreen(
    onLoginClick: () -> Unit
) {
    return Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DoktoPrimary)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.width(100.dp).height(100.dp),
                imageVector = Icons.Filled.Done,
                contentDescription = stringResource(R.string.registration_complete),
                tint = DoktoSecondary
            )
            Text(
                text = stringResource(R.string.registration_complete),
                color = DoktoSecondary,
                textAlign = TextAlign.Center,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = stringResource(R.string.check_email_for_verification),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(30.dp))
            DoktoButton(textResourceId = R.string.login) {
                onLoginClick.invoke()
            }
        }
    }
}