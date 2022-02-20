package io.github.easymodeling;

@Model(type = StringTypeModel.class, fields = {
        @Field(name = "commonString", min = 10, max = 20),
        @Field(name = "alphanumericString"),
        @Field(name = "alphabeticString", alphabetic = true),
        @Field(name = "numericString", numeric = true),
        @Field(name = "constString", string = "constString"),
        @Field(name = "builder"),
})
public class StringTypeModelConfig {

    private String commonString;
    private String alphanumericString;
    private String alphabeticString;
    private String numericString;
    private String constString;
}
