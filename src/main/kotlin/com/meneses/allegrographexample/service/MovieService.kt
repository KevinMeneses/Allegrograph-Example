package com.meneses.allegrographexample.service

import com.meneses.allegrographexample.repository.MovieRepository

private const val PREFIX_CLASS = "prefix class: <http://example.com/class/>\n"
private const val PREFIX_PROP = "prefix prop: <http://example.com/property/>\n"
private const val PREFIX_RDF = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"

class MovieService(
    private val movieRepository: MovieRepository
) {
    fun getMovieTitleWithActorAndDirectorIfAvailable() {
        val query = PREFIX_PROP + PREFIX_RDF +
                "SELECT ?title ?actor ?director \n" +
                "WHERE {\n" +
                "  ?movie prop:title ?title.\n" +
                "  ?movie prop:actor/prop:name ?actor.\n" +
                "  OPTIONAL { \n" +
                "    ?movie prop:director/prop:name ?director. \n" +
                "  }\n" +
                "}"
        val result = movieRepository.executeTupleQuery(query)
        while (result?.hasNext() == true) {
            val binding = result.next()
            println(
                "title: " + binding.getBinding("title").value.stringValue() + " - " +
                        "actor: " + binding.getBinding("actor").value.stringValue() + " - " +
                        "director: " + binding.getBinding("director").value.stringValue()
            )
        }
    }

    fun getMovieTitleWithDirectorAndRatingSourceWhereRatingIsGreaterThan6() {
        val query = PREFIX_PROP + PREFIX_RDF +
                "SELECT ?title ?director ?rating_source\n" +
                "WHERE {\n" +
                "  ?movie prop:title ?title.\n" +
                "  ?movie prop:director/prop:name ?director.\n" +
                "  ?movie prop:rating/prop:value ?rating.\n" +
                "  ?movie prop:rating/prop:source ?rating_source.\n" +
                "  FILTER(?rating > 6.0)\n" +
                "}\n" +
                "GROUP BY ?title ?director ?rating_source\n" +
                "ORDER BY ?title"
        val result = movieRepository.executeTupleQuery(query)
        while (result?.hasNext() == true) {
            val binding = result.next()
            println(
                "title: " + binding.getBinding("title").value.stringValue() + " - " +
                        "director: " + binding.getBinding("director").value.stringValue() + " - " +
                        "rating source: " + binding.getBinding("rating_source").value.stringValue()
            )
        }
    }

    fun getActorCouplesThatWorkedInTheSameMovie() {
        val query = PREFIX_PROP + PREFIX_RDF +
                "SELECT ?actor1 ?actor2\n" +
                "WHERE {\n" +
                "  ?movie prop:actor/prop:name ?actor1 .\n" +
                "  ?movie prop:actor/prop:name ?actor2 .\n" +
                "  ?movie prop:title ?title .\n" +
                "  FILTER (?actor1 < ?actor2)\n" +
                "}\n" +
                "ORDER BY ?actor1"
        val result = movieRepository.executeTupleQuery(query)
        while (result?.hasNext() == true) {
            val binding = result.next()
            println(
                "actor1: " + binding.getBinding("actor1").value.stringValue() + " - " +
                        "actor2: " + binding.getBinding("actor2").value.stringValue()
            )
        }
    }

    fun getActorsThatWorkedWithAnActorThatWorkedWithHarrisonFord() {
        val query = PREFIX_PROP + PREFIX_RDF +
                "SELECT ?actor\n" +
                "WHERE {\n" +
                "  ?movie prop:actor/prop:name ?actor .\n" +
                "  ?movie prop:actor/prop:name ?actor2 .\n" +
                "  ?movie2 prop:actor/prop:name ?actor2 .\n" +
                "  ?movie2 prop:actor/prop:name ?actor3 .\n" +
                "  ?movie prop:title ?title .\n" +
                "  FILTER (?actor < ?actor2)\n" +
                "  FILTER (?actor != ?actor3)\n" +
                "  FILTER (?actor2 != ?actor3)\n" +
                "  FILTER regex(?actor3, \"Harrison Ford\")\n" +
                "}\n" +
                "GROUP BY ?actor"
        val result = movieRepository.executeTupleQuery(query)
        while (result?.hasNext() == true) {
            val binding = result.next()
            println("actor: " + binding.getBinding("actor").value.stringValue())
        }
    }

    fun updateIMDBRatingValueOfTheSocialNetworkMovie() {
        val query = PREFIX_PROP + PREFIX_RDF +
                "DELETE { ?rating prop:value ?rating_value }\n" +
                "INSERT { ?rating prop:value 8 }\n" +
                "WHERE {\n" +
                "  ?movie prop:title ?title .\n" +
                "  ?movie prop:rating ?rating .\n" +
                "  ?rating prop:source ?rating_source .\n" +
                "  ?rating prop:value ?rating_value .\n" +
                "  FILTER regex(?title, \"The Social Network\")\n" +
                "  FILTER regex(?rating_source, \"Internet Movie Database\")\n" +
                "}"
        val result = movieRepository.executeBooleanQuery(query)
        println("Movie updated: $result")
    }

    fun deleteArtificialIntelligenceMoviePlot() {
        val query = PREFIX_PROP + PREFIX_RDF +
                "DELETE { ?movie prop:plot ?plot }\n" +
                "WHERE { \n" +
                "  ?movie prop:title ?title .\n" +
                "  ?movie prop:plot ?plot\n" +
                "  FILTER regex(?title, \"A.I. Artificial Intelligence\")\n" +
                "}"
        val result = movieRepository.executeBooleanQuery(query)
        println("Plot deleted: $result")
    }
}