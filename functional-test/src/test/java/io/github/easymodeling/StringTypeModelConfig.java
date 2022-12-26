package io.github.easymodeling;

@Model(type = StringTypeModel.class, fields = {
        @Field(name = "commonString", min = 10, max = 20),
        @Field(name = "alphanumericString"),
        @Field(name = "alphabeticString", alphabetic = true),
        @Field(name = "numericString", numeric = true),
        @Field(name = "constString", string = "constString"),
        @Field(name = "builder"),
})
@Model(type = Integer.class)
@Model(type = int.class)
public class StringTypeModelConfig {
}
