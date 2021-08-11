package io.github.vbelles.billboard.data.model

data class Section(
    val title: String,
    val header: Header?,
    val strips: List<Strip>
)