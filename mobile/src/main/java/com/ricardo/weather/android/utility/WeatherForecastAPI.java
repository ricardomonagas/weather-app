package com.ricardo.weather.android.utility;


public class WeatherForecastAPI {


    // Constants

    private static final String BASE_URL = "https://api.worldweatheronline.com/free/v2/weather.ashx";

    private static final String API_KEY = "1faf42083be500eb0c89c59655add";

    private static final String QUERY_KEY = "q=";

    private static final String API_QUERY_KEY = "key=";

    private static final String INCLUDE_LOCATION_KEY = "includeLocation=";

    private static final String FORECAST_KEY = "fx=";


    private static final String PERIOD_KEY = "tp=";

        private static final String DAY_KEY = "24";


    private static final String YES_KEY = "yes";

    private static final String NO_KEY = "no";


    public static final String TODAY_WEATHER_REQUEST_KEY = "today_weather";

    public static final String NEXT_DAYS_WEATHER_REQUEST_KEY = "next_days_weather";

    private static final String DATE_KEY = "date=";

        private static final String TODAY_KEY = "today";

    private static final String NUM_OF_DAYS_KEY = "num_of_days=";

    private static final String FORMAT_KEY = "format=";

        private static final String JSON_KEY = "json";


    private static final int NEXT_DAYS = 5;


    public static final String DATA_KEY = "data";

    public static final String VALUE_KEY = "value";

    public static final String CURRENT_CONDITION_KEY = "current_condition";

        public static final String TEMP_C_KEY = "temp_C";

        public static final String TEMP_F_KEY = "temp_F";

        public static final String TEMPC_KEY = "tempC";

        public static final String TEMPF_KEY = "tempF";

        public static final String HUMIDITY_KEY = "humidity";

        public static final String PRECIPITATION_KEY = "precipMM";

        public static final String PRESSURE_KEY = "pressure";

        public static final String WIND_SPEED_KMPH_KEY = "windspeedKmph";

        public static final String WIND_SPEED_MIPH_KEY = "windspeedMiles";

        public static final String WEATHER_DIRECTION_KEY = "winddir16Point";

        public static final String WEATHER_DESCRIPTION_KEY = "weatherDesc";

        public static final String WEATHER_ICON_URL_KEY = "weatherIconUrl";


    public static final String DATE_VALUE_KEY = "date";

    public static final String HOURLY_KEY = "hourly";


    public static final String NEAREST_AREA_KEY = "nearest_area";

        public static final String REGION_KEY = "region";

        public static final String COUNTRY_KEY = "country";


    public static final String WEATHER_KEY = "weather";





    public static String createTodayWeatherURL(float latitude, float longitude) {


        String key = getKeyString();

        String location = getLocationString(latitude, longitude);

        return BASE_URL + Utils.QUESTION_MARK + location + Utils.AND + DATE_KEY + TODAY_KEY + Utils.AND + NUM_OF_DAYS_KEY + 1 + Utils.AND + INCLUDE_LOCATION_KEY + YES_KEY + Utils.AND + FORMAT_KEY + JSON_KEY + Utils.AND + key;

    }

    public static String createNextDaysWeatherURL(float latitude, float longitude) {


        String key = getKeyString();

        String location = getLocationString(latitude, longitude);

        return BASE_URL + Utils.QUESTION_MARK + location + Utils.AND + NUM_OF_DAYS_KEY + NEXT_DAYS + Utils.AND + INCLUDE_LOCATION_KEY + YES_KEY + Utils.AND + FORECAST_KEY + YES_KEY + Utils.AND + PERIOD_KEY + DAY_KEY + Utils.AND + FORMAT_KEY + JSON_KEY + Utils.AND + key;

    }


    // Getters

    private static String getKeyString() {

        return API_QUERY_KEY + API_KEY;

    }

    public static String getLocationString(float latitude, float longitude) {

        return QUERY_KEY + latitude + "," + longitude;

    }




}
