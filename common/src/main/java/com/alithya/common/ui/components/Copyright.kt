package com.alithya.common.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.alithya.common.R
import com.alithya.common.ui.themes.BoilerPlateTheme
import com.alithya.common.ui.themes.dimensions

@Composable
fun Copyright(
    modifier : Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.small)
    ) {
        Text(
            text = stringResource(id = R.string.login_copyright_msg).uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = MaterialTheme.dimensions.letterSpacingSmall,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.login_policy_msg_small).uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = MaterialTheme.dimensions.letterSpacingSmall,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CopyrightPreview() {
    BoilerPlateTheme {
        Copyright()
    }
}