// Copyright 2013 Square, Inc.
package com.squareup.protoparser;

import com.google.auto.value.AutoValue;
import com.squareup.protoparser.MessageElement.FieldElement;
import com.squareup.protoparser.MessageElement.OneOfElement;
import java.util.Collections;
import java.util.List;

import static com.squareup.protoparser.MessageElement.validateFieldTagUniqueness;
import static com.squareup.protoparser.Utils.appendDocumentation;
import static com.squareup.protoparser.Utils.appendIndented;
import static com.squareup.protoparser.Utils.immutableCopyOf;

@AutoValue
public abstract class ExtendElement {
  public static ExtendElement create(String name, String qualifiedName, String documentation,
      List<FieldElement> fields) {
    validateFieldTagUniqueness(qualifiedName, fields, Collections.<OneOfElement>emptyList());
    return new AutoValue_ExtendElement(name, qualifiedName, documentation,
        immutableCopyOf(fields, "fields"));
  }

  ExtendElement() {
  }

  public abstract String name();
  public abstract String qualifiedName();
  public abstract String documentation();
  public abstract List<FieldElement> fields();

  @Override public final String toString() {
    StringBuilder builder = new StringBuilder();
    appendDocumentation(builder, documentation());
    builder.append("extend ")
        .append(name())
        .append(" {");
    if (!fields().isEmpty()) {
      builder.append('\n');
      for (FieldElement field : fields()) {
        appendIndented(builder, field.toString());
      }
    }
    return builder.append("}\n").toString();
  }
}