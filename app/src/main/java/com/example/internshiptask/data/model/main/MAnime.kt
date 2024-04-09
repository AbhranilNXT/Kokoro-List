package com.example.internshiptask.data.model.main

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

data class MAnime(
    @Exclude var id: String? = null,
    var title: String? = null,
    var studio: String? = null,
    var notes: String? = null,
    @get:PropertyName("image_url")
    @set:PropertyName("image_url")
    var imgUrl: String? = null,
    var year: String? = null,
    var status: String? = null,
    var episodes: String? = null,
    var malscore: Double? = null,
    var genres: String? = null,
    var synopsis: String? = null,
    var rating: Double? = null,
    @get:PropertyName("started_watching")
    @set:PropertyName("started_watching")
    var startedWatching: Timestamp? = null,
    @get:PropertyName("finished_watching")
    @set:PropertyName("finished_watching")
    var finishedWatching: Timestamp? = null,
    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userId: String? = null,
    @get:PropertyName("mal_id")
    @set:PropertyName("mal_id")
    var malId: Int? = null
    ){
}
