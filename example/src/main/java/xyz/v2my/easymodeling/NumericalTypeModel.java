package xyz.v2my.easymodeling;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NumericalTypeModel {

    private Integer anInt;

    private Byte aByte;

    private Short aShort;

    private Long aLong;

    private Float aFloat;

    private Double aDouble;

    public Integer getAnInt() {
        return anInt;
    }

    public Byte getaByte() {
        return aByte;
    }

    public Short getaShort() {
        return aShort;
    }

    public Long getaLong() {
        return aLong;
    }

    public Float getaFloat() {
        return aFloat;
    }

    public Double getaDouble() {
        return aDouble;
    }
}
