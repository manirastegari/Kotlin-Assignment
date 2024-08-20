package com.example.project_g02e

import java.io.Serializable

class Lesson : Serializable {
    val name: String
    val length: String
    var completed: Boolean
    val number: Int
    val details: String

    constructor(name: String, length: String, completed: Boolean, number: Int, details: String) {
        this.name = name
        this.length = length
        this.completed = completed
        this.number = number
        this.details = details
    }

    override fun toString(): String {
        return "Lesson(name='$name', length='$length', completed=$completed, number=$number, details='$details')"
    }
}