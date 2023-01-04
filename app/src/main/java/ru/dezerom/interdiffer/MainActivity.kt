package ru.dezerom.interdiffer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import ru.dezerom.interdiffer.presentation.sreens.people.PeopleScreen
import ru.dezerom.interdiffer.ui.theme.InterDifferTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InterDifferTheme {
                Scaffold(
                    topBar = { TopBar() }
                ) {
                    PeopleScreen()
                }
            }
        }
    }
}

@Composable
fun TopBar() {}
