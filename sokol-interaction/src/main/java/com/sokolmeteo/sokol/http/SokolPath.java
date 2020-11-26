package com.sokolmeteo.sokol.http;

public class SokolPath {
    private final static String MAIN = "";
    private final static String API = MAIN + "/api";
    private final static String PLATFORM_API = MAIN + "/platform/api";

    public final static class UserPath {
        private final static String MAIN = PLATFORM_API + "/user";
        public final static String LOGIN = MAIN + "/login";
        public final static String LOGOUT = MAIN + "/logout";
        public final static String REGISTER = MAIN + "/register";
        public final static String RECOVER = MAIN + "/send-repair-code";
    }

    public final static class DevicePath {
        private final static String MAIN = API + "/device";
        public final static String DEVICES = MAIN + "";
        public final static String SAVE = MAIN + "/save";
        public final static String DEVICE = MAIN + "/{id}";
        public final static String DELETE = DEVICE + "/delete";
        public final static String PARAMETERS = DEVICE + "/parameters";
    }

    public final static class AnalyticsPath {
        private final static String MAIN = API + "/analytics";
        public final static String RECORD = MAIN + "/record";
    }

    public final static class ForecastPath {
        private final static String MAIN = API + "/forecast";
        public final static String FORECASTS = MAIN + "";
    }

    public final static class AnalysePath {
        private final static String MAIN = API + "/analysis";
        public final static String ANALYSIS = MAIN + "";
        public final static String SAVE = MAIN + "/save";
        public final static String ANALYSE = MAIN + "/{id}";
        public final static String DELETE = ANALYSE + "/delete";
    }
}
