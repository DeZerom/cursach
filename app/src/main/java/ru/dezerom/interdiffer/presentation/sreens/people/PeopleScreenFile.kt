package ru.dezerom.interdiffer.presentation.sreens.people

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ru.dezerom.interdiffer.presentation.widgets.BaseColumnWidget
import ru.dezerom.interdiffer.presentation.widgets.EmptyListWidget

@Composable
fun PeopleScreen() {
    val context = LocalContext.current

    BaseColumnWidget {
        EmptyListWidget {
            Toast.makeText(context, "1", Toast.LENGTH_LONG).show()
        }
    }
}
