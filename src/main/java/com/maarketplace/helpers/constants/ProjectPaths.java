package com.maarketplace.helpers.constants;


import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.FileSystemResource;

public class ProjectPaths {

    public final static String ROOT = new FileSystemResource("").getFile().getAbsolutePath();

    public final static String TARGET = "/target/classes";

    public final static String SRC = "/src/main";

    public final static String RESOURCES = "/resources";

    public final static String STATIC = "/static";

    public final static String IMAGES = "/images";

    public static @NotNull String getStaticPath() {
        return ProjectPaths.ROOT + ProjectPaths.SRC + ProjectPaths.RESOURCES + ProjectPaths.STATIC;
    }

    public static @NotNull String getTargetStaticPath() {
        return ProjectPaths.ROOT + ProjectPaths.TARGET + ProjectPaths.STATIC;
    }

    public static @NotNull String getResourcesPath() {
        return ProjectPaths.getStaticPath() + ProjectPaths.IMAGES;
    }

    public static @NotNull String getImagesPath() {
        return ProjectPaths.getTargetStaticPath() + ProjectPaths.IMAGES;
    }

}
