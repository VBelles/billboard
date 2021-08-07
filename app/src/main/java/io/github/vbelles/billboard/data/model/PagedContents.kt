package io.github.vbelles.billboard.data.model

data class PagedContents(
    val contents: List<Content>,
    val page: Int,
    val nextPage: Int?,
)