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
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import org.kodein.di.*
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object Di {

    val di = DI.lazy {
        bind<Mapper<MoviesResponse, List<Movie>>>() with singleton { MoviesMapper() }
        bind<String>("ApiHost") with provider { "https://api.themoviedb.org/3/search/movie" }
        bind<String>("ApiKey") with provider { "238af7cc48b4305ff3fb75d7af217de4" }
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
            val apiKey by di.instance<String>("ApiKey")
            val apiHost by di.instance<String>("ApiHost")

            MoviesApiImpl(
                client = instance(),
                key = apiKey,
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