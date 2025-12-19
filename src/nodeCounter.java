import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

public class nodeCounter implements ParseTreeListener {

    private int count = 0;

    @Override
    public void visitTerminal(TerminalNode terminalNode) {}

    @Override
    public void visitErrorNode(ErrorNode errorNode) {}

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {
        count++;
    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {}

    public int getCount() {
        return count;
    }
}