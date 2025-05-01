package org.softengproj;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * class to set the data in the metrics table
 */
public class MetricTable {
  private SimpleStringProperty metric;
  private SimpleDoubleProperty computation;

  public MetricTable(String metric, double computation) {
    this.metric = new SimpleStringProperty(metric);
    this.computation = new SimpleDoubleProperty(computation);

  }

  /**
   * Getting the metric property
   * 
   * @return the metric
   */
  public String getMetric() {
    return metric.get();
  }

  /**
   * Getting the computation property
   * 
   * @return the computation
   */
  public double getComputation() {
    return computation.get();
  }

  /**
   * Setting the metric
   * 
   * @param newmetric the metric to be set
   */
  public void setMetric(String newmetric) {
    metric = new SimpleStringProperty(newmetric);
  }

  /**
   * Setting the computation
   * 
   * @param newcomp the computation to be set
   */
  public void setComputation(double newcomp) {
    computation = new SimpleDoubleProperty(newcomp);
  }

}
