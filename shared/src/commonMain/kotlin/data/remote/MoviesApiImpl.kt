package data.remote

import data.base.mapper.Mapper
import data.base.remote.Api
import data.entity.Movie
import data.entity.MoviesResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpStatement
import io.ktor.client.statement.readText
import io.ktor.http.HttpHeaders
import io.ktor.http.takeFrom
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
            parameter("s", request)
        }

        val httpResponse = httpStatement.execute()

        val json = httpResponse.readText()

        val response = Json.nonstrict.parse(MoviesResponse.serializer(), json)

        return mapper.mapTo(response)
    }

    private fun HttpRequestBuilder.apiUrl(path: String? = null) {
        header(HttpHeaders.CacheControl, "no-cache")
        url {
            takeFrom(hostUrl).parameters.append("apiKey", key)
            path?.let {
                encodedPath = it
            }
        }
    }

}