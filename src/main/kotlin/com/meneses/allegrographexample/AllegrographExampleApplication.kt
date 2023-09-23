package com.meneses.allegrographexample

import com.meneses.allegrographexample.repository.MovieRepository
import com.meneses.allegrographexample.service.MovieService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AllegrographExampleApplication

fun main(args: Array<String>) {
	runApplication<AllegrographExampleApplication>(*args)
	val repository = MovieRepository().apply { connect() }
	MovieService(repository).run {
		println("\nExercise 1: getMovieTitleWithActorAndDirectorIfAvailable\n")
		getMovieTitleWithActorAndDirectorIfAvailable()

		println("\nExercise 2: getMovieTitleWithDirectorAndRatingSourceWhereRatingIsGreaterThan6\n")
		getMovieTitleWithDirectorAndRatingSourceWhereRatingIsGreaterThan6()

		println("\nExercise 3: getActorCouplesThatWorkedInTheSameMovie\n")
		getActorCouplesThatWorkedInTheSameMovie()

		println("\nExercise 4: getActorsThatWorkedWithAnActorThatWorkedWithHarrisonFord\n")
		getActorsThatWorkedWithAnActorThatWorkedWithHarrisonFord()

		println("\nExercise 5: updateIMDBRatingValueOfTheSocialNetworkMovie\n")
		updateIMDBRatingValueOfTheSocialNetworkMovie()

		println("\nExercise 6: deleteArtificialIntelligenceMoviePlot\n")
		deleteArtificialIntelligenceMoviePlot()
	}
}
