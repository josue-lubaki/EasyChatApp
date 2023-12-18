package com.alithya.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alithya.common.ui.themes.BoilerPlateTheme

@Composable
fun OptionCell(
    title: String,
    optionSelected : MutableState<String>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(42.dp)
            .background(color = MaterialTheme.colorScheme.background)
            .clickable { optionSelected.value = title }
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){

        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = title,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Normal,
        )

        if(title == optionSelected.value){
            Icon(
                modifier = Modifier.padding(end = 16.dp),
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun OptionCellPreview() {
    BoilerPlateTheme {
        OptionCell(
            title = "Work Skils",
            optionSelected = remember { mutableStateOf("Work Skils")}
        )
    }
}