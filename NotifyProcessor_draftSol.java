package sorald.processor;
import sorald.annotations.ProcessorAnnotation;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.factory.Factory;
import spoon.reflect.declaration.CtClass;
import spoon.pattern.Pattern;
import spoon.pattern.PatternBuilder;
import java.util.List;
import java.util.Map;
import java.util.*;
import spoon.pattern.Generator;

@ProcessorAnnotation( key= "S2446", description="notifyAll should be used instad of notify")


public class NotifyProcessor extends SoraldAbstractProcessor <CtSynchronized> {

@Override
protected void repairInternal(CtSynchronized element){
Factory factory = element.getFactory();
CtClass<?> threadClass = factory.Class().get(Thread.class);
Pattern p = PatternBuilder.create(threadClass.getMethodsByName("notify()").get(0).getBody().clone()).build();
Generator gen = p.generator();
Map<String, Object> bindingParameters = new HashMap<String, Object>();
toCall temp = new toCall();

bindingParameters.put( "notify()", temp);
List<CtMethod> Methods = gen.generate(bindingParameters);
}
}
class toCall {
  toCall()
  {
    this.notifyAll();
  }
}