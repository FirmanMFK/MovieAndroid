package id.firman.movieandroid.models.entity

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = Person::class)
@Entity(tableName =  "peopleSuggestionsFts")
class PeopleSuggestions (val id: Int, val name: String)