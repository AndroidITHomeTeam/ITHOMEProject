package com.example.edgarpetrosian.ithome.WebService.model

data class AppResponse(
        val id: String,
        val question: String,
        val answer: String,
        val option_a: String,
        val option_b: String,
        val option_c: String,
        val option_d: String
)



/*
http://www.jsonschema2pojo.org/   Generate Plain Old Java Objects from JSON or JSON-Schema.
 */