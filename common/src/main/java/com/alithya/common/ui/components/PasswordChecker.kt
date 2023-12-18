package com.alithya.common.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alithya.common.R
import com.alithya.common.ui.themes.EasyChatTheme

@Composable
fun PasswordChecker(
    password: String,
    onValidPassword: (Boolean) -> Unit,
    testTag: String = "PasswordCheckerQa"
) {
    /*
       password must contain at least 8 characters or more,
       at least 1 uppercase and
       1 lowercase letter
       2 or more digits
       1 symbol among @!#$%
   */
    val hasMinLength = remember(password) { password.length >= 8 }
    val hasUppercase = remember(password) { password.matches(Regex(".*[A-Z].*")) }
    val hasLowercase = remember(password) { password.matches(Regex(".*[a-z].*")) }
    val hasDigits = remember(password) { password.matches(Regex(".*\\d{2,}.*")) }
    val hasSymbol = remember(password) { password.matches(Regex(".*[@!#\$%].*")) }

    val isValid = remember(password) {
        hasMinLength && hasUppercase && hasLowercase && hasDigits && hasSymbol
    }

    LaunchedEffect(isValid) {
        onValidPassword(isValid)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp).testTag("$testTag-title"),
            text = stringResource(R.string.password_contain),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        PasswordCriteria(stringResource(R.string.minimum_8_caract_res), hasMinLength, testTag = "$testTag-hasMinLength")
        PasswordCriteria(stringResource(R.string.au_moins_une_majuscule), hasUppercase, testTag = "$testTag-hasUppercase")
        PasswordCriteria(stringResource(R.string.au_moins_une_minuscule), hasLowercase, testTag = "$testTag-hasLowercase")
        PasswordCriteria(stringResource(R.string.au_moins_deux_chiffres), hasDigits, testTag = "$testTag-hasDigits")
        PasswordCriteria(stringResource(R.string.au_moins_un_symbole), hasSymbol, testTag = "$testTag-hasSymbol")
    }
}

@Composable
fun PasswordCriteria(criteria: String, isMet: Boolean, testTag: String) {
    Row(
        modifier = Modifier.testTag(testTag),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isMet) {
            Icon(
                modifier = Modifier.size(16.dp).testTag("$testTag-CheckIcon"),
                imageVector = Icons.Default.Check,
                contentDescription = "Check Icon",
                tint = MaterialTheme.colorScheme.secondary,
            )
        } else {
            Icon(
                modifier = Modifier.size(16.dp).testTag("$testTag-CloseIcon"),
                imageVector = Icons.Default.Close,
                contentDescription = "Close Icon",
                tint = MaterialTheme.colorScheme.error,
            )
        }
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = criteria,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PasswordCheckerRightPassword() {
    EasyChatTheme {
        PasswordChecker(
            password = "Password12@",
            onValidPassword = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PasswordCheckerWrongPassword() {
    EasyChatTheme {
        PasswordChecker(
            password = "pass12",
            onValidPassword = {}
        )
    }
}