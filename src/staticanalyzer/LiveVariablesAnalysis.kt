package staticanalyzer

import program.*
import program.List

data class ElementWithAnalysisData(
    val element: Element = NullElement(),
    val children: kotlin.collections.List<ElementWithAnalysisData> = emptyList(),
    val gen: Set<String> = mutableSetOf(),
    val kill: Set<String> = mutableSetOf(),
    var setIn: Set<String> = mutableSetOf(),
    val setOut: Set<String> = mutableSetOf()
)

fun getKilledVariablesOut(identifier: Identifier, elements: kotlin.collections.List<Element>): Set<String> {
    when (identifier.name) {
        "setq", "func" -> {
            return setOf((elements[0] as Identifier).name)
        }
    }
    return setOf()
}

fun getKilledVariablesIn(identifier: Identifier, elements: kotlin.collections.List<Element>): Set<String> {
    var ids: kotlin.collections.List<Identifier> = listOf()
    when (identifier.name) {
        "func" -> {
            ids = (elements[2] as List).elements.map { it as Identifier }
        }
        "lambda", "prog" -> {
            ids = (elements[1] as List).elements.map { it as Identifier }
        }
    }
    return ids.map { it.name }.toSet()
}

fun getElementToEvaluate(identifier: Identifier, elements: kotlin.collections.List<Element>): Element {
    when (identifier.name) {
        "func" -> {
            return elements[2]
        }
        "lambda", "prog", "setq" -> {
            return elements[1]
        }
    }
    return elements[0]
}

fun calculateLiveVariables(element: Element, input: ElementWithAnalysisData): ElementWithAnalysisData {
    var gen = setOf<String>()
    var kill = setOf<String>()
    var children = mutableListOf<ElementWithAnalysisData>()
    var setIn: Set<String>
    var setOut = setOf<String>().union(input.setIn)
    when (element) {
        is List -> {
            when (val firstElement = element.elements[0]) {
                is Identifier -> {
                    when (firstElement.name) {
                        "setq", "lambda", "func", "prog" -> {
                            val identifier = firstElement
                            val rest = element.elements.drop(1)

                            val localInput = ElementWithAnalysisData(
                                input.element,
                                input.children,
                                input.gen,
                                input.kill,
                                input.setIn.minus(getKilledVariablesIn(identifier, rest)),
                                input.setOut
                            )
                            val expression = calculateLiveVariables(getElementToEvaluate(identifier, rest), localInput)
                            children.add(expression)


                            setOut = setOut.union(expression.setIn)
                            kill = kill.union(getKilledVariablesOut(identifier, rest))
                        } else -> {
                            var prevInput = input
                            for (elem in element.elements.reversed()) {
                                val prev = calculateLiveVariables(elem, prevInput)
                                children.add(prev)
                                setOut = setOut.union(prev.setIn)
                                prevInput = prev
                            }
                        }
                    }
                }  else -> {
                    var prevInput = input
                    for (elem in element.elements.reversed()) {
                        val prev = calculateLiveVariables(elem, prevInput)
                        children.add(prev)
                        setOut = setOut.union(prev.setIn)
                        prevInput = prev
                    }
                }
            }
        }
        is Identifier -> {
            if (isKeyword(element.name) || isSpecialForm(element.name)) {
                return ElementWithAnalysisData(element)
            }
            gen = gen.union(setOf(element.name))
        }
    }
    setIn = gen.union(setOut.minus(kill))
    return ElementWithAnalysisData(element, children, gen, kill, setIn,setOut)
}

fun calculateLiveVariables(program: List): ElementWithAnalysisData {
    return calculateLiveVariables(program, ElementWithAnalysisData())
}

fun getAllIdentifiers(program: Element): Set<String> {
    val identifiers = mutableSetOf<String>()
    when (program) {
        is List -> {
            for (element in program.elements) {
                identifiers.addAll(getAllIdentifiers(element))
            }
        }
        is Identifier -> {
            if (isKeyword(program.name) || isSpecialForm(program.name)) {
                return setOf()
            }
            return setOf(program.name)
        }
    }
    return identifiers
}

fun getAllSetIns(program: ElementWithAnalysisData): Set<String> {
    var setIns = program.setIn
    for (child in program.children) {
        setIns = setIns.union(getAllSetIns(child))
    }
    return setIns
}

fun getUnusedVariables(program: List): Set<String> {
    val analysisData = calculateLiveVariables(program)
    val usedVars = getAllSetIns(analysisData)
    val allVars = getAllIdentifiers(program)
    return allVars.minus(usedVars)
}
