package testNamespace.config {

import ext.ComponentMgr;
import ext.type.panel;
import testPackage.TestComponent;

/**
 * An ActionScript class that is not a config class.
 */
public class NonConfig extends panel {
  /**
   * @see testPackage.TestComponent
   */
  public function NonConfig(config:Object = null) {
    super(config || {});
  }
}
}