package ru.dezerom.interdiffer.presentation.utils.res.destinations

sealed class Graphs(val route: String) {

    object PeopleGraph: Graphs("people_graph")

    object ComparisonsGraph: Graphs("comparisons_graph")

}
