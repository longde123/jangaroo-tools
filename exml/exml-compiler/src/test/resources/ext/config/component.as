package ext.config {

[ExtConfig(target="Ext.Component")]
public class component extends component{

  /**
   * Id of the component
   */
  public native function get id():String;
  /**
   * @private
   */
  public native function set id(value:String):void;

   /**
   * the x value
   */
  public native function get x():Number;
  /**
   * @private
   */
  public native function set x(value:Number):void;
}
}