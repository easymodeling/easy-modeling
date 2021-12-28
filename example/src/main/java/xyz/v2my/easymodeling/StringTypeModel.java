package xyz.v2my.easymodeling;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StringTypeModel {

    private String commonString;

    private String alphanumericString;

    private String alphabeticString;

    private String numericString;

    private String constString;

    public String getCommonString() {
        return commonString;
    }

    public String getAlphanumericString() {
        return alphanumericString;
    }

    public String getAlphabeticString() {
        return alphabeticString;
    }

    public String getNumericString() {
        return numericString;
    }

    public String getConstString() {
        return constString;
    }
}
