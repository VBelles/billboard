package io.github.vbelles.billboard.ui

import io.github.vbelles.billboard.data.model.ContentType

sealed class Screen(val route: String) {

    object Movies : Screen("movies")

    object TvShows : Screen("tv_shows")

    object People : Screen("people")

    object Grid : Screen("grid/{content_type}/{id}") {
        fun parametrized(id: String, contentType: ContentType) = "grid/${contentType.id}/$id"
    }

    object Details : Screen("details/{content_type}/{id}") {
        fun parametrized(id: Int, contentType: ContentType) = "details/${contentType.id}/$id"
    }
}