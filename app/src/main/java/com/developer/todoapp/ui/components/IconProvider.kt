package com.developer.todoapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

//Se usa para retornar una lista de iconos de tipo IconContainer
class IconProvider: PreviewParameterProvider<IconContainer> {

    override val values: Sequence<IconContainer>
        get() = sequenceOf(
              IconContainer(Icons.Default.Add),
                          IconContainer(Icons.Default.MusicNote),
                          IconContainer(Icons.Default.Star),
                          IconContainer(Icons.Default.Search)
        )
}