import java.io.*;
import java.lang.reflect.Array;

import program.exceptions.SyntaxException;
import program.List;
import syntax.LISPParser;

import static visualization.VisualizeKt.ELementToPNG;

import static program.ProgramBuilderKt.getProgram;
import static program.ProgramExecutionKt.executeProgram;
import static staticanalyzer.LiveVariablesAnalysisKt.calculateLiveVariables;
import static staticanalyzer.LiveVariablesAnalysisKt.getUnusedVariables;

// Рената перепиши на Котлин по братски
// нет
public class Main {

    public static void main(String[] args) throws IOException, IllegalStateException, SyntaxException {
        try {
            var mode = args[0];
            var reader = new StringReader(args[1]);
            var lexer = new LISPParser.LISPLexer(reader);
            var parser = new LISPParser(lexer);
            parser.parse();

            var program = (List) getProgram(LISPParser.node);
            switch (mode) {
                case "run": {
                    var result = executeProgram(program);
                    System.out.println(result);
                    break;
                }
                case "analyse": {
                    var result = getUnusedVariables(program);
                    for (var variable : result) {
                        System.out.print(variable + ",");
                    }
                    break;
                }
                case "visualize": {
                    var dotOutput = new FileWriter("/Users/r-shakirova/IdeaProjects/F-Language/src/visualization/output.dot");
                    var result = calculateLiveVariables(program);
                    var dot = ELementToPNG(result);
                    dotOutput.write(dot);
                    dotOutput.close();
                    String[] exec = {"dot", "-Tpng", "-o output.png", "output.dot"};
                    var p = Runtime.getRuntime().exec(exec);
                    try {
                        System.out.println(p.waitFor());
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                }
            }
        } catch (Throwable ex) {
            System.out.println("");
        }
    }
}
