package io.github.easymodeling;

@Model(type = NumericalTypeModel.class, fields = {
        @Field(name = "anInt", max = 3, constant = 11.),
        @Field(name = "aShort", max = 10, constant = 11),
        @Field(name = "aByte", constant = 11),
        @Field(name = "aLong", constant = 11),
        @Field(name = "aFloat", constant = 11),
        @Field(name = "aDouble", constant = 11),
})
public class NumericalModelConfig {
}
