package xyz.v2my.easymodeling;

@Model(type = NumericalTypeModel.class, fields = {
        @Field(name = "anInt", max = 3),
        @Field(name = "aShort", max = 10),
})
public class NumericalModelConfig {
}
