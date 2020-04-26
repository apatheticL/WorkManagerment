package com.nhatle.workmangement.data.model.response

data class RegisterResponse(
    var username: String,
    var password: String,
    var avatar: String,
    var full_name: String,
    var addres: String,
    var email: String,
    var phone: String,
    var createdtime: String
)