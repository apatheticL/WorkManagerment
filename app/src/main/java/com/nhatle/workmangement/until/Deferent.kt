package com.nhatle.workmangement.until

const val ADDRESS = "http://192.168.0.102"
const val BASE_URL = "$ADDRESS:8080"
const val URL_SOCKET = "$ADDRESS:3306"
const val LOCATION_URI = "avatars"
const val PART_URI = "avatars"
const val SHARE = "SHARE"
const val USER = "USER"

class Deferent {
    companion object {
        private var number = -1

        @JvmStatic
        fun setNumberMember(number: Int) {
            this.number = number
        }

        @JvmStatic
        fun getNumber(): Int = number
    }
}