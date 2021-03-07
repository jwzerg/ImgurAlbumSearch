package com.imguralbumsearch.rpc

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

data class SearchResponse(
    val success: Boolean,
    @SerializedName("status") val statusCode: Integer,
    @SerializedName("data") val albumList: List<Album>
)

data class Album(
    val id: String,
    val title: String,
    val images: List<Image>
)

data class Image(
    val id: String,
    val title: String,
    @SerializedName("type") val mimeType: String,
    @SerializedName("link") val url: String,
    /**
     * Use this url when the mime type is video/mp4.
     * The field is not always set in the response, we set it to an empty by default.
     */
    @SerializedName("gifv") val gifUrl: String = ""
)

/** The RPC client for calling Imgur gallery search API. */
interface ImgurSearchService {
    @GET("/3/gallery/search")
    suspend fun searchAlbums(@Query("q") query: String): Response<SearchResponse>
}