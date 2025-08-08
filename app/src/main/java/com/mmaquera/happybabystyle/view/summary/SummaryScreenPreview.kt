package com.mmaquera.happybabystyle.view.summary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.mmaquera.happybabystyle.ui.theme.HappyBabyStyleTheme

/**
 * Comprehensive previews for SummaryScreen components
 * Following Single Responsibility Principle - only handles previews
 */

@Preview(name = "Summary Screen - Complete", showBackground = true, showSystemUi = true)
@Composable
fun SummaryScreenCompletePreview() {
    HappyBabyStyleTheme {
        SummaryScreen()
    }
}

@Preview(name = "Summary Screen - Light Theme", showBackground = true)
@Composable
fun SummaryScreenLightPreview() {
    HappyBabyStyleTheme(darkTheme = false) {
        SummaryScreen()
    }
}

@Preview(name = "Summary Screen - Dark Theme", showBackground = true)
@Composable
fun SummaryScreenDarkPreview() {
    HappyBabyStyleTheme(darkTheme = true) {
        SummaryScreen()
    }
}

@Preview(name = "Summary Screen - Loading State", showBackground = true)
@Composable
fun SummaryScreenLoadingPreview() {
    HappyBabyStyleTheme {
        val viewModel = remember { SummaryViewModel(SummaryRepositoryImpl()) }
        SummaryScreen(viewModel = viewModel)
    }
}

@Preview(name = "Summary Screen - Error State", showBackground = true)
@Composable
fun SummaryScreenErrorPreview() {
    HappyBabyStyleTheme {
        val viewModel = remember { 
            SummaryViewModel(object : SummaryRepository {
                override fun getOrderSummary(): OrderSummaryInfo {
                    throw RuntimeException("Network error")
                }
            })
        }
        SummaryScreen(viewModel = viewModel)
    }
} 