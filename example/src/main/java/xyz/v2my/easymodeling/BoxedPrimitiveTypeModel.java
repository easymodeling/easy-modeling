package xyz.v2my.easymodeling;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoxedPrimitiveTypeModel {

    private Integer anInt;

    private Byte aByte;

    private Short aShort;

    private Long aLong;

    private Float aFloat;

    private Double aDouble;

    private Boolean aBoolean;

    private Character aChar;

    private String aString;

    public Integer getAnInt() {
        return anInt;
    }
}
