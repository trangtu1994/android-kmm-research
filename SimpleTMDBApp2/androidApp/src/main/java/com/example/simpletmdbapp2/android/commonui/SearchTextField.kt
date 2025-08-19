package com.example.simpletmdbapp2.android.commonui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    placeHolderText: String = "",
    text: String,
    onSearchClicked: (String) -> Unit,
    onTextChanged: (String) -> Unit,
) {
    Row(
        modifier
            .height(55.dp)
            .fillMaxWidth()
            .shadow(shape = CircleShape, elevation = 5.dp, clip = true),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        Icon(Icons.Default.Search, "", Modifier.clickable {
            onSearchClicked.invoke(text)
        })
        TextField(
            text,
            onValueChange = {
                onTextChanged.invoke(it)
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            singleLine = true,
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClicked.invoke(text)
            }),
            modifier = Modifier.weight(2f, fill = true),
            placeholder = { Text(placeHolderText) },
        )
        if (text.isNotBlank()) {
            Icon(
                Icons.Filled.Clear, "",
                modifier = Modifier.weight(0.5f).clickable {
                    onSearchClicked.invoke("")
                })
        }
    }
}
