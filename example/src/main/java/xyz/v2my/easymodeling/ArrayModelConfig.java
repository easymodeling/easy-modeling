package xyz.v2my.easymodeling;

@Model(type = ArrayModel.class, fields = {
        @Field(name = "anIntArray", minSize = 2, maxSize = 5),
        @Field(name = "aStringArray", string = "abc"),
        @Field(name = "aShortMatrix", constant = -8),
        @Field(name = "aFloatArray", min = -1.1, max = 5.5),
        @Field(name = "aByteMatrix", size = 8),
})
public class ArrayModelConfig {
}
