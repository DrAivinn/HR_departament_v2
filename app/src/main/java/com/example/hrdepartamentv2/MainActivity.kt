package com.example.hrdepartamentv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val persons = Person.persons.sortedBy { it.surname }
        val groups = persons.groupBy { it.jobTitle }

        setContent {
            PersonsList(persons, groups)
        }
    }

    @Composable
    @OptIn(ExperimentalFoundationApi::class)
    private fun PersonsList(
        persons: List<Person>,
        groups: Map<String, List<Person>>
    ) {
        val listState = rememberLazyListState()
        val scope = rememberCoroutineScope()
        LazyColumn(state = listState) {
            item {
                Text(
                    "В конец",
                    Modifier
                        .fillMaxWidth()
                        .background(Color.DarkGray)
                        .padding(4.dp)
                        .clickable() { scope.launch { listState.animateScrollToItem(persons.lastIndex) } },
                    fontSize = 36.sp,
                    color = Color.White
                )
            }
            groups.forEach { (jobTitle, persons) ->
                stickyHeader(jobTitle) {
                    Text(
                        jobTitle,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Cyan)
                    )
                }
                items(persons) { person ->
                    Text("${person.surname} ${person.name}", fontSize = 24.sp)
                }

            }
            item {
                Text(
                    "В начало",
                    Modifier
                        .fillMaxWidth()
                        .background(Color.DarkGray)
                        .padding(4.dp)
                        .clickable() { scope.launch { listState.animateScrollToItem(0) } },
                    fontSize = 36.sp, color = Color.White
                )
            }
        }
    }
}
