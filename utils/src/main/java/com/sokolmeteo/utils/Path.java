package com.sokolmeteo.utils;

public class Path {
    private final static String API = "";
    public final static String LOGIN = API + "/login";
    public final static String DATA = API + "/data";

    public final static class AuthPath {
        private final static String MAIN = API + "/auth";
        public final static String LOGIN = MAIN + "/login";
        public final static String LOGOUT = MAIN + "/logout";
        public final static String REGISTRATION = MAIN + "/register";
        public final static String DELETION = MAIN + "/delete";
        public final static String RECOVERY = MAIN + "/recover";
    }

    public final static class DevicePath {
        private final static String MAIN = API + "/device";
        public final static String ALL = MAIN + "/all";
        public final static String GET_ONE = MAIN + "";
        public final static String SAVE = MAIN + "";
        public final static String DELETION = MAIN + "";
        public final static String PARAMS = MAIN + "/parameter";
    }

    public final static class WeatherRecordPath {
        private final static String MAIN = API + "/record";
        public final static String ALL = MAIN + "/all";
    }

    public final static class ForecastPath {
        private final static String MAIN = API + "/forecast";
        public final static String ALL = MAIN + "/all";
    }

    public final static class AnalysePath {
        private final static String MAIN = API + "/analyse";
        public final static String ALL = MAIN + "/all";
        public final static String SAVE = MAIN + "";
        public final static String DELETION = MAIN + "";
    }

}
