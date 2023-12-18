package com.alithya.common.utils

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * created by Josue Lubaki
 * date : 2023-09-08
 * version : 1.0.0
 */

@Preview(
    name = "1 - Phone Portrait",
    device = "spec:width=412dp,height=892dp,orientation=portrait"
)
@Preview(
    name = "11 - Phone Portrait (Dark Mode)",
    device = "spec:width=412dp,height=892dp,orientation=portrait",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "2 - Phone Lanscape",
    device = "spec:width=412dp,height=892dp,orientation=landscape"
)
@Preview(
    name = "22 - Phone Lanscape (Dark Mode)",
    device = "spec:width=412dp,height=892dp,orientation=landscape",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "3 - Foldable Portrait",
    device = "spec:width=839dp,height=945dp,orientation=portrait"
)
@Preview(
    name = "33 - Foldable Portrait (Dark Mode)",
    device = "spec:width=839dp,height=945dp,orientation=portrait",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "4 - Foldable Landscape",
    device = "spec:width=839dp,height=945dp,orientation=landscape"
)
@Preview(
    name = "44 - Foldable Landscape (Dark Mode)",
    device = "spec:width=839dp,height=945dp,orientation=landscape",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "5 - Tablette Portrait",
    device = "spec:width=1280dp,height=900dp,orientation=portrait"
)
@Preview(
    name = "55 - Tablette Portrait (Dark Mode)",
    device = "spec:width=1280dp,height=900dp,orientation=portrait",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "6 - Tablette Lanscape",
    device = "spec:width=1280dp,height=900dp,orientation=landscape"
)
@Preview(
    name = "66 - Tablette Lanscape (Dark Mode)",
    device = "spec:width=1280dp,height=900dp,orientation=landscape",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class AllPreview