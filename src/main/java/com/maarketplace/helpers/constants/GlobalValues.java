package com.maarketplace.helpers.constants;

import jakarta.validation.constraints.NotNull;

import java.lang.reflect.Field;
import java.util.Map;

public class GlobalValues {

    public final static String APP_NAME = "Marketplace";

    public final static String CHARSET = "UTF-8";

    public final static String LANG = "en-US";

    public final static String[] AUTHORS = {"EL AIDI IDRISSI Achraf, elaidiidrissiachraf@gmail.com, https://achrafidrissi.vercel.app/"};

    public final static String APP_PROTOCOL = "http";

    public final static String APP_IP = "127.0.0.1";

    public final static String APP_HOST = "localhost";

    public final static Integer APP_PORT = 80;

    public final static String APP_URL = GlobalValues.APP_PROTOCOL + "://" + GlobalValues.APP_HOST + ":" + GlobalValues.APP_PORT.toString();

    public final static String APP_REPO = "https://github.com/achrafidrissi/Marketplace";

    public final static String EMAIL_FROM = "mat.lambertucci@stud.uniroma3.it";

    public final static String DESCRIPTION = GlobalValues.APP_NAME + " site.";

    public final static String SQL_DB_NAME = "Marketplace";

    public final static String SQL_SCHEMA_NAME = "marketplace";

    public final static String ROOT_PATH = ProjectPaths.ROOT;

}
