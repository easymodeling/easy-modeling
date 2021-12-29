package xyz.v2my.easymodeling;

@Model(type = DatetimeModel.class, fields = {
        @Field(name = "instant"),
        @Field(name = "now", now = true),
})
public class DatetimeModelConfig {
}
