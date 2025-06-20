package com.magnus.playfut.domain.helper

import com.magnus.playfut.domain.model.structure.Player

data class Substitution(val outPlayer: Player, val inPlayer: Player)

// mínimo comum múltiplo (para rodízio completo)
private fun lcm(a: Int, b: Int): Int {
    fun gcd(x: Int, y: Int): Int = if (y == 0) x else gcd(y, x % y)
    return a * b / gcd(a, b)
}

object RotationGenerator {
    fun generate(
        starters: List<Player>,
        substitutes: List<Player>
    ):  List<Substitution> {
        val field = starters.sortedBy { it.id }.toMutableList()
        val bench = substitutes.sortedBy { it.id }.toMutableList()

        val outQueue = ArrayDeque(field)
        val inQueue = ArrayDeque(bench)

        val substitutions = mutableListOf<Substitution>()

        val fieldSize = field.size
        val benchSize = bench.size

        val requiredSubstitutions = lcm(fieldSize, benchSize)
        val initialBenchIds = bench.map { it.id }.toSet()

        var count = 0

        while (true) {
            if (inQueue.isEmpty()) break

            val outPlayer = outQueue.removeFirst()
            val inPlayer = inQueue.removeFirst()

            substitutions.add(Substitution(outPlayer, inPlayer))

            field.remove(outPlayer)
            field.add(inPlayer)

            inQueue.addLast(outPlayer)
            outQueue.addLast(inPlayer)

            count++

            val fieldIds = field.map { it.id }.toSet()
            val allOriginalReservesOffField = initialBenchIds.all { it !in fieldIds }

            if (count >= requiredSubstitutions && allOriginalReservesOffField) {
                break
            }
        }

        return substitutions
    }

    fun groupSubstitutionsByRounds(
        substitutions: List<Substitution>,
        benchSize: Int
    ): List<List<Substitution>> {
        val rounds = mutableListOf<List<Substitution>>()
        var startIndex = 0

        while (startIndex < substitutions.size) {
            val endIndex = (startIndex + benchSize).coerceAtMost(substitutions.size)
            val round = substitutions.subList(startIndex, endIndex)
            rounds.add(round)
            startIndex += benchSize
        }

        return rounds
    }
}