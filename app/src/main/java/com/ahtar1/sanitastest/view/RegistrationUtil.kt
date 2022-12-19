package com.ahtar1.sanitastest.view

import java.util.regex.Pattern

object RegistrationUtil {


    /**
     * The input is not valid if...
     * ...the username/password/verifyPassword/tc/name was empty
     * ...the confirmed password was not the same as the first password
     * ...the tc was not 11 digits
     * ...the email was invalid
     * ...the password was less than 6 digits
     */

    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun validateRegistrationInput(
        email: String,
        password: String,
        verifyPassword: String,
        tc: String,
        name: String
    ): Boolean {
        if (email.isEmpty() || password.isEmpty() || verifyPassword.isEmpty() || tc.isEmpty() || name.isEmpty()) {
            return false
        }

        if (password != verifyPassword) {
            return false
        }

        if (tc.length != 11) {
            return false
        }


        if (password.length < 6) {
            return false
        }


        if (!EMAIL_ADDRESS_PATTERN.matcher(email).matches()) {
            return false
        }


        return true
    }
}