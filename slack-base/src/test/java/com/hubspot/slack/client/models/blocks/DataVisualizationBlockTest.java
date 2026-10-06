package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.blocks.charts.AreaChart;
import com.hubspot.slack.client.models.blocks.charts.BarChart;
import com.hubspot.slack.client.models.blocks.charts.ChartAxisConfig;
import com.hubspot.slack.client.models.blocks.charts.ChartDataPoint;
import com.hubspot.slack.client.models.blocks.charts.ChartDataSeries;
import com.hubspot.slack.client.models.blocks.charts.ChartSegment;
import com.hubspot.slack.client.models.blocks.charts.LineChart;
import com.hubspot.slack.client.models.blocks.charts.PieChart;
import java.io.IOException;
import org.junit.Test;

public class DataVisualizationBlockTest {

  private static final ObjectMapper MAPPER = ObjectMapperUtils.mapper();

  @Test
  public void itSerializesAndDeserializesPieChart() throws IOException {
    DataVisualizationBlock original = DataVisualizationBlock
      .builder()
      .setTitle("Market share")
      .setChart(
        PieChart
          .builder()
          .addSegments(ChartSegment.builder().setLabel("A").setValue(60.0).build())
          .addSegments(ChartSegment.builder().setLabel("B").setValue(40.0).build())
          .build()
      )
      .build();

    String json = MAPPER.writeValueAsString(original);
    assertThat(MAPPER.readValue(json, Block.class))
      .isInstanceOf(DataVisualizationBlock.class);

    DataVisualizationBlock roundTripped = MAPPER.readValue(
      json,
      DataVisualizationBlock.class
    );
    assertThat(roundTripped).isEqualTo(original);
    assertThat(roundTripped.getChart()).isInstanceOf(PieChart.class);
  }

  @Test
  public void itSerializesAndDeserializesBarChart() throws IOException {
    DataVisualizationBlock original = DataVisualizationBlock
      .builder()
      .setTitle("Monthly revenue")
      .setChart(
        BarChart
          .builder()
          .addSeries(
            ChartDataSeries
              .builder()
              .setName("2026")
              .addData(ChartDataPoint.builder().setLabel("Jan").setValue(5.0).build())
              .addData(ChartDataPoint.builder().setLabel("Feb").setValue(8.0).build())
              .build()
          )
          .setAxisConfig(
            ChartAxisConfig
              .builder()
              .addCategories("Jan")
              .addCategories("Feb")
              .setXLabel("Month")
              .setYLabel("Revenue")
              .build()
          )
          .build()
      )
      .build();

    String json = MAPPER.writeValueAsString(original);
    DataVisualizationBlock roundTripped = MAPPER.readValue(
      json,
      DataVisualizationBlock.class
    );
    assertThat(roundTripped).isEqualTo(original);
    assertThat(roundTripped.getChart()).isInstanceOf(BarChart.class);
  }

  @Test
  public void itRejectsTitleOver50Chars() {
    String longTitle = new String(new char[51]).replace('\0', 'a');
    assertThatThrownBy(() ->
        DataVisualizationBlock
          .builder()
          .setTitle(longTitle)
          .setChart(
            PieChart
              .builder()
              .addSegments(ChartSegment.builder().setLabel("A").setValue(1.0).build())
              .build()
          )
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("title cannot exceed 50");
  }

  @Test
  public void itRejectsEmptyPieChart() {
    assertThatThrownBy(() -> PieChart.builder().build())
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("between 1 and 12");
  }

  @Test
  public void itSerializesAndDeserializesLineChart() throws IOException {
    DataVisualizationBlock original = DataVisualizationBlock
      .builder()
      .setTitle("Latency")
      .setChart(
        LineChart
          .builder()
          .addSeries(
            ChartDataSeries
              .builder()
              .setName("p50")
              .addData(ChartDataPoint.builder().setLabel("Jan").setValue(1.0).build())
              .build()
          )
          .setAxisConfig(ChartAxisConfig.builder().addCategories("Jan").build())
          .build()
      )
      .build();
    String json = MAPPER.writeValueAsString(original);
    assertThat(json).contains("\"type\":\"line\"");
    DataVisualizationBlock roundTripped = MAPPER.readValue(
      json,
      DataVisualizationBlock.class
    );
    assertThat(roundTripped).isEqualTo(original);
    assertThat(roundTripped.getChart()).isInstanceOf(LineChart.class);
  }

  @Test
  public void itRejectsDuplicateSeriesNames() {
    assertThatThrownBy(() ->
        BarChart
          .builder()
          .addSeries(
            ChartDataSeries
              .builder()
              .setName("dup")
              .addData(ChartDataPoint.builder().setLabel("Jan").setValue(1.0).build())
              .build()
          )
          .addSeries(
            ChartDataSeries
              .builder()
              .setName("dup")
              .addData(ChartDataPoint.builder().setLabel("Feb").setValue(2.0).build())
              .build()
          )
          .setAxisConfig(ChartAxisConfig.builder().addCategories("Jan").build())
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("names must be unique");
  }

  @Test
  public void itRejectsCategoryLabelOver20Chars() {
    String longCategory = new String(new char[21]).replace('\0', 'x');
    assertThatThrownBy(() -> ChartAxisConfig.builder().addCategories(longCategory).build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("category label");
  }

  @Test
  public void itRejectsNonPositivePieSegmentValue() {
    assertThatThrownBy(() -> ChartSegment.builder().setLabel("Zero").setValue(0.0).build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("value must be > 0");
  }

  @Test
  public void itUsesAreaChartTypeDiscriminator() throws IOException {
    DataVisualizationBlock original = DataVisualizationBlock
      .builder()
      .setTitle("Area")
      .setChart(
        AreaChart
          .builder()
          .addSeries(
            ChartDataSeries
              .builder()
              .setName("s")
              .addData(ChartDataPoint.builder().setLabel("Jan").setValue(1.0).build())
              .build()
          )
          .setAxisConfig(ChartAxisConfig.builder().addCategories("Jan").build())
          .build()
      )
      .build();
    String json = MAPPER.writeValueAsString(original);
    assertThat(json).contains("\"type\":\"area\"");
    assertThat(MAPPER.readValue(json, DataVisualizationBlock.class).getChart())
      .isInstanceOf(AreaChart.class);
  }
}
