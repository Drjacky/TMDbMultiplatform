package di

import data.base.mapper.Mapper
import data.base.remote.Api
import data.entity.Movie
import data.entity.MoviesResponse
import data.mapper.MoviesMapper
import data.remote.MoviesApiImpl
import data.repository.MoviesRepositoryImpl
import domain.base.repository.Repository
import domain.base.usecase.UseCase
import domain.usecase.GetMoviesUseCaseImpl
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import org.kodein.di.*
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object Di {

    val di = DI.lazy {
        bind<Mapper<MoviesResponse, List<Movie>>>() with singleton { MoviesMapper() }
        bind<String>("ApiHost") with provider { "https://www.omdbapi.com/" }
        bind<String>("ClientId") with provider { "b445ca0b" }
        bind<HttpClient>() with singleton {
            HttpClient {
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }
            }

        }
        bind<Api<String, List<Movie>>>("Api") with provider {
            val clientId by di.instance<String>("ClientId")
            val apiHost by di.instance<String>("ApiHost")

            MoviesApiImpl(
                client = instance(),
                key = clientId,
                hostUrl = apiHost,
                mapper = instance()
            )
        }
        bind<Repository<String, List<Movie>>>("Repository") with provider {
            MoviesRepositoryImpl<String>(instance(tag = "Api"))
        }
        bind<UseCase<String, List<Movie>>>("UseCase") with provider {
            GetMoviesUseCaseImpl<String>(instance(tag = "Repository"))
        }
    }

}