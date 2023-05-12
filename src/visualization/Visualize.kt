package visualization

import syntax.LISPParser
import syntax.LISPParser.NonTerminalNode
import syntax.LISPParser.TerminalNode
import java.util.*


object Visualize {
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
}
