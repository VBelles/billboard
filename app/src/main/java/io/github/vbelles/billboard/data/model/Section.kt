package io.github.vbelles.billboard.data.model

data class Section(
    val name: String,
    val sectionType: SectionType,
    val icon: String,
    val strips: List<Strip>
) {
    enum class SectionType { Page, People }
}