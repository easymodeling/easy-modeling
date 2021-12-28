package xyz.v2my.easymodeling;

@Model(type = Order.class)
@Model(type = OrderLine.class)
@Model(type = PrimitiveTypeModel.class, fields = {
        @Field(name = "aString", min = 1, max = 12),
})
@Model(type = BoxedPrimitiveTypeModel.class)
public class EasyModelingConfig {
}
