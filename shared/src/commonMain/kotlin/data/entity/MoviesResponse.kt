package data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    @SerialName("page") val page: Int,
    @SerialName("total_results") val totalResults: Int,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("results") val results: List<MovieResponse>
)

@Serializable
data class MovieResponse(
    @SerialName("popularity") val popularity: Double,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("video") val video: Boolean,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("id") val id: Int,
    @SerialName("adult") val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("genre_ids") val genreIds: List<Int>,
    @SerialName("title") val title: String,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("overview") val overview: String,
    @SerialName("release_date") val releaseDate: String
)