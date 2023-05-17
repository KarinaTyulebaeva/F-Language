package visualization

import program.Element
import program.Identifier
import program.List
import program.Literal
import syntax.LISPParser
import syntax.LISPParser.*
import java.util.*
import kotlin.collections.ArrayList

    fun TreeToPNG(tree: LISPParser.TreeNode): String {
        val queue = ArrayDeque<Int>()
        val nodes = ArrayList<LISPParser.TreeNode>()
        var cnt = 0
        nodes.add(tree)
        queue.add(0)
        var dotOutput = String.format("digraph G{%d[label=\"%s\"];$}", 0, "root")
        while (!queue.isEmpty()) {
            val topID = queue.poll()
            print("$topID ")
            val top = nodes[topID]
            // Print memory address
            println(top)
            for (child in top.children) {
                nodes.add(child as LISPParser.TreeNode)
                queue.add(++cnt)
                var nodeContent: String? = ""
                nodeContent = if (!child.isTerminal) {
                    (child as NonTerminalNode).nodeName
                } else {
                    (child as TerminalNode).data.value.toString()
                }
                val toReplace = String.format("%d[label=\"%s\"];%d->%d;$", cnt, nodeContent, topID, cnt)
                dotOutput = dotOutput.replace("$", toReplace)
            }
        }
        dotOutput = dotOutput.replace("$", "")
        return dotOutput
    }

fun ELementToPNG(element: Element): String {
    val queue = ArrayDeque<Int>()
    val nodes = ArrayList<Element>()
    var cnt = 0
    nodes.add(element)
    queue.add(0)
    var dotOutput = String.format("digraph G{%d[label=\"%s\"];$}", 0, "root")
    while (!queue.isEmpty()) {
        val topID = queue.poll()
        print("$topID ")
        val top = nodes[topID]
        // Print memory address
        println(top)
        val children = if (top is List) top.elements else emptyList()
        for (child in children) {
            nodes.add(child)
            queue.add(++cnt)
            var nodeContent: String? = ""
            when (child) {
                is List -> {
                    nodeContent = "Elements"
                }

                is Identifier -> {
                    nodeContent = child.name
                }

                is Literal -> {
                    nodeContent = child.print()
                }
            }
            val analysisData = if (child is List) {
                child.analysisData.setIn.fold("[ ") { str, it -> "$str $it " } + " ]"
            } else {
                ""
            }
            val toReplace = String.format("%d[label=\"%s \n %s \"];%d->%d;$", cnt, nodeContent, analysisData, topID, cnt)
            dotOutput = dotOutput.replace("$", toReplace)
        }
    }
    dotOutput = dotOutput.replace("$", "")
    return dotOutput
}
