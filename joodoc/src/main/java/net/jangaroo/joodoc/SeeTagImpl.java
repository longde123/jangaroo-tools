/*
 * Copyright 2008 CoreMedia AG
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS
 * IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either 
 * express or implied. See the License for the specific language 
 * governing permissions and limitations under the License.
 */

package net.jangaroo.joodoc;

import com.sun.javadoc.*;

/**
 * Created by IntelliJ IDEA.
 * User: htewis
 * Date: 26.08.2004
 * Time: 11:16:48
 * To change this template use File | Settings | File Templates.
 */
public class SeeTagImpl extends TagImpl implements SeeTag{
  private final String referencedClassName;
  private final String referencedMemberName;

  public SeeTagImpl(Doc doc, String text) {
    this(doc,"@see",text);
  }

  public SeeTagImpl(Doc doc, String name, String text) {
    super(doc, "@see", name, text);
    text = text.trim();
    String className=null, memberName=null;
    int classLength = Util.getIdentifierLength(text);
    if (classLength>0) {
      className = text.substring(0,classLength);
    }
    if (text.length()>classLength+1 && text.charAt(classLength)=='#') {
      int memberLength = Util.getIdentifierLength(text.substring(classLength+1));
      if (memberLength>0) {
        memberName = text.substring(classLength+1,classLength+1+memberLength);
      }
    }
    if (className==null) {
      ClassDoc classDoc = doc instanceof ClassDoc
                          ? (ClassDoc)doc
                          : ((MemberDoc)doc).containingClass();
      className = classDoc.qualifiedName();
    }
    referencedClassName = className;
    referencedMemberName = memberName;
  }

  public ClassDoc referencedClass() {
    Doc classDoc = DocMap.getDocByQualifiedName(referencedClassName);
    return classDoc instanceof ClassDoc ? (ClassDoc)classDoc : null;
  }

  public MemberDoc referencedMember() {
    if (referencedMemberName!=null) {
      //System.out.println("referencedMember: "+referencedClassName+"#"+referencedMemberName);
      return (MemberDoc)DocMap.getDocByQualifiedName(referencedClassName+"#"+referencedMemberName);
    }
    return null;
  }

  public PackageDoc referencedPackage() {
    Doc classDoc = DocMap.getDocByQualifiedName(referencedClassName);
    return classDoc instanceof PackageDoc ? (PackageDoc)classDoc : null;
  }

  public String label() {
    return text;
  }

  public String referencedClassName() {
    return referencedClassName;
  }

  public String referencedMemberName() {
    return referencedMemberName;
  }

  public String toString() {
    return name+" class '"+referencedClassName+"' member '"+referencedMemberName+"'";
  }
}
