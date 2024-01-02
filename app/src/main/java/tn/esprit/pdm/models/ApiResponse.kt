package tn.esprit.pdm.models

data class ApiResponse<T>(
    val message: String,
    val apiResponse: T
)
