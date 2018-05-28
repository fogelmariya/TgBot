package com.github.fogelao.entity

/**
 * @author Fogel Artem on 27.05.2018.
 */
class Event(
        val id: String,
        val eventDateTime: String,
        val operationDateTime: String,
        val operationAttribute: String,
        val operationType: String,
        val operationPlacePostalCode: String,
        val operationPlaceName: String,
        val itemWeight: String
) {
    override fun toString(): String {
        return "OperationDateTime: $operationDateTime\n" +
                "OperationAttribute: $operationAttribute\n" +
                "OperationPlaceName: $operationPlaceName"
    }
}