import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import program.exceptions.SyntaxException;
import program.List;
import syntax.LISPParser;

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
                }
                case "analyse": {
                    var result = getUnusedVariables(program);
                    for (var variable : result) {
                        System.out.print(variable + ",");
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("");
        }
    }
}
