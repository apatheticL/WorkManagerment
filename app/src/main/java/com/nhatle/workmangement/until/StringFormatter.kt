package com.nhatle.workmangement.until

import java.util.regex.Pattern

class StringFormatter {
    companion object {
        private val EMAIL_PATTENRN = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$" // cấu trúc 1 email thông thường
                )// cấu trúc 1 email thông thường

        @JvmStatic
        fun checkEmallFormat(string: String): Boolean =
            Pattern.compile(EMAIL_PATTENRN).matcher(string).matches()
    }
}