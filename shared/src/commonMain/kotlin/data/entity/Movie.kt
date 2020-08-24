package data.entity

private const val POSTER_PATH_BASE_URL = "https://image.tmdb.org/t/p/original"

data class Movie(
    val popularity: Double,
    val voteCount: Int,
    val video: Boolean,
    val posterPath: String,
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val genreIds: List<Int>,
    val title: String,
    val voteAverage: Double,
    val overview: String,
    val releaseDate: String,
    val fullPosterPath: String = POSTER_PATH_BASE_URL + posterPath
)