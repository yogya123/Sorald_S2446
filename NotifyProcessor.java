package sorald.processor;
import sorald.annotations.ProcessorAnnotation;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.factory.Factory;
import java.util.List;
import spoon.reflect.code.CtCodeSnippetStatement;


@ProcessorAnnotation( key= "S2446", description="notifyAll should be used instead of notify")
public class NotifyProcessor extends SoraldAbstractProcessor <CtSynchronized> {

@Override
protected void repairInternal(CtSynchronized element){
    CtBlock<?> blockFocus = element.getBlock();
    List<CtStatement> linesOfBlock = blockFocus.getStatements();

    for(int i =0 ; i< linesOfBlock.size(); i++)
    { String temp = linesOfBlock.get(i).toString();
      if(temp.contains("notify"))
      { CtCodeSnippetStatement line = getFactory().Core().createCodeSnippetStatement();
        String value = "notifyAll()";
        line.setValue(value);
        linesOfBlock.set(i,line);
      }

    }
    // Here assumption is taken that notify() is called in the first line of block
    final CtBlock<?> blocktoReplace = getFactory().Code().createCtBlock(linesOfBlock.get(0));
    element.replace(blocktoReplace);
}
}
