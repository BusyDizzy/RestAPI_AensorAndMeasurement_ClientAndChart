package anton.RestApi.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


import java.time.LocalDateTime;

@JsonAutoDetect
public class MeasurementDTO {

    private Double value;
    private Boolean raining;
    private SensorDTO sensor;

    public MeasurementDTO() {
    }

    public MeasurementDTO(Double value, Boolean raining, LocalDateTime measuredAt, SensorDTO sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
