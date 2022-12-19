package com.ahtar1.sanitastest.view

import org.junit.Test
import com.google.common.truth.Truth.assertThat

class RegistrationUtilTest {

    @Test
    fun `empty email returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "123456",
            "123456",
            "12345678901",
            "Ahtar"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "test1@test.com",
            password = "",
            verifyPassword = "123456",
            tc = "12345678901",
            name = "Ahtar"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty verifyPassword returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "test1@test.com",
            password = "123456",
            verifyPassword = "",
            tc = "12345678901",
            name = "Ahtar"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty tc returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "test1@test.com",
            password = "123456",
            verifyPassword = "123456",
            tc = "",
            name = "Ahtar"
        )
    }

    @Test
    fun `empty name returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "test1@test.com",
            password = "123456",
            verifyPassword = "123456",
            tc = "12345678901",
            name = ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `short password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            email = "test1@test.com",
            password = "12345",
            verifyPassword = "12345",
            tc = "12345678901",
            name = "Ahtar"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `password and verifyPassword not the same returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            email = "test1@test.com",
            password = "123456",
            verifyPassword = "1234567",
            tc = "12345678901",
            name = "Ahtar"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `tc not 11 digits returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            email = "test1@test.com",
            password = "123456",
            verifyPassword = "123456",
            tc = "1234567890",
            name = "Ahtar"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid email returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "test1",
            password = "123456",
            verifyPassword = "123456",
            tc = "12345678901",
            name = "Ahtar"
        )
        assertThat(result).isFalse()
    }

}