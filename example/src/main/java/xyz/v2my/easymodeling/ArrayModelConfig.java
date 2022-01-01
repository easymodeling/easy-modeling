package xyz.v2my.easymodeling;

@Model(type = ArrayModel.class, fields = {
        @Field(name = "anIntArray", minLength = 2, maxLength = 5),
        @Field(name = "aStringArray", string = "abc"),
        @Field(name = "aShortMatrix", constant = -8),
        @Field(name = "aFloatArray", min = -1.1, max = 5.5),
        @Field(name = "aByteMatrix", length = 8),
})
public class ArrayModelConfig {
}
