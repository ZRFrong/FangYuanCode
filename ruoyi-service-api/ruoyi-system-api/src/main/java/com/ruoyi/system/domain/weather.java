package com.ruoyi.system.domain;

public class weather {
    public class AqiDetail
    {
        private String area;

        private String no2;

        private String o3;

        private String num;

        private String so2;

        private String pm2_5;

        private String aqi;

        private String pm10;

        private String primary_pollutant;

        private String co;

        private String quality;

        private String o3_8h;

        public void setArea(String area){
            this.area = area;
        }
        public String getArea(){
            return this.area;
        }
        public void setNo2(String no2){
            this.no2 = no2;
        }
        public String getNo2(){
            return this.no2;
        }
        public void setO3(String o3){
            this.o3 = o3;
        }
        public String getO3(){
            return this.o3;
        }
        public void setNum(String num){
            this.num = num;
        }
        public String getNum(){
            return this.num;
        }
        public void setSo2(String so2){
            this.so2 = so2;
        }
        public String getSo2(){
            return this.so2;
        }
        public void setPm2_5(String pm2_5){
            this.pm2_5 = pm2_5;
        }
        public String getPm2_5(){
            return this.pm2_5;
        }
        public void setAqi(String aqi){
            this.aqi = aqi;
        }
        public String getAqi(){
            return this.aqi;
        }
        public void setPm10(String pm10){
            this.pm10 = pm10;
        }
        public String getPm10(){
            return this.pm10;
        }
        public void setPrimary_pollutant(String primary_pollutant){
            this.primary_pollutant = primary_pollutant;
        }
        public String getPrimary_pollutant(){
            return this.primary_pollutant;
        }
        public void setCo(String co){
            this.co = co;
        }
        public String getCo(){
            return this.co;
        }
        public void setQuality(String quality){
            this.quality = quality;
        }
        public String getQuality(){
            return this.quality;
        }
        public void setO3_8h(String o3_8h){
            this.o3_8h = o3_8h;
        }
        public String getO3_8h(){
            return this.o3_8h;
        }
    }

        private String temperature_time;

        private String sd;

        private String aqi;

        private String weather;

        private String temperature;

        private AqiDetail aqiDetail;

        private String wind_direction;

        private String weather_pic;

        private String weather_code;

        private String wind_power;

        public void setTemperature_time(String temperature_time){
            this.temperature_time = temperature_time;
        }
        public String getTemperature_time(){
            return this.temperature_time;
        }
        public void setSd(String sd){
            this.sd = sd;
        }
        public String getSd(){
            return this.sd;
        }
        public void setAqi(String aqi){
            this.aqi = aqi;
        }
        public String getAqi(){
            return this.aqi;
        }
        public void setWeather(String weather){
            this.weather = weather;
        }
        public String getWeather(){
            return this.weather;
        }
        public void setTemperature(String temperature){
            this.temperature = temperature;
        }
        public String getTemperature(){
            return this.temperature;
        }
        public void setAqiDetail(AqiDetail aqiDetail){
            this.aqiDetail = aqiDetail;
        }
        public AqiDetail getAqiDetail(){
            return this.aqiDetail;
        }
        public void setWind_direction(String wind_direction){
            this.wind_direction = wind_direction;
        }
        public String getWind_direction(){
            return this.wind_direction;
        }
        public void setWeather_pic(String weather_pic){
            this.weather_pic = weather_pic;
        }
        public String getWeather_pic(){
            return this.weather_pic;
        }
        public void setWeather_code(String weather_code){
            this.weather_code = weather_code;
        }
        public String getWeather_code(){
            return this.weather_code;
        }
        public void setWind_power(String wind_power){
            this.wind_power = wind_power;
        }
        public String getWind_power(){
            return this.wind_power;
        }

}
