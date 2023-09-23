package com.meneses.allegrographexample.repository

import com.franz.agraph.repository.AGRepositoryConnection
import com.franz.agraph.repository.AGServer
import org.eclipse.rdf4j.query.TupleQueryResult

private const val SERVER_URL = "http://localhost:10035"
private const val USER = "test"
private const val PASSWORD = "12345"
private const val REPO_NAME = "movies"

class MovieRepository(
    private var connection: AGRepositoryConnection? = null
) {
    fun connect() {
        connection = AGServer(SERVER_URL, USER, PASSWORD)
            .rootCatalog
            ?.openRepository(REPO_NAME)
            ?.getConnection()
    }

    fun executeTupleQuery(query: String): TupleQueryResult? {
        val tupleQuery = connection?.prepareTupleQuery(query)
        return tupleQuery?.evaluate()
    }

    fun executeBooleanQuery(query: String): Boolean {
        val booleanQuery = connection?.prepareBooleanQuery(query)
        return booleanQuery?.evaluate() ?: false
    }
}