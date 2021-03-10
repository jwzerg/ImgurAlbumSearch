package com.imguralbumsearch.utils

import com.imguralbumsearch.rpc.Media

private const val MIME_TYPE_VIDEO_MP4 = "video/mp4"

fun isVideo(media: Media) = media.mimeType == MIME_TYPE_VIDEO_MP4