package id.firman.movieandroid.models.entity

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = Movie::class)
@Entity(tableName =  "movieSuggestionsFts")
class MovieSuggestions (val id: Int, val title: String)