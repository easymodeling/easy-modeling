package xyz.v2my.easymodeling;

@Model(type = DatetimeModel.class, fields = {
        @Field(name = "instant"),
        @Field(name = "now", now = true),
        @Field(name = "before", before = "2000-01-01T00:00:00Z"),
        @Field(name = "after", after = "2000-01-01T00:00:00Z"),
        @Field(name = "localDate", datetime = "2000-01-01T00:00:00Z"),
})
public class DatetimeModelConfig {
}
