package com.toybethsystems.dokto.ui.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.toybethsystems.dokto.R

@Composable
fun DoktoDropDownMenu(
    modifier: Modifier = Modifier,
    suggestions: List<String>,
    textFieldValue: String,
    hintResourceId: Int,
    labelResourceId: Int? = null,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    errorMessage: String? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val trailingIcon = if (expanded) Icons.Filled.ArrowDropUp
    else Icons.Filled.ArrowDropDown

    Column(
        modifier = modifier.then(Modifier.fillMaxWidth())
    ) {


        DoktoTextFiled(
            modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                textFieldSize = layoutCoordinates.size.toSize()
            },
            textFieldValue = textFieldValue,
            hintResourceId = hintResourceId,
            labelResourceId = labelResourceId,
            onValueChange = { },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = stringResource(id = R.string.select),
                        tint = Color.Black
                    )
                }
            },
            onClick = { expanded = !expanded },
            singleLine = singleLine,
            errorMessage = errorMessage
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(0.dp, 8.dp),
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = label
                        onValueChange(selectedText)
                        expanded = false
                    }
                ) {
                    Text(text = label)
                }
            }
        }
    }
}