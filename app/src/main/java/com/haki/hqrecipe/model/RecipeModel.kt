package com.haki.hqrecipe.model

data class RecipeModel (
    val id: String,
    val name: String,
    val photo: String,
    val desc: String,
    val ingre: String,
    val steps: String,
)