package id.firman.movieandroid.models.entity

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = Tv::class)
@Entity(tableName = "tvSuggestions")
class TvSuggestions (val id: Int, val name: String)