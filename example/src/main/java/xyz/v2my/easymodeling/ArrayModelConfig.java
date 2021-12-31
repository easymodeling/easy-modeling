package xyz.v2my.easymodeling;

@Model(type = ArrayModel.class, fields = {
        @Field(name = "anIntArray", min = 2, max = 5),
        @Field(name = "aStringArray", string = "abc"),
        @Field(name = "aShortMatrix", constant = -8),
})
public class ArrayModelConfig {
}
