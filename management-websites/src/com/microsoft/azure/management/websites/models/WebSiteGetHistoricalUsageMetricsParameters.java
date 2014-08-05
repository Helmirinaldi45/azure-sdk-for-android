/**
 * 
 * Copyright (c) Microsoft and contributors.  All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

// Warning: This code was generated by a tool.
// 
// Changes to this file may cause incorrect behavior and will be lost if the
// code is regenerated.

package com.microsoft.azure.management.websites.models;

import java.util.ArrayList;
import java.util.Calendar;

/**
* Parameters supplied to the Get Historical Usage Metrics Web Site operation.
*/
public class WebSiteGetHistoricalUsageMetricsParameters {
    private Calendar endTime;
    
    /**
    * Optional. The ending time of the metrics to return. If this parameter is
    * not specified, the current time is used.
    * @return The EndTime value.
    */
    public Calendar getEndTime() {
        return this.endTime;
    }
    
    /**
    * Optional. The ending time of the metrics to return. If this parameter is
    * not specified, the current time is used.
    * @param endTimeValue The EndTime value.
    */
    public void setEndTime(final Calendar endTimeValue) {
        this.endTime = endTimeValue;
    }
    
    private ArrayList<String> metricNames;
    
    /**
    * Optional. Specifies a comma-separated list of the names of the metrics to
    * return. If the names parameter is not specified, then all available
    * metrics are returned.
    * @return The MetricNames value.
    */
    public ArrayList<String> getMetricNames() {
        return this.metricNames;
    }
    
    /**
    * Optional. Specifies a comma-separated list of the names of the metrics to
    * return. If the names parameter is not specified, then all available
    * metrics are returned.
    * @param metricNamesValue The MetricNames value.
    */
    public void setMetricNames(final ArrayList<String> metricNamesValue) {
        this.metricNames = metricNamesValue;
    }
    
    private Calendar startTime;
    
    /**
    * Optional. The starting time of the metrics to return. If this parameter
    * is not specified, the beginning of the current hour is used.
    * @return The StartTime value.
    */
    public Calendar getStartTime() {
        return this.startTime;
    }
    
    /**
    * Optional. The starting time of the metrics to return. If this parameter
    * is not specified, the beginning of the current hour is used.
    * @param startTimeValue The StartTime value.
    */
    public void setStartTime(final Calendar startTimeValue) {
        this.startTime = startTimeValue;
    }
    
    /**
    * Initializes a new instance of the
    * WebSiteGetHistoricalUsageMetricsParameters class.
    *
    */
    public WebSiteGetHistoricalUsageMetricsParameters() {
        this.setMetricNames(new ArrayList<String>());
    }
}