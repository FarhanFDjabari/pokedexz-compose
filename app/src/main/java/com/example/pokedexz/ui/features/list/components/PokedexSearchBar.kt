package com.example.pokedexz.ui.features.list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexz.ui.theme.PokedexZTheme

@Composable
fun PokedexSearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    focusManager: FocusManager = LocalFocusManager.current,
    onSearch: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf( hint != "")
    }

    Box(
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            maxLines = 1,
            singleLine = true,
            shape = CircleShape,
            placeholder = {
                if (isHintDisplayed) {
                    Text(
                        text = hint,
                        color = MaterialTheme.colors.secondary
                    )
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "search",
                    modifier = Modifier.padding(5.dp,0.dp,0.dp,0.dp)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = MaterialTheme.colors.primary,
                unfocusedIndicatorColor = Color.LightGray,
                leadingIconColor = MaterialTheme.colors.primary,
                disabledLeadingIconColor = Color.LightGray,
                backgroundColor = MaterialTheme.colors.background
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
                autoCorrect = false,
                capitalization = KeyboardCapitalization.None
            ),
            keyboardActions = KeyboardActions(onSearch = {
                focusManager.clearFocus()
                onSearch(text)
            }),
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            text = ""
                            onSearch(text)
                        }
                    ) {
                        Icon(
                            Icons.Rounded.Close,
                            tint = MaterialTheme.colors.primary,
                            contentDescription = "erase field button"
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .onFocusChanged {
                    if (!it.hasFocus) focusManager.clearFocus()
                    isHintDisplayed = it.hasFocus != true && text.isEmpty()
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PokedexSearchBarPreview() {
    PokedexZTheme {
        PokedexSearchBar(
            hint = "Search Pokemon",
            onSearch = {}
        )
    }
}