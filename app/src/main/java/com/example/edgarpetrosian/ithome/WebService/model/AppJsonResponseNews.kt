package com.example.edgarpetrosian.ithome.WebService.model

data class AppJsonResponseNews(
        val id: String,
        val title: String,
        val description: String,
        val detailText: String,
        val postImageUrl: String,
        val postVideoUrl: String,
        val category: String
)