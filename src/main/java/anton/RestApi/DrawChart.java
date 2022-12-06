package anton.RestApi;

import anton.RestApi.dto.MeasurementDTO;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.colors.ChartColor;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;


public class DrawChart {
    private static final String urlGetMeasurements = "http://192.168.0.101:8080/measurements";

    public static void main(String[] args) {

        // Собираем измерения с заданого URL
        List<Double> measurements = getMeasurements(urlGetMeasurements);
        // Строим график
        XYChart chart = drawChart(measurements);
        new SwingWrapper<XYChart>(chart).displayChart();

    }

    /**
     * Метод по сбору измерений сенсора (Measurements) методом GET
     */
    public static List<Double> getMeasurements(String url){

        RestTemplate restTemplate  = new RestTemplate();
        ResponseEntity<MeasurementDTO[]> measurementsResponse = restTemplate.getForEntity(url, MeasurementDTO[].class);
        if (measurementsResponse == null || measurementsResponse.getBody() == null){
                return Collections.emptyList();
        }
        MeasurementDTO[] measurementsDTO = measurementsResponse.getBody();
        List<Double> measurements = new ArrayList<>(Arrays.asList(measurementsDTO)).stream().map(MeasurementDTO::getValue).toList();
        return measurements;
    }


    /**
     * Метод для построения графика температур зафиксированных Сенсором
     */
    private static XYChart drawChart (List<Double> temperatures) {

        XYChart chart = new XYChartBuilder().width(800).height(600).title("График температур зафиксированных Сенсором")
                .xAxisTitle("X - Количество измерений").yAxisTitle("Y - Диапазон температур от -100 до 100 градусов").build();

        // Кастомизация графика
        chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.GREY));
        chart.getStyler().setPlotGridLinesColor(new Color(255, 255, 255));
        chart.getStyler().setChartBackgroundColor(Color.WHITE);
        chart.getStyler().setLegendBackgroundColor(Color.PINK);
        chart.getStyler().setChartFontColor(Color.MAGENTA);
        chart.getStyler().setChartTitleBoxBackgroundColor(new Color(0, 222, 0));
        chart.getStyler().setChartTitleBoxVisible(true);
        chart.getStyler().setChartTitleBoxBorderColor(Color.BLACK);
        chart.getStyler().setPlotGridLinesVisible(false);

        chart.getStyler().setAxisTickPadding(20);
        chart.getStyler().setAxisTickMarkLength(15);

        chart.getStyler().setPlotMargin(20);

        chart.getStyler().setChartTitleFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        chart.getStyler().setLegendFont(new Font(Font.SERIF, Font.PLAIN, 18));
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSE);
        chart.getStyler().setLegendSeriesLineLength(12);
        chart.getStyler().setAxisTitleFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));
        chart.getStyler().setAxisTickLabelsFont(new Font(Font.SERIF, Font.PLAIN, 11));
        chart.getStyler().setDatePattern("dd-MMM");
        chart.getStyler().setDecimalPattern("#0.000");
        chart.getStyler().setLocale(Locale.getDefault());

        /**
         *  Передаем данные осей Х и У
         */

        double[] xData = IntStream.range(0, temperatures.size()).asDoubleStream().toArray();
        double[] yData = temperatures.stream().mapToDouble(x -> x).toArray();

        // Series
        XYSeries series = chart.addSeries("Зафиксированные температуры", xData, yData);
        series.setLineColor(XChartSeriesColors.BLUE);
        series.setMarkerColor(Color.ORANGE);
        series.setMarker(SeriesMarkers.CIRCLE);
        series.setLineStyle(SeriesLines.SOLID);

        return chart;
    }
}
