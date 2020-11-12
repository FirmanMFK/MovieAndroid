package id.firman.movieandroid.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.firman.movieandroid.models.entity.*
import id.firman.movieandroid.utils.*

@Database(
    entities = [(Movie::class),
        (Tv::class),
        (Person::class),
        (SearchMovieResult::class),
        (DiscoveryMovieResult::class),
        (DiscoveryTvResult::class),
        (MovieRecentQueries::class),
        (SearchTvResult::class),
        (TvRecentQueries::class),
        (MovieSuggestions::class),
        (TvSuggestions::class),
        (FilteredMovieResult::class),
        (FilteredTvResult::class),
        (PeopleResult::class),
        (MoviePerson::class),
        (TvPerson::class),
        (MoviePersonResult::class),
        (TvPersonResult::class),
        (SearchPeopleResult::class),
        (PeopleRecentQueries::class),
        (PeopleSuggestions::class)],
    version = 30, exportSchema = false
)
@TypeConverters(
    value = [(StringListConverter::class), (IntegerListConverter::class),
        (KeywordListConverter::class), (VideoListConverter::class), (ReviewListConverter::class)]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao
    abstract fun peopleDao(): PeopleDao
}
