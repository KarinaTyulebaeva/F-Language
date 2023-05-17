package visualization

import program.exceptions.SyntaxException
import syntax.LISPParser
import syntax.LISPParser.LISPLexer
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.*


object ASTVisualization {
    @Throws(IOException::class, IllegalStateException::class, SyntaxException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val queue = ArrayDeque<LISPParser.TreeNode>()
        val level = ArrayDeque<Int>()
        queue.add(LISPParser.node)
        level.add(0)
        while (!queue.isEmpty()) {
            val p = queue.poll()
            val lvl = level.poll()
            if (p.isTerminal) {
                println(lvl.toString() + ":| " + p.data.value)
            } else {
                println(lvl.toString() + ":> " + p.nodeName)
            }
            for (x in p.children) {
                queue.add(x)
                level.add(lvl + 1)
            }
        }
        visualize()
    }

    @Throws(IOException::class)
    fun visualize() {
        val dotOutput = FileWriter("~/src/visualization/output.dot")
        val lexer = LISPLexer(FileReader("~/src/input.txt"))
        val parser = LISPParser(lexer)
        if (parser.parse()) {
            println("Parsing Result = SUCCESS")
            val dot = TreeToPNG(LISPParser.node)
            dotOutput.write(dot)
            dotOutput.close()
            val exec = arrayOf(
                "dot", "-Tpng", "-o output.png", "visualization/output.dot"
            )
            val p = Runtime.getRuntime().exec(exec)
            try {
                println(p.waitFor())
            } catch (e: InterruptedException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        }
        return
    }
}
