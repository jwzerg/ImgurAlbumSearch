package com.imguralbumsearch.rpc

data class Album(val id: String, val title: String, val images: List<Image>)

data class Image(val type: String, val url: String, val gifUrl: String = "")