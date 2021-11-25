package com.toybeth.docto.ui.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toybeth.docto.base.theme.DoktoError
import com.toybeth.docto.base.theme.DoktoPrimary
import com.toybeth.docto.base.theme.DoktoPrimaryVariant

@Composable
fun DoktoCheckBox(
    modifier: Modifier = Modifier,
    checkedState: Boolean,
    textResourceId: Int? = null,
    textField: @Composable (() -> Unit)? = null,
    errorMessage: String? = null,
    onCheckedChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        // ---------------------- CHECK BOX ------------------- //
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState,
                colors = CheckboxDefaults.colors(
                    checkedColor = DoktoPrimaryVariant,
                    uncheckedColor = Color.LightGray,
                    checkmarkColor = Color.White
                ),
                onCheckedChange = { onCheckedChange(it) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            if (textResourceId != null) {
                Text(
                    text = stringResource(id = textResourceId),
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }
            if (textField != null) {
                textField()
            }
        }

        // ---------------------- ERROR MESSAGE ------------------- //
        AnimatedVisibility(visible = errorMessage != null) {
            errorMessage?.let {
                Text(
                    text = it,
                    color = DoktoError,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 15.dp, top = 3.dp)
                )
            }
        }
    }
}