package data.remote

import data.base.mapper.Mapper
import data.base.remote.Api
import data.entity.Movie
import data.entity.MoviesResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json

class MoviesApiImpl(
    private val client: HttpClient,
    private val key: String,
    private val hostUrl: String,
    private val mapper: Mapper<MoviesResponse, List<Movie>>
) : Api<String, List<Movie>> {

    @OptIn(UnstableDefault::class)// = deprecated: @UseExperimental(UnstableDefault::class)
    override suspend fun execute(request: String?): List<Movie> {

        val httpStatement = client.get<HttpStatement> {
            apiUrl()
            parameter("query", request)
        }

        val httpResponse = httpStatement.execute()

        val json = httpResponse.readText()

        val response = Json.nonstrict.parse(MoviesResponse.serializer(), json)

        return mapper.mapTo(response)

    }

    private fun HttpRequestBuilder.apiUrl(path: String? = null) {
        header(HttpHeaders.CacheControl, "no-cache")
        url {
            takeFrom(hostUrl).parameters.append("api_key", key)
            path?.let {
                encodedPath = it
            }
        }
    }

}