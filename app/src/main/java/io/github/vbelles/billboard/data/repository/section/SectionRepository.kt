package io.github.vbelles.billboard.data.repository.section

import io.github.vbelles.billboard.data.dto.SectionDto
import io.github.vbelles.billboard.data.dto.StripDto
import io.github.vbelles.billboard.data.model.Section
import io.github.vbelles.billboard.data.model.Strip
import io.github.vbelles.billboard.data.resultOf

class SectionRepository(private val sectionApiClient: SectionApiClient, private val endpoint: String) {

    suspend fun listSections(): Result<List<Section>> {
        return resultOf {
            sectionApiClient.listSections(endpoint)
        }.map { sectionsDto ->
            sectionsDto.map { dto -> dto.toSection() }
        }
    }

    private fun SectionDto.toSection() = Section(
        name = name,
        sectionType = when (sectionType) {
            "people" -> Section.SectionType.People
            else -> Section.SectionType.Page
        },
        icon = icon,
        strips = strips.map { dto -> dto.toStrip() }
    )

    private fun StripDto.toStrip() = Strip(
        name = name,
        source = source,
    )
}


