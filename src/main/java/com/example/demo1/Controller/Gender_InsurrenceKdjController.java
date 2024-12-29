package com.example.demo1.Controller;

import com.example.demo1.Model.GenderAgeKDJModel;
import com.example.demo1.Model.InsuranceClaimsKDJModel;
import com.example.demo1.Model.TestTimeTraitementKDJModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import java.util.Map;

public class Gender_InsurrenceKdjController {

    @FXML
    private StackedBarChart<String, Number> genderAgePyramidChart;

    // Insurance Claims
    @FXML
    private LineChart<String, Number> lineChart;

    // Processing Time Bar Chart
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private GenderAgeKDJModel genderAgeModel;
    private InsuranceClaimsKDJModel insuranceClaimsModel;
    private TestTimeTraitementKDJModel  testResultModel;


    @FXML
    public void initialize() {
        // Initialize models
        genderAgeModel = new GenderAgeKDJModel();
        insuranceClaimsModel = new InsuranceClaimsKDJModel();
        testResultModel = new TestTimeTraitementKDJModel();

        // Gender Age Pyramid Setup
        setupGenderAgePyramid();

        // Insurance Claims Setup
        setupInsuranceClaimsChart();

        // Processing Time Bar Chart Setup
        setupProcessingTimeChart();
    }

    private void setupGenderAgePyramid() {
        // Retrieve data and create chart series
        Map<String, Map<String, Integer>> ageGroups = genderAgeModel.getAgeGroupsBySex();
        genderAgePyramidChart.setLegendVisible(false);

        XYChart.Series<String, Number> maleSeries = new XYChart.Series<>();
        maleSeries.setName("Male");
        XYChart.Series<String, Number> femaleSeries = new XYChart.Series<>();
        femaleSeries.setName("Female");

        // Add data to series
        ageGroups.get("Male").forEach((ageGroup, count) ->
                maleSeries.getData().add(new XYChart.Data<>(ageGroup, count)));
        ageGroups.get("Female").forEach((ageGroup, count) ->
                femaleSeries.getData().add(new XYChart.Data<>(ageGroup, count)));

        // Add series to chart
        genderAgePyramidChart.getData().addAll(maleSeries, femaleSeries);

        // Styling and Tooltip
        Platform.runLater(() -> {
            for (XYChart.Data<String, Number> data : maleSeries.getData()) {
                data.getNode().setStyle("-fx-bar-fill: #424088;");
                Tooltip tooltip = new Tooltip("Male: " + data.getYValue() + " in age group " + data.getXValue());
                Tooltip.install(data.getNode(), tooltip);
            }
            for (XYChart.Data<String, Number> data : femaleSeries.getData()) {
                data.getNode().setStyle("-fx-bar-fill: #5A9BD5;");
                Tooltip tooltip = new Tooltip("Female: " + data.getYValue() + " in age group " + data.getXValue());
                Tooltip.install(data.getNode(), tooltip);
            }
        });
    }

    private void setupInsuranceClaimsChart() {
        // Retrieve data
        Map<String, Integer> claimsByYear = insuranceClaimsModel.getInsuranceClaimsByYear();

        // Create chart series
        XYChart.Series<String, Number> claimsSeries = new XYChart.Series<>();
        claimsSeries.setName("Insurance Claims");

        // Add data to series
        claimsByYear.forEach((year, claims) ->
                claimsSeries.getData().add(new XYChart.Data<>(year, claims)));

        // Add series to chart
        lineChart.getData().add(claimsSeries);
        lineChart.setLegendVisible(false);
    }

    private void setupProcessingTimeChart() {
        // Retrieve data
        double averageTime = testResultModel.getAverageProcessingTime();

        // Create chart series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Average Time", averageTime));

        // Add series to chart
        barChart.getData().add(series);

        // Customize Y-axis
        yAxis.setLabel("Time (hours)");
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(48);
        yAxis.setTickUnit(5);

        // Style bars
        series.getData().forEach(data -> {
            data.getNode().setStyle("-fx-bar-fill: #5A9BD5;");
        });
    }
}
