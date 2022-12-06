package anton.RestApi.dto;

import java.util.ArrayList;
import java.util.List;

public class MeasurementsResponse {

    private List<MeasurementDTO> measurementList;

    public List<MeasurementDTO> getMeasurementList() {
        return measurementList;
    }

    public void setMeasurementList(List<MeasurementDTO> measurementList) {
        this.measurementList = measurementList;
    }

    public MeasurementsResponse(List<MeasurementDTO> measurementList) {
        this.measurementList = measurementList;
    }

    public MeasurementsResponse() {
    }

}
