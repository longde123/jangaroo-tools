package net.jangaroo.jooc.ast;

import java.io.IOException;

/**
 * Virtual AST node that is created to aggregate a complementing get and set accessor.
 */
public class PropertyDeclaration extends TypedIdeDeclaration {

  private FunctionDeclaration getter;
  private FunctionDeclaration setter;

  PropertyDeclaration(FunctionDeclaration getter, FunctionDeclaration setter) {
    super(getter.getSymModifiers(), getter.getIde(), getter.getOptTypeRelation());
    this.getter = getter;
    this.setter = setter;
  }

  public FunctionDeclaration getGetter() {
    return getter;
  }

  public FunctionDeclaration getSetter() {
    return setter;
  }

  @Override
  public boolean isClassMember() {
    return getter.isClassMember();
  }

  @Override
  public ClassDeclaration getClassDeclaration() {
    return getter.getClassDeclaration();
  }

  @Override
  public void visit(AstVisitor visitor) throws IOException {
    throw new IllegalStateException("PropertyDeclaration is virtual and must not appear in AST.");
  }

  static TypedIdeDeclaration addDeclaration(FunctionDeclaration getterOrSetter, IdeDeclaration additionalDeclaration) {
    if (additionalDeclaration instanceof PropertyDeclaration) {
      PropertyDeclaration additionalPropertyDeclaration = (PropertyDeclaration) additionalDeclaration;
      additionalDeclaration = getterOrSetter.isGetter() ? additionalPropertyDeclaration.getSetter()
              : additionalPropertyDeclaration.getGetter();
    }
    if (additionalDeclaration instanceof FunctionDeclaration) {
      FunctionDeclaration additionalFunctionDeclaration = (FunctionDeclaration) additionalDeclaration;
      if (additionalFunctionDeclaration.isGetter()) {
        if (getterOrSetter.isSetter()) {
          return new PropertyDeclaration(additionalFunctionDeclaration, getterOrSetter);
        } else {
          return getterOrSetter;
        }
      } else if (additionalFunctionDeclaration.isSetter()) {
        if (getterOrSetter.isGetter()) {
          return new PropertyDeclaration(getterOrSetter, additionalFunctionDeclaration);
        } else {
          return getterOrSetter;
        }
      }
    }
    return null;
  }
}