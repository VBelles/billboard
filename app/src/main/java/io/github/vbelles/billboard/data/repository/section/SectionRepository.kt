package io.github.vbelles.billboard.data.repository.section

import io.github.vbelles.billboard.data.dto.HeaderDto
import io.github.vbelles.billboard.data.dto.SectionDto
import io.github.vbelles.billboard.data.dto.StripDto
import io.github.vbelles.billboard.data.model.ContentType
import io.github.vbelles.billboard.data.model.Header
import io.github.vbelles.billboard.data.model.Section
import io.github.vbelles.billboard.data.model.Strip
import io.github.vbelles.billboard.data.resultOf

class SectionRepository(private val sectionApiClient: SectionApiClient) {

    suspend fun getSection(sectionId: String): Result<Section> {
        return resultOf { sectionApiClient.getSection(sectionId) }
            .map { sectionDto -> sectionDto.toSection() }
    }

    private fun SectionDto.toSection() = Section(
        title = title,
        header = header?.toHeader(),
        strips = strips.map { dto -> dto.toStrip() }
    )

    private fun HeaderDto.toHeader() = Header(
        contentType = ContentType.fromId(contentType)!!,
        source = source,
    )

    private fun StripDto.toStrip() = Strip(
        title = title,
        contentType = ContentType.fromId(contentType)!!,
        source = source,
    )
}


