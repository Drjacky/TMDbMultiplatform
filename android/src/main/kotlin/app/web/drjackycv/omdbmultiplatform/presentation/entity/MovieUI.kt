package app.web.drjackycv.omdbmultiplatform.presentation.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieUI(
    val title: String,
    val imdbID: String,
    val rated: String,
    val poster: String
) : Parcelable