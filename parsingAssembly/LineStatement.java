package parsingAssembly;

import java.util.Comparator;

public class LineStatement {

    private Label label;
    private Instruction instruction;
    private Comment comment;

    public LineStatement(Label label, Instruction instruction, Comment comment) {
        this.label = label;
        this.instruction= instruction;
        this.comment = comment;
    }
}