package com.haki.hqrecipe.model

data class RecipeModel (
    val id: Long,
    val name: String,
    val photo: String,
    val desc: String,
    val ingre: String,
    val steps: String,
    val isSaved: Boolean = false,
)