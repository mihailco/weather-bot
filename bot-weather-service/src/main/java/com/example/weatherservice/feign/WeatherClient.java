package com.example.weatherservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.handh.model.Root;


@FeignClient(name = "weather-service", url = "https://api.openweathermap.org")
public interface WeatherClient {

    @RequestMapping(method = RequestMethod.GET, value = "/data/2.5/weather")
    Root getWeather(@RequestParam(value = "lon") double lon,
                    @RequestParam(value = "lat") double lat,
                    @RequestParam(value = "appid") String appid,
                    @RequestParam(value = "units") String units
    );
}
