package com.imguralbumsearch.rpc

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

data class SearchResponse(
    val success: Boolean,
    @SerializedName("status") val statusCode: Int,
    @SerializedName("data") val albumList: List<Album>
)

@Parcelize
data class Album(
    val id: String,
    val title: String,
    @SerializedName("images_count") val imagesCount: Int,
    val images: List<Image>
) : Parcelable

@Parcelize
data class Image(
    val id: String,
    @SerializedName("type") val mimeType: String,
    @SerializedName("link") val url: String,
    /**
     * Use this url when the mime type is video/mp4.
     * Note, this field is not always set in the response.
     */
    @SerializedName("gifv") val gifUrl: String? = null
) : Parcelable

/** The RPC client for calling Imgur gallery search API. */
interface ImgurSearchService {
    @GET("/3/gallery/search")
    suspend fun searchAlbums(@Query("q") query: String): Response<SearchResponse>
}