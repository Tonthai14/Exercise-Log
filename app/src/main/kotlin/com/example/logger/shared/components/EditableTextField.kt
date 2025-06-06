package com.example.logger.shared.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.logger.font.cascadiaMonoFamily

@Composable
fun EditableTextField(config: EditableTextFieldConfig) {
    TextField(
        label = { Text(text = config.label, fontFamily = cascadiaMonoFamily) },
        value = config.value,
        onValueChange = { input -> config.onValueChanged(input) },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.White,
            errorContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        //trailingIcon = { Icon(Icons.Filled.Clear, "", tint = Color.Blue) },
        keyboardOptions = config.keyboardOptions,
        modifier = (config.modifier ?: Modifier)
            .padding(4.dp)
    )
}

data class EditableTextFieldConfig(
    val label: String,
    val value: String,
    val onValueChanged: (String) -> Unit,
    val keyboardOptions: KeyboardOptions,
    val modifier: Modifier? = null
)