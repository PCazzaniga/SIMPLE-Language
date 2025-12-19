import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

public class validateListenerWithBar extends validateListener{

    private final progressBar bar;

    public validateListenerWithBar(Parser recognizer, int totalTrack) {
        super(recognizer);
        bar = new progressBar(totalTrack, 5);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        if(!(ctx instanceof simpleParser.FileContext)){
            bar.progress();
            if (bar.isVisibleProgress()) {
                System.out.print("\r" + bar);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void exitFile(simpleParser.FileContext ctx) {
        bar.progress();
        String finalBar = bar.toString();
        System.out.print("\r" + finalBar);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.print("\r" + " ".repeat(finalBar.length()) + "\r");
        super.exitFile(ctx);
    }
}