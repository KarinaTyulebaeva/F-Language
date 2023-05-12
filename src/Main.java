import java.io.FileReader;
import java.io.IOException;

import program.exceptions.SyntaxException;
import program.List;
import syntax.LISPParser;

import static program.ProgramBuilderKt.getProgram;
import static program.ProgramExecutionKt.executeProgram;

// Рената перепиши на Котлин по братски
public class Main {
    public static void main(String[] args) throws IOException, IllegalStateException, SyntaxException {
        var reader = new FileReader("/Users/k.tyulebaeva/compiler/Functional-Compiler/src/input.txt");
        var lexer = new LISPParser.LISPLexer(reader);
        var parser = new LISPParser(lexer);
        parser.parse();

        var program = (List) getProgram(LISPParser.node);
        var result = executeProgram(program);
        System.out.println(result);
    }
}
