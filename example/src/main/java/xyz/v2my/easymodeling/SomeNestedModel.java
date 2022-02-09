package xyz.v2my.easymodeling;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SomeNestedModel {

    public String string;

    public Integer integer;

    public SomeNestedModel nestedModel;

    public SomeNestedModel[] arrayOfNestedModel;
}
