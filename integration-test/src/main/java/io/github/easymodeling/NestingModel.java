package io.github.easymodeling;

public class NestingModel {

    public String name;

    public InnerModel innerModel;

    public static class InnerModel {
        public String name;
    }
}
