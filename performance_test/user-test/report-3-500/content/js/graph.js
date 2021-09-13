/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
$(document).ready(function() {

    $(".click-title").mouseenter( function(    e){
        e.preventDefault();
        this.style.cursor="pointer";
    });
    $(".click-title").mousedown( function(event){
        event.preventDefault();
    });

    // Ugly code while this script is shared among several pages
    try{
        refreshHitsPerSecond(true);
    } catch(e){}
    try{
        refreshResponseTimeOverTime(true);
    } catch(e){}
    try{
        refreshResponseTimePercentiles();
    } catch(e){}
});


var responseTimePercentilesInfos = {
        data: {"result": {"minY": 18.0, "minX": 0.0, "maxY": 21253.0, "series": [{"data": [[0.0, 18.0], [0.1, 19.0], [0.2, 19.0], [0.3, 20.0], [0.4, 20.0], [0.5, 20.0], [0.6, 20.0], [0.7, 20.0], [0.8, 20.0], [0.9, 20.0], [1.0, 20.0], [1.1, 21.0], [1.2, 21.0], [1.3, 21.0], [1.4, 21.0], [1.5, 21.0], [1.6, 21.0], [1.7, 21.0], [1.8, 21.0], [1.9, 21.0], [2.0, 21.0], [2.1, 21.0], [2.2, 21.0], [2.3, 21.0], [2.4, 21.0], [2.5, 21.0], [2.6, 21.0], [2.7, 21.0], [2.8, 21.0], [2.9, 21.0], [3.0, 22.0], [3.1, 22.0], [3.2, 22.0], [3.3, 22.0], [3.4, 22.0], [3.5, 22.0], [3.6, 22.0], [3.7, 22.0], [3.8, 22.0], [3.9, 22.0], [4.0, 22.0], [4.1, 22.0], [4.2, 22.0], [4.3, 22.0], [4.4, 22.0], [4.5, 22.0], [4.6, 22.0], [4.7, 22.0], [4.8, 22.0], [4.9, 22.0], [5.0, 22.0], [5.1, 22.0], [5.2, 22.0], [5.3, 22.0], [5.4, 23.0], [5.5, 23.0], [5.6, 23.0], [5.7, 23.0], [5.8, 23.0], [5.9, 23.0], [6.0, 23.0], [6.1, 23.0], [6.2, 23.0], [6.3, 23.0], [6.4, 23.0], [6.5, 23.0], [6.6, 23.0], [6.7, 23.0], [6.8, 23.0], [6.9, 23.0], [7.0, 23.0], [7.1, 23.0], [7.2, 23.0], [7.3, 23.0], [7.4, 23.0], [7.5, 23.0], [7.6, 23.0], [7.7, 23.0], [7.8, 23.0], [7.9, 23.0], [8.0, 23.0], [8.1, 24.0], [8.2, 24.0], [8.3, 24.0], [8.4, 24.0], [8.5, 24.0], [8.6, 24.0], [8.7, 24.0], [8.8, 24.0], [8.9, 24.0], [9.0, 24.0], [9.1, 24.0], [9.2, 24.0], [9.3, 24.0], [9.4, 24.0], [9.5, 24.0], [9.6, 24.0], [9.7, 24.0], [9.8, 24.0], [9.9, 24.0], [10.0, 24.0], [10.1, 24.0], [10.2, 24.0], [10.3, 24.0], [10.4, 24.0], [10.5, 24.0], [10.6, 24.0], [10.7, 24.0], [10.8, 24.0], [10.9, 24.0], [11.0, 24.0], [11.1, 24.0], [11.2, 25.0], [11.3, 25.0], [11.4, 25.0], [11.5, 25.0], [11.6, 25.0], [11.7, 25.0], [11.8, 25.0], [11.9, 25.0], [12.0, 25.0], [12.1, 25.0], [12.2, 25.0], [12.3, 25.0], [12.4, 25.0], [12.5, 25.0], [12.6, 25.0], [12.7, 25.0], [12.8, 25.0], [12.9, 25.0], [13.0, 25.0], [13.1, 25.0], [13.2, 25.0], [13.3, 25.0], [13.4, 25.0], [13.5, 25.0], [13.6, 25.0], [13.7, 25.0], [13.8, 25.0], [13.9, 25.0], [14.0, 25.0], [14.1, 25.0], [14.2, 25.0], [14.3, 25.0], [14.4, 25.0], [14.5, 26.0], [14.6, 26.0], [14.7, 26.0], [14.8, 26.0], [14.9, 26.0], [15.0, 26.0], [15.1, 26.0], [15.2, 26.0], [15.3, 26.0], [15.4, 26.0], [15.5, 26.0], [15.6, 26.0], [15.7, 26.0], [15.8, 26.0], [15.9, 26.0], [16.0, 26.0], [16.1, 26.0], [16.2, 26.0], [16.3, 26.0], [16.4, 26.0], [16.5, 26.0], [16.6, 26.0], [16.7, 26.0], [16.8, 26.0], [16.9, 26.0], [17.0, 26.0], [17.1, 26.0], [17.2, 26.0], [17.3, 26.0], [17.4, 26.0], [17.5, 26.0], [17.6, 26.0], [17.7, 26.0], [17.8, 26.0], [17.9, 26.0], [18.0, 26.0], [18.1, 26.0], [18.2, 26.0], [18.3, 26.0], [18.4, 27.0], [18.5, 27.0], [18.6, 27.0], [18.7, 27.0], [18.8, 27.0], [18.9, 27.0], [19.0, 27.0], [19.1, 27.0], [19.2, 27.0], [19.3, 27.0], [19.4, 27.0], [19.5, 27.0], [19.6, 27.0], [19.7, 27.0], [19.8, 27.0], [19.9, 27.0], [20.0, 27.0], [20.1, 27.0], [20.2, 27.0], [20.3, 27.0], [20.4, 27.0], [20.5, 27.0], [20.6, 27.0], [20.7, 27.0], [20.8, 27.0], [20.9, 27.0], [21.0, 27.0], [21.1, 27.0], [21.2, 27.0], [21.3, 27.0], [21.4, 28.0], [21.5, 28.0], [21.6, 28.0], [21.7, 28.0], [21.8, 28.0], [21.9, 28.0], [22.0, 28.0], [22.1, 28.0], [22.2, 28.0], [22.3, 28.0], [22.4, 28.0], [22.5, 28.0], [22.6, 28.0], [22.7, 28.0], [22.8, 28.0], [22.9, 28.0], [23.0, 28.0], [23.1, 28.0], [23.2, 28.0], [23.3, 28.0], [23.4, 28.0], [23.5, 28.0], [23.6, 28.0], [23.7, 28.0], [23.8, 28.0], [23.9, 28.0], [24.0, 28.0], [24.1, 28.0], [24.2, 28.0], [24.3, 28.0], [24.4, 28.0], [24.5, 29.0], [24.6, 29.0], [24.7, 29.0], [24.8, 29.0], [24.9, 29.0], [25.0, 29.0], [25.1, 29.0], [25.2, 29.0], [25.3, 29.0], [25.4, 29.0], [25.5, 29.0], [25.6, 29.0], [25.7, 29.0], [25.8, 29.0], [25.9, 29.0], [26.0, 29.0], [26.1, 29.0], [26.2, 29.0], [26.3, 29.0], [26.4, 29.0], [26.5, 29.0], [26.6, 29.0], [26.7, 29.0], [26.8, 29.0], [26.9, 29.0], [27.0, 29.0], [27.1, 29.0], [27.2, 30.0], [27.3, 30.0], [27.4, 30.0], [27.5, 30.0], [27.6, 30.0], [27.7, 30.0], [27.8, 30.0], [27.9, 30.0], [28.0, 30.0], [28.1, 30.0], [28.2, 30.0], [28.3, 30.0], [28.4, 30.0], [28.5, 30.0], [28.6, 30.0], [28.7, 30.0], [28.8, 30.0], [28.9, 30.0], [29.0, 30.0], [29.1, 30.0], [29.2, 30.0], [29.3, 30.0], [29.4, 30.0], [29.5, 30.0], [29.6, 30.0], [29.7, 30.0], [29.8, 30.0], [29.9, 30.0], [30.0, 31.0], [30.1, 31.0], [30.2, 31.0], [30.3, 31.0], [30.4, 31.0], [30.5, 31.0], [30.6, 31.0], [30.7, 31.0], [30.8, 31.0], [30.9, 31.0], [31.0, 31.0], [31.1, 31.0], [31.2, 31.0], [31.3, 31.0], [31.4, 31.0], [31.5, 31.0], [31.6, 31.0], [31.7, 31.0], [31.8, 31.0], [31.9, 31.0], [32.0, 31.0], [32.1, 31.0], [32.2, 31.0], [32.3, 32.0], [32.4, 32.0], [32.5, 32.0], [32.6, 32.0], [32.7, 32.0], [32.8, 32.0], [32.9, 32.0], [33.0, 32.0], [33.1, 32.0], [33.2, 32.0], [33.3, 32.0], [33.4, 32.0], [33.5, 32.0], [33.6, 32.0], [33.7, 32.0], [33.8, 32.0], [33.9, 32.0], [34.0, 32.0], [34.1, 32.0], [34.2, 32.0], [34.3, 32.0], [34.4, 32.0], [34.5, 32.0], [34.6, 33.0], [34.7, 33.0], [34.8, 33.0], [34.9, 33.0], [35.0, 33.0], [35.1, 33.0], [35.2, 33.0], [35.3, 33.0], [35.4, 33.0], [35.5, 33.0], [35.6, 33.0], [35.7, 33.0], [35.8, 33.0], [35.9, 33.0], [36.0, 33.0], [36.1, 33.0], [36.2, 33.0], [36.3, 33.0], [36.4, 33.0], [36.5, 33.0], [36.6, 33.0], [36.7, 33.0], [36.8, 34.0], [36.9, 34.0], [37.0, 34.0], [37.1, 34.0], [37.2, 34.0], [37.3, 34.0], [37.4, 34.0], [37.5, 34.0], [37.6, 34.0], [37.7, 34.0], [37.8, 34.0], [37.9, 34.0], [38.0, 34.0], [38.1, 34.0], [38.2, 34.0], [38.3, 34.0], [38.4, 34.0], [38.5, 34.0], [38.6, 34.0], [38.7, 34.0], [38.8, 34.0], [38.9, 35.0], [39.0, 35.0], [39.1, 35.0], [39.2, 35.0], [39.3, 35.0], [39.4, 35.0], [39.5, 35.0], [39.6, 35.0], [39.7, 35.0], [39.8, 35.0], [39.9, 35.0], [40.0, 35.0], [40.1, 35.0], [40.2, 35.0], [40.3, 35.0], [40.4, 36.0], [40.5, 36.0], [40.6, 36.0], [40.7, 36.0], [40.8, 36.0], [40.9, 36.0], [41.0, 36.0], [41.1, 36.0], [41.2, 36.0], [41.3, 36.0], [41.4, 36.0], [41.5, 36.0], [41.6, 36.0], [41.7, 36.0], [41.8, 36.0], [41.9, 37.0], [42.0, 37.0], [42.1, 37.0], [42.2, 37.0], [42.3, 37.0], [42.4, 37.0], [42.5, 37.0], [42.6, 37.0], [42.7, 37.0], [42.8, 37.0], [42.9, 38.0], [43.0, 38.0], [43.1, 38.0], [43.2, 38.0], [43.3, 38.0], [43.4, 38.0], [43.5, 38.0], [43.6, 38.0], [43.7, 38.0], [43.8, 39.0], [43.9, 39.0], [44.0, 39.0], [44.1, 39.0], [44.2, 39.0], [44.3, 39.0], [44.4, 39.0], [44.5, 39.0], [44.6, 39.0], [44.7, 39.0], [44.8, 39.0], [44.9, 39.0], [45.0, 40.0], [45.1, 40.0], [45.2, 40.0], [45.3, 40.0], [45.4, 40.0], [45.5, 40.0], [45.6, 40.0], [45.7, 40.0], [45.8, 40.0], [45.9, 40.0], [46.0, 40.0], [46.1, 40.0], [46.2, 40.0], [46.3, 41.0], [46.4, 41.0], [46.5, 41.0], [46.6, 41.0], [46.7, 41.0], [46.8, 41.0], [46.9, 41.0], [47.0, 41.0], [47.1, 41.0], [47.2, 42.0], [47.3, 42.0], [47.4, 42.0], [47.5, 42.0], [47.6, 42.0], [47.7, 42.0], [47.8, 42.0], [47.9, 42.0], [48.0, 42.0], [48.1, 42.0], [48.2, 43.0], [48.3, 43.0], [48.4, 43.0], [48.5, 43.0], [48.6, 43.0], [48.7, 43.0], [48.8, 43.0], [48.9, 43.0], [49.0, 43.0], [49.1, 43.0], [49.2, 43.0], [49.3, 43.0], [49.4, 44.0], [49.5, 44.0], [49.6, 44.0], [49.7, 44.0], [49.8, 44.0], [49.9, 44.0], [50.0, 44.0], [50.1, 44.0], [50.2, 45.0], [50.3, 45.0], [50.4, 45.0], [50.5, 45.0], [50.6, 45.0], [50.7, 45.0], [50.8, 45.0], [50.9, 45.0], [51.0, 46.0], [51.1, 46.0], [51.2, 46.0], [51.3, 46.0], [51.4, 46.0], [51.5, 46.0], [51.6, 46.0], [51.7, 47.0], [51.8, 47.0], [51.9, 47.0], [52.0, 47.0], [52.1, 47.0], [52.2, 48.0], [52.3, 48.0], [52.4, 48.0], [52.5, 48.0], [52.6, 48.0], [52.7, 48.0], [52.8, 48.0], [52.9, 49.0], [53.0, 49.0], [53.1, 49.0], [53.2, 49.0], [53.3, 49.0], [53.4, 49.0], [53.5, 49.0], [53.6, 49.0], [53.7, 50.0], [53.8, 50.0], [53.9, 50.0], [54.0, 50.0], [54.1, 50.0], [54.2, 50.0], [54.3, 50.0], [54.4, 50.0], [54.5, 50.0], [54.6, 51.0], [54.7, 51.0], [54.8, 51.0], [54.9, 51.0], [55.0, 51.0], [55.1, 51.0], [55.2, 52.0], [55.3, 52.0], [55.4, 52.0], [55.5, 52.0], [55.6, 53.0], [55.7, 53.0], [55.8, 53.0], [55.9, 53.0], [56.0, 53.0], [56.1, 53.0], [56.2, 53.0], [56.3, 54.0], [56.4, 54.0], [56.5, 54.0], [56.6, 54.0], [56.7, 54.0], [56.8, 54.0], [56.9, 55.0], [57.0, 55.0], [57.1, 55.0], [57.2, 55.0], [57.3, 56.0], [57.4, 56.0], [57.5, 56.0], [57.6, 56.0], [57.7, 56.0], [57.8, 56.0], [57.9, 57.0], [58.0, 57.0], [58.1, 57.0], [58.2, 57.0], [58.3, 57.0], [58.4, 58.0], [58.5, 58.0], [58.6, 59.0], [58.7, 59.0], [58.8, 59.0], [58.9, 60.0], [59.0, 60.0], [59.1, 60.0], [59.2, 60.0], [59.3, 60.0], [59.4, 61.0], [59.5, 61.0], [59.6, 61.0], [59.7, 61.0], [59.8, 62.0], [59.9, 62.0], [60.0, 62.0], [60.1, 63.0], [60.2, 63.0], [60.3, 63.0], [60.4, 63.0], [60.5, 64.0], [60.6, 64.0], [60.7, 64.0], [60.8, 64.0], [60.9, 64.0], [61.0, 65.0], [61.1, 65.0], [61.2, 65.0], [61.3, 65.0], [61.4, 65.0], [61.5, 66.0], [61.6, 66.0], [61.7, 66.0], [61.8, 67.0], [61.9, 67.0], [62.0, 67.0], [62.1, 68.0], [62.2, 68.0], [62.3, 69.0], [62.4, 69.0], [62.5, 69.0], [62.6, 69.0], [62.7, 70.0], [62.8, 70.0], [62.9, 71.0], [63.0, 71.0], [63.1, 71.0], [63.2, 72.0], [63.3, 72.0], [63.4, 72.0], [63.5, 72.0], [63.6, 73.0], [63.7, 73.0], [63.8, 74.0], [63.9, 74.0], [64.0, 74.0], [64.1, 75.0], [64.2, 75.0], [64.3, 76.0], [64.4, 77.0], [64.5, 77.0], [64.6, 78.0], [64.7, 79.0], [64.8, 79.0], [64.9, 79.0], [65.0, 79.0], [65.1, 80.0], [65.2, 80.0], [65.3, 81.0], [65.4, 81.0], [65.5, 81.0], [65.6, 82.0], [65.7, 82.0], [65.8, 83.0], [65.9, 83.0], [66.0, 84.0], [66.1, 84.0], [66.2, 85.0], [66.3, 85.0], [66.4, 86.0], [66.5, 86.0], [66.6, 87.0], [66.7, 87.0], [66.8, 88.0], [66.9, 89.0], [67.0, 89.0], [67.1, 90.0], [67.2, 90.0], [67.3, 90.0], [67.4, 91.0], [67.5, 91.0], [67.6, 91.0], [67.7, 92.0], [67.8, 93.0], [67.9, 94.0], [68.0, 94.0], [68.1, 94.0], [68.2, 95.0], [68.3, 96.0], [68.4, 97.0], [68.5, 97.0], [68.6, 98.0], [68.7, 99.0], [68.8, 100.0], [68.9, 103.0], [69.0, 103.0], [69.1, 105.0], [69.2, 106.0], [69.3, 107.0], [69.4, 108.0], [69.5, 111.0], [69.6, 112.0], [69.7, 112.0], [69.8, 113.0], [69.9, 114.0], [70.0, 114.0], [70.1, 117.0], [70.2, 118.0], [70.3, 121.0], [70.4, 122.0], [70.5, 125.0], [70.6, 128.0], [70.7, 132.0], [70.8, 135.0], [70.9, 145.0], [71.0, 152.0], [71.1, 164.0], [71.2, 173.0], [71.3, 191.0], [71.4, 195.0], [71.5, 198.0], [71.6, 208.0], [71.7, 209.0], [71.8, 218.0], [71.9, 222.0], [72.0, 229.0], [72.1, 232.0], [72.2, 235.0], [72.3, 236.0], [72.4, 239.0], [72.5, 243.0], [72.6, 248.0], [72.7, 254.0], [72.8, 258.0], [72.9, 259.0], [73.0, 261.0], [73.1, 262.0], [73.2, 265.0], [73.3, 265.0], [73.4, 268.0], [73.5, 273.0], [73.6, 274.0], [73.7, 274.0], [73.8, 276.0], [73.9, 277.0], [74.0, 279.0], [74.1, 283.0], [74.2, 288.0], [74.3, 289.0], [74.4, 293.0], [74.5, 294.0], [74.6, 296.0], [74.7, 302.0], [74.8, 304.0], [74.9, 309.0], [75.0, 310.0], [75.1, 311.0], [75.2, 312.0], [75.3, 318.0], [75.4, 323.0], [75.5, 329.0], [75.6, 331.0], [75.7, 333.0], [75.8, 340.0], [75.9, 347.0], [76.0, 351.0], [76.1, 366.0], [76.2, 367.0], [76.3, 370.0], [76.4, 376.0], [76.5, 378.0], [76.6, 381.0], [76.7, 384.0], [76.8, 391.0], [76.9, 391.0], [77.0, 395.0], [77.1, 400.0], [77.2, 404.0], [77.3, 407.0], [77.4, 410.0], [77.5, 418.0], [77.6, 425.0], [77.7, 429.0], [77.8, 437.0], [77.9, 445.0], [78.0, 456.0], [78.1, 458.0], [78.2, 471.0], [78.3, 495.0], [78.4, 508.0], [78.5, 514.0], [78.6, 520.0], [78.7, 533.0], [78.8, 537.0], [78.9, 539.0], [79.0, 540.0], [79.1, 546.0], [79.2, 548.0], [79.3, 550.0], [79.4, 554.0], [79.5, 557.0], [79.6, 563.0], [79.7, 565.0], [79.8, 571.0], [79.9, 578.0], [80.0, 579.0], [80.1, 585.0], [80.2, 587.0], [80.3, 591.0], [80.4, 599.0], [80.5, 600.0], [80.6, 601.0], [80.7, 609.0], [80.8, 612.0], [80.9, 615.0], [81.0, 625.0], [81.1, 628.0], [81.2, 638.0], [81.3, 641.0], [81.4, 642.0], [81.5, 645.0], [81.6, 648.0], [81.7, 649.0], [81.8, 650.0], [81.9, 661.0], [82.0, 665.0], [82.1, 668.0], [82.2, 670.0], [82.3, 672.0], [82.4, 674.0], [82.5, 676.0], [82.6, 679.0], [82.7, 681.0], [82.8, 682.0], [82.9, 682.0], [83.0, 683.0], [83.1, 685.0], [83.2, 686.0], [83.3, 688.0], [83.4, 690.0], [83.5, 691.0], [83.6, 694.0], [83.7, 696.0], [83.8, 699.0], [83.9, 700.0], [84.0, 701.0], [84.1, 702.0], [84.2, 703.0], [84.3, 709.0], [84.4, 713.0], [84.5, 715.0], [84.6, 715.0], [84.7, 716.0], [84.8, 721.0], [84.9, 725.0], [85.0, 727.0], [85.1, 733.0], [85.2, 734.0], [85.3, 737.0], [85.4, 742.0], [85.5, 744.0], [85.6, 749.0], [85.7, 752.0], [85.8, 753.0], [85.9, 757.0], [86.0, 757.0], [86.1, 762.0], [86.2, 763.0], [86.3, 766.0], [86.4, 769.0], [86.5, 771.0], [86.6, 772.0], [86.7, 773.0], [86.8, 776.0], [86.9, 777.0], [87.0, 780.0], [87.1, 782.0], [87.2, 783.0], [87.3, 785.0], [87.4, 786.0], [87.5, 787.0], [87.6, 788.0], [87.7, 790.0], [87.8, 792.0], [87.9, 795.0], [88.0, 795.0], [88.1, 800.0], [88.2, 802.0], [88.3, 803.0], [88.4, 806.0], [88.5, 810.0], [88.6, 813.0], [88.7, 814.0], [88.8, 822.0], [88.9, 825.0], [89.0, 830.0], [89.1, 838.0], [89.2, 845.0], [89.3, 847.0], [89.4, 851.0], [89.5, 856.0], [89.6, 859.0], [89.7, 865.0], [89.8, 865.0], [89.9, 878.0], [90.0, 893.0], [90.1, 899.0], [90.2, 905.0], [90.3, 906.0], [90.4, 908.0], [90.5, 911.0], [90.6, 912.0], [90.7, 913.0], [90.8, 917.0], [90.9, 920.0], [91.0, 928.0], [91.1, 931.0], [91.2, 932.0], [91.3, 944.0], [91.4, 945.0], [91.5, 947.0], [91.6, 952.0], [91.7, 956.0], [91.8, 957.0], [91.9, 963.0], [92.0, 971.0], [92.1, 974.0], [92.2, 978.0], [92.3, 983.0], [92.4, 985.0], [92.5, 989.0], [92.6, 1002.0], [92.7, 1003.0], [92.8, 1007.0], [92.9, 1009.0], [93.0, 1013.0], [93.1, 1014.0], [93.2, 1015.0], [93.3, 1018.0], [93.4, 1019.0], [93.5, 1020.0], [93.6, 1027.0], [93.7, 1033.0], [93.8, 1043.0], [93.9, 1049.0], [94.0, 1051.0], [94.1, 1053.0], [94.2, 1053.0], [94.3, 1059.0], [94.4, 1062.0], [94.5, 1065.0], [94.6, 1066.0], [94.7, 1069.0], [94.8, 1070.0], [94.9, 1078.0], [95.0, 1083.0], [95.1, 1086.0], [95.2, 1087.0], [95.3, 1094.0], [95.4, 1102.0], [95.5, 1110.0], [95.6, 1115.0], [95.7, 1118.0], [95.8, 1118.0], [95.9, 1123.0], [96.0, 1127.0], [96.1, 1133.0], [96.2, 1136.0], [96.3, 1142.0], [96.4, 1149.0], [96.5, 1151.0], [96.6, 1154.0], [96.7, 1155.0], [96.8, 1161.0], [96.9, 1172.0], [97.0, 1183.0], [97.1, 1183.0], [97.2, 1190.0], [97.3, 1205.0], [97.4, 1214.0], [97.5, 1224.0], [97.6, 1233.0], [97.7, 1253.0], [97.8, 1256.0], [97.9, 1265.0], [98.0, 1272.0], [98.1, 1273.0], [98.2, 1289.0], [98.3, 1305.0], [98.4, 1307.0], [98.5, 1309.0], [98.6, 1315.0], [98.7, 1334.0], [98.8, 1337.0], [98.9, 1338.0], [99.0, 1548.0], [99.1, 1557.0], [99.2, 1566.0], [99.3, 1576.0], [99.4, 1588.0], [99.5, 1655.0], [99.6, 1657.0], [99.7, 2067.0], [99.8, 2094.0], [99.9, 21241.0]], "isOverall": false, "label": "HTTP请求", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 100.0, "title": "Response Time Percentiles"}},
        getOptions: function() {
            return {
                series: {
                    points: { show: false }
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimePercentiles'
                },
                xaxis: {
                    tickDecimals: 1,
                    axisLabel: "Percentiles",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Percentile value in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : %x.2 percentile was %y ms"
                },
                selection: { mode: "xy" },
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimePercentiles"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimesPercentiles"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimesPercentiles"), dataset, prepareOverviewOptions(options));
        }
};

/**
 * @param elementId Id of element where we display message
 */
function setEmptyGraph(elementId) {
    $(function() {
        $(elementId).text("No graph series with filter="+seriesFilter);
    });
}

// Response times percentiles
function refreshResponseTimePercentiles() {
    var infos = responseTimePercentilesInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimePercentiles");
        return;
    }
    if (isGraph($("#flotResponseTimesPercentiles"))){
        infos.createGraph();
    } else {
        var choiceContainer = $("#choicesResponseTimePercentiles");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimesPercentiles", "#overviewResponseTimesPercentiles");
        $('#bodyResponseTimePercentiles .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimeDistributionInfos = {
        data: {"result": {"minY": 1.0, "minX": 0.0, "maxY": 1374.0, "series": [{"data": [[0.0, 1374.0], [2100.0, 1.0], [600.0, 67.0], [700.0, 85.0], [200.0, 63.0], [800.0, 41.0], [900.0, 48.0], [1000.0, 57.0], [1100.0, 38.0], [300.0, 48.0], [1200.0, 20.0], [1300.0, 13.0], [21200.0, 2.0], [1500.0, 10.0], [100.0, 57.0], [400.0, 26.0], [1600.0, 5.0], [500.0, 42.0], [2000.0, 3.0]], "isOverall": false, "label": "HTTP请求", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 100, "maxX": 21200.0, "title": "Response Time Distribution"}},
        getOptions: function() {
            var granularity = this.data.result.granularity;
            return {
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimeDistribution'
                },
                xaxis:{
                    axisLabel: "Response times in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of responses",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                bars : {
                    show: true,
                    barWidth: this.data.result.granularity
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: function(label, xval, yval, flotItem){
                        return yval + " responses for " + label + " were between " + xval + " and " + (xval + granularity) + " ms";
                    }
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimeDistribution"), prepareData(data.result.series, $("#choicesResponseTimeDistribution")), options);
        }

};

// Response time distribution
function refreshResponseTimeDistribution() {
    var infos = responseTimeDistributionInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimeDistribution");
        return;
    }
    if (isGraph($("#flotResponseTimeDistribution"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimeDistribution");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        $('#footerResponseTimeDistribution .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var syntheticResponseTimeDistributionInfos = {
        data: {"result": {"minY": 19.0, "minX": 0.0, "ticks": [[0, "Requests having \nresponse time <= 500ms"], [1, "Requests having \nresponse time > 500ms and <= 1,500ms"], [2, "Requests having \nresponse time > 1,500ms"], [3, "Requests in error"]], "maxY": 1349.0, "series": [{"data": [[0.0, 1349.0]], "color": "#9ACD32", "isOverall": false, "label": "Requests having \nresponse time <= 500ms", "isController": false}, {"data": [[1.0, 130.0]], "color": "yellow", "isOverall": false, "label": "Requests having \nresponse time > 500ms and <= 1,500ms", "isController": false}, {"data": [[2.0, 19.0]], "color": "orange", "isOverall": false, "label": "Requests having \nresponse time > 1,500ms", "isController": false}, {"data": [[3.0, 502.0]], "color": "#FF6347", "isOverall": false, "label": "Requests in error", "isController": false}], "supportsControllersDiscrimination": false, "maxX": 3.0, "title": "Synthetic Response Times Distribution"}},
        getOptions: function() {
            return {
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendSyntheticResponseTimeDistribution'
                },
                xaxis:{
                    axisLabel: "Response times ranges",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                    tickLength:0,
                    min:-0.5,
                    max:3.5
                },
                yaxis: {
                    axisLabel: "Number of responses",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                bars : {
                    show: true,
                    align: "center",
                    barWidth: 0.25,
                    fill:.75
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: function(label, xval, yval, flotItem){
                        return yval + " " + label;
                    }
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var options = this.getOptions();
            prepareOptions(options, data);
            options.xaxis.ticks = data.result.ticks;
            $.plot($("#flotSyntheticResponseTimeDistribution"), prepareData(data.result.series, $("#choicesSyntheticResponseTimeDistribution")), options);
        }

};

// Response time distribution
function refreshSyntheticResponseTimeDistribution() {
    var infos = syntheticResponseTimeDistributionInfos;
    prepareSeries(infos.data, true);
    if (isGraph($("#flotSyntheticResponseTimeDistribution"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesSyntheticResponseTimeDistribution");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        $('#footerSyntheticResponseTimeDistribution .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var activeThreadsOverTimeInfos = {
        data: {"result": {"minY": 1.0, "minX": 1.63089642E12, "maxY": 405.4480000000002, "series": [{"data": [[1.63089648E12, 1.0], [1.63089642E12, 17.903807615230463]], "isOverall": false, "label": "sendEmail", "isController": false}, {"data": [[1.63089648E12, 16.673999999999985]], "isOverall": false, "label": "reset", "isController": false}, {"data": [[1.63089642E12, 405.4480000000002]], "isOverall": false, "label": "login", "isController": false}, {"data": [[1.63089648E12, 170.92400000000012]], "isOverall": false, "label": "signup", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.63089648E12, "title": "Active Threads Over Time"}},
        getOptions: function() {
            return {
                series: {
                    stack: true,
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of active threads",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 6,
                    show: true,
                    container: '#legendActiveThreadsOverTime'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                selection: {
                    mode: 'xy'
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : At %x there were %y active threads"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesActiveThreadsOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotActiveThreadsOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewActiveThreadsOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Active Threads Over Time
function refreshActiveThreadsOverTime(fixTimestamps) {
    var infos = activeThreadsOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotActiveThreadsOverTime"))) {
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesActiveThreadsOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotActiveThreadsOverTime", "#overviewActiveThreadsOverTime");
        $('#footerActiveThreadsOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var timeVsThreadsInfos = {
        data: {"result": {"minY": 24.860000000000007, "minX": 1.0, "maxY": 14174.666666666666, "series": [{"data": [[2.0, 25.5], [3.0, 25.645161290322584], [4.0, 27.575757575757574], [5.0, 24.860000000000007], [6.0, 26.116666666666667], [7.0, 27.269841269841262], [8.0, 71.26], [9.0, 31.133333333333336], [10.0, 49.01960784313725], [11.0, 222.59183673469386], [12.0, 30.35555555555555], [13.0, 45.548387096774185], [14.0, 54.95652173913042], [15.0, 52.037735849056595], [16.0, 45.66666666666667], [17.0, 44.97727272727273], [18.0, 40.975609756097555], [19.0, 39.92000000000001], [20.0, 41.57894736842106], [21.0, 36.94117647058823], [22.0, 31.11111111111111], [23.0, 44.357142857142854], [24.0, 38.92307692307692], [25.0, 31.599999999999998], [26.0, 40.90909090909091], [27.0, 59.99999999999999], [28.0, 35.8], [29.0, 49.46153846153847], [30.0, 48.75], [31.0, 58.666666666666664], [33.0, 41.91666666666668], [32.0, 54.800000000000004], [34.0, 37.25000000000001], [35.0, 37.90909090909091], [37.0, 44.8], [36.0, 44.041666666666664], [38.0, 46.545454545454554], [39.0, 31.2], [40.0, 39.699999999999996], [41.0, 44.0], [43.0, 44.699999999999996], [42.0, 28.5], [45.0, 40.6], [44.0, 59.5], [47.0, 52.75], [46.0, 34.14285714285714], [48.0, 97.2], [49.0, 74.5], [57.0, 111.5], [63.0, 166.0], [65.0, 173.0], [75.0, 266.5], [74.0, 104.0], [77.0, 205.5], [78.0, 220.0], [76.0, 237.5], [87.0, 1003.0], [86.0, 1058.0], [85.0, 1017.0], [90.0, 993.5], [88.0, 1007.0], [95.0, 989.0], [93.0, 993.0], [99.0, 958.6666666666666], [97.0, 962.0], [96.0, 947.0], [103.0, 929.5], [102.0, 937.5], [100.0, 971.0], [106.0, 939.0], [104.0, 952.0], [109.0, 588.0], [111.0, 908.0], [110.0, 910.5], [108.0, 906.5], [112.0, 276.0], [114.0, 189.0], [146.0, 296.0], [167.0, 716.0], [166.0, 793.6875000000001], [165.0, 878.0], [175.0, 703.5], [173.0, 722.0], [171.0, 704.6666666666666], [170.0, 692.0], [168.0, 758.5], [182.0, 534.0], [181.0, 624.0], [178.0, 597.0], [177.0, 686.0], [176.0, 700.0], [191.0, 366.0], [189.0, 419.25], [187.0, 542.0], [186.0, 532.6666666666666], [184.0, 522.6666666666666], [199.0, 294.0], [198.0, 372.0], [197.0, 232.0], [196.0, 414.0], [195.0, 446.0], [194.0, 388.0], [192.0, 427.5], [207.0, 299.5], [206.0, 376.0], [205.0, 323.0], [203.0, 333.0], [202.0, 285.0], [201.0, 330.5], [215.0, 422.0], [214.0, 457.0], [213.0, 270.3333333333333], [212.0, 367.0], [211.0, 338.3333333333333], [209.0, 336.5], [208.0, 404.0], [222.0, 1184.0], [220.0, 431.0], [218.0, 283.0], [217.0, 440.0], [216.0, 496.0], [231.0, 1053.0], [230.0, 1184.5], [228.0, 1263.0], [226.0, 1162.75], [224.0, 1136.3333333333333], [238.0, 1601.0], [239.0, 1275.3333333333333], [236.0, 1419.6666666666667], [235.0, 1167.25], [237.0, 1019.0], [234.0, 625.5], [247.0, 1548.0], [246.0, 1655.0], [241.0, 1577.0], [242.0, 1020.0], [255.0, 57.0], [254.0, 761.3333333333334], [253.0, 48.0], [252.0, 52.5], [251.0, 49.0], [249.0, 1540.0], [248.0, 1551.0], [257.0, 890.86301369863], [267.0, 59.0], [266.0, 403.5], [265.0, 161.5], [263.0, 45.0], [262.0, 68.0], [260.0, 1557.0], [259.0, 1180.0], [258.0, 726.1428571428571], [269.0, 640.6666666666666], [268.0, 544.0], [256.0, 571.0], [271.0, 717.8], [264.0, 671.0], [270.0, 611.5833333333333], [287.0, 376.33333333333337], [283.0, 189.5], [278.0, 508.44444444444446], [285.0, 580.75], [284.0, 576.25], [286.0, 723.0], [275.0, 615.6666666666666], [274.0, 673.5], [273.0, 641.0], [272.0, 639.0], [280.0, 282.5], [282.0, 400.8], [281.0, 570.4], [279.0, 680.6666666666666], [277.0, 706.0], [276.0, 662.4], [291.0, 713.0], [299.0, 659.0], [298.0, 649.0], [297.0, 679.0], [295.0, 665.0], [294.0, 686.0], [292.0, 263.0], [290.0, 731.3333333333334], [289.0, 803.3333333333334], [288.0, 767.5], [313.0, 32.0], [335.0, 42.333333333333336], [334.0, 45.5], [333.0, 44.0], [331.0, 42.5], [330.0, 31.0], [325.0, 32.333333333333336], [322.0, 35.0], [321.0, 31.5], [349.0, 73.0], [347.0, 40.0], [346.0, 42.0], [343.0, 33.0], [340.0, 46.0], [336.0, 43.5], [367.0, 56.333333333333336], [364.0, 55.75], [362.0, 64.0], [361.0, 34.0], [358.0, 42.5], [356.0, 41.333333333333336], [353.0, 44.0], [371.0, 49.0], [382.0, 69.89999999999999], [380.0, 42.0], [379.0, 37.0], [377.0, 53.0], [374.0, 38.0], [372.0, 48.75], [370.0, 48.0], [369.0, 64.0], [368.0, 50.0], [398.0, 487.52287581699363], [399.0, 1013.0], [396.0, 75.0], [394.0, 73.0], [391.0, 79.25], [390.0, 64.0], [389.0, 71.0], [388.0, 76.5], [386.0, 75.0], [384.0, 71.5], [414.0, 504.0], [415.0, 477.03448275862064], [413.0, 65.0], [411.0, 951.0], [408.0, 304.25], [407.0, 678.6666666666666], [405.0, 983.0], [403.0, 530.5], [400.0, 390.33333333333337], [431.0, 781.0], [427.0, 622.0], [426.0, 790.0], [423.0, 436.75], [419.0, 72.0], [417.0, 79.0], [446.0, 129.33333333333334], [447.0, 43.0], [445.0, 494.0], [443.0, 514.0], [441.0, 112.0], [440.0, 314.0], [439.0, 330.3076923076924], [438.0, 773.0], [436.0, 603.25], [463.0, 367.0], [462.0, 340.0], [461.0, 258.0], [458.0, 400.0], [457.0, 292.6666666666667], [454.0, 213.5], [453.0, 156.33333333333331], [451.0, 226.5], [476.0, 215.5], [479.0, 133.66666666666666], [478.0, 297.0], [477.0, 46.0], [474.0, 50.0], [473.0, 99.0], [469.0, 412.0], [467.0, 359.0], [466.0, 40.0], [465.0, 326.0], [493.0, 102.5], [492.0, 108.0], [491.0, 331.0], [487.0, 114.0], [486.0, 167.0], [484.0, 135.33333333333334], [483.0, 290.0], [482.0, 56.5], [500.0, 156.2333333333333], [496.0, 178.25], [1.0, 14174.666666666666]], "isOverall": false, "label": "HTTP请求", "isController": false}, {"data": [[152.7300000000004, 268.2089999999996]], "isOverall": false, "label": "HTTP请求-Aggregated", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 500.0, "title": "Time VS Threads"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    axisLabel: "Number of active threads",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response times in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: { noColumns: 2,show: true, container: '#legendTimeVsThreads' },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s: At %x.2 active threads, Average response time was %y.2 ms"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesTimeVsThreads"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotTimesVsThreads"), dataset, options);
            // setup overview
            $.plot($("#overviewTimesVsThreads"), dataset, prepareOverviewOptions(options));
        }
};

// Time vs threads
function refreshTimeVsThreads(){
    var infos = timeVsThreadsInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyTimeVsThreads");
        return;
    }
    if(isGraph($("#flotTimesVsThreads"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTimeVsThreads");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTimesVsThreads", "#overviewTimesVsThreads");
        $('#footerTimeVsThreads .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var bytesThroughputOverTimeInfos = {
        data : {"result": {"minY": 3141.6833333333334, "minX": 1.63089642E12, "maxY": 7664.8, "series": [{"data": [[1.63089648E12, 5729.416666666667], [1.63089642E12, 7664.8]], "isOverall": false, "label": "Bytes received per second", "isController": false}, {"data": [[1.63089648E12, 3454.883333333333], [1.63089642E12, 3141.6833333333334]], "isOverall": false, "label": "Bytes sent per second", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.63089648E12, "title": "Bytes Throughput Over Time"}},
        getOptions : function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity) ,
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Bytes / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendBytesThroughputOverTime'
                },
                selection: {
                    mode: "xy"
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y"
                }
            };
        },
        createGraph : function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesBytesThroughputOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotBytesThroughputOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewBytesThroughputOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Bytes throughput Over Time
function refreshBytesThroughputOverTime(fixTimestamps) {
    var infos = bytesThroughputOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotBytesThroughputOverTime"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesBytesThroughputOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotBytesThroughputOverTime", "#overviewBytesThroughputOverTime");
        $('#footerBytesThroughputOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimesOverTimeInfos = {
        data: {"result": {"minY": 208.66066066066037, "minX": 1.63089642E12, "maxY": 327.63836163836174, "series": [{"data": [[1.63089648E12, 327.63836163836174], [1.63089642E12, 208.66066066066037]], "isOverall": false, "label": "HTTP请求", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.63089648E12, "title": "Response Time Over Time"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average response time was %y ms"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Response Times Over Time
function refreshResponseTimeOverTime(fixTimestamps) {
    var infos = responseTimesOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimeOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotResponseTimesOverTime"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimesOverTime", "#overviewResponseTimesOverTime");
        $('#footerResponseTimesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var latenciesOverTimeInfos = {
        data: {"result": {"minY": 207.99099099099095, "minX": 1.63089642E12, "maxY": 284.4795204795206, "series": [{"data": [[1.63089648E12, 284.4795204795206], [1.63089642E12, 207.99099099099095]], "isOverall": false, "label": "HTTP请求", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.63089648E12, "title": "Latencies Over Time"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response latencies in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendLatenciesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average latency was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesLatenciesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotLatenciesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewLatenciesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Latencies Over Time
function refreshLatenciesOverTime(fixTimestamps) {
    var infos = latenciesOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyLatenciesOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotLatenciesOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesLatenciesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotLatenciesOverTime", "#overviewLatenciesOverTime");
        $('#footerLatenciesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var connectTimeOverTimeInfos = {
        data: {"result": {"minY": 61.593406593406584, "minX": 1.63089642E12, "maxY": 144.10910910910894, "series": [{"data": [[1.63089648E12, 61.593406593406584], [1.63089642E12, 144.10910910910894]], "isOverall": false, "label": "HTTP请求", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.63089648E12, "title": "Connect Time Over Time"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getConnectTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average Connect Time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendConnectTimeOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average connect time was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesConnectTimeOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotConnectTimeOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewConnectTimeOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Connect Time Over Time
function refreshConnectTimeOverTime(fixTimestamps) {
    var infos = connectTimeOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyConnectTimeOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotConnectTimeOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesConnectTimeOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotConnectTimeOverTime", "#overviewConnectTimeOverTime");
        $('#footerConnectTimeOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var responseTimePercentilesOverTimeInfos = {
        data: {"result": {"minY": 18.0, "minX": 1.63089642E12, "maxY": 2155.0, "series": [{"data": [[1.63089648E12, 209.0], [1.63089642E12, 2155.0]], "isOverall": false, "label": "Max", "isController": false}, {"data": [[1.63089648E12, 37.0], [1.63089642E12, 847.0]], "isOverall": false, "label": "90th percentile", "isController": false}, {"data": [[1.63089648E12, 117.0], [1.63089642E12, 1588.0]], "isOverall": false, "label": "99th percentile", "isController": false}, {"data": [[1.63089648E12, 42.0], [1.63089642E12, 1152.0]], "isOverall": false, "label": "95th percentile", "isController": false}, {"data": [[1.63089648E12, 18.0], [1.63089642E12, 21.0]], "isOverall": false, "label": "Min", "isController": false}, {"data": [[1.63089648E12, 26.0], [1.63089642E12, 49.0]], "isOverall": false, "label": "Median", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.63089648E12, "title": "Response Time Percentiles Over Time (successful requests only)"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Response Time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimePercentilesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Response time was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimePercentilesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimePercentilesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimePercentilesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Response Time Percentiles Over Time
function refreshResponseTimePercentilesOverTime(fixTimestamps) {
    var infos = responseTimePercentilesOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotResponseTimePercentilesOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesResponseTimePercentilesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimePercentilesOverTime", "#overviewResponseTimePercentilesOverTime");
        $('#footerResponseTimePercentilesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var responseTimeVsRequestInfos = {
    data: {"result": {"minY": 24.0, "minX": 1.0, "maxY": 21241.0, "series": [{"data": [[259.0, 122.0], [132.0, 31.0], [4.0, 1085.0], [141.0, 24.0], [153.0, 41.0], [154.0, 27.0], [95.0, 28.0], [210.0, 32.0], [109.0, 27.0], [241.0, 64.0]], "isOverall": false, "label": "Successes", "isController": false}, {"data": [[1.0, 21241.0], [79.0, 86.0], [360.0, 719.5], [62.0, 30.5]], "isOverall": false, "label": "Failures", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 360.0, "title": "Response Time Vs Request"}},
    getOptions: function() {
        return {
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: true
                }
            },
            xaxis: {
                axisLabel: "Global number of requests per second",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            yaxis: {
                axisLabel: "Median Response Time in ms",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            legend: {
                noColumns: 2,
                show: true,
                container: '#legendResponseTimeVsRequest'
            },
            selection: {
                mode: 'xy'
            },
            grid: {
                hoverable: true // IMPORTANT! this is needed for tooltip to work
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s : Median response time at %x req/s was %y ms"
            },
            colors: ["#9ACD32", "#FF6347"]
        };
    },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesResponseTimeVsRequest"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotResponseTimeVsRequest"), dataset, options);
        // setup overview
        $.plot($("#overviewResponseTimeVsRequest"), dataset, prepareOverviewOptions(options));

    }
};

// Response Time vs Request
function refreshResponseTimeVsRequest() {
    var infos = responseTimeVsRequestInfos;
    prepareSeries(infos.data);
    if (isGraph($("#flotResponseTimeVsRequest"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimeVsRequest");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimeVsRequest", "#overviewResponseTimeVsRequest");
        $('#footerResponseRimeVsRequest .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var latenciesVsRequestInfos = {
    data: {"result": {"minY": 0.0, "minX": 1.0, "maxY": 1085.0, "series": [{"data": [[259.0, 121.0], [132.0, 31.0], [4.0, 1085.0], [141.0, 24.0], [153.0, 40.0], [154.0, 27.0], [95.0, 27.0], [210.0, 31.0], [109.0, 26.0], [241.0, 64.0]], "isOverall": false, "label": "Successes", "isController": false}, {"data": [[1.0, 0.0], [79.0, 85.0], [360.0, 719.0], [62.0, 29.0]], "isOverall": false, "label": "Failures", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 360.0, "title": "Latencies Vs Request"}},
    getOptions: function() {
        return{
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: true
                }
            },
            xaxis: {
                axisLabel: "Global number of requests per second",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            yaxis: {
                axisLabel: "Median Latency in ms",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            legend: { noColumns: 2,show: true, container: '#legendLatencyVsRequest' },
            selection: {
                mode: 'xy'
            },
            grid: {
                hoverable: true // IMPORTANT! this is needed for tooltip to work
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s : Median Latency time at %x req/s was %y ms"
            },
            colors: ["#9ACD32", "#FF6347"]
        };
    },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesLatencyVsRequest"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotLatenciesVsRequest"), dataset, options);
        // setup overview
        $.plot($("#overviewLatenciesVsRequest"), dataset, prepareOverviewOptions(options));
    }
};

// Latencies vs Request
function refreshLatenciesVsRequest() {
        var infos = latenciesVsRequestInfos;
        prepareSeries(infos.data);
        if(isGraph($("#flotLatenciesVsRequest"))){
            infos.createGraph();
        }else{
            var choiceContainer = $("#choicesLatencyVsRequest");
            createLegend(choiceContainer, infos);
            infos.createGraph();
            setGraphZoomable("#flotLatenciesVsRequest", "#overviewLatenciesVsRequest");
            $('#footerLatenciesVsRequest .legendColorBox > div').each(function(i){
                $(this).clone().prependTo(choiceContainer.find("li").eq(i));
            });
        }
};

var hitsPerSecondInfos = {
        data: {"result": {"minY": 16.666666666666668, "minX": 1.63089642E12, "maxY": 16.666666666666668, "series": [{"data": [[1.63089648E12, 16.666666666666668], [1.63089642E12, 16.666666666666668]], "isOverall": false, "label": "hitsPerSecond", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.63089648E12, "title": "Hits Per Second"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of hits / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendHitsPerSecond"
                },
                selection: {
                    mode : 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y.2 hits/sec"
                }
            };
        },
        createGraph: function createGraph() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesHitsPerSecond"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotHitsPerSecond"), dataset, options);
            // setup overview
            $.plot($("#overviewHitsPerSecond"), dataset, prepareOverviewOptions(options));
        }
};

// Hits per second
function refreshHitsPerSecond(fixTimestamps) {
    var infos = hitsPerSecondInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if (isGraph($("#flotHitsPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesHitsPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotHitsPerSecond", "#overviewHitsPerSecond");
        $('#footerHitsPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var codesPerSecondInfos = {
        data: {"result": {"minY": 0.03333333333333333, "minX": 1.63089642E12, "maxY": 16.65, "series": [{"data": [[1.63089648E12, 8.316666666666666], [1.63089642E12, 16.65]], "isOverall": false, "label": "200", "isController": false}, {"data": [[1.63089648E12, 0.03333333333333333]], "isOverall": false, "label": "Non HTTP response code: org.apache.http.conn.HttpHostConnectException", "isController": false}, {"data": [[1.63089648E12, 8.333333333333334]], "isOverall": false, "label": "500", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.63089648E12, "title": "Codes Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of responses / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendCodesPerSecond"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "Number of Response Codes %s at %x was %y.2 responses / sec"
                }
            };
        },
    createGraph: function() {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesCodesPerSecond"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotCodesPerSecond"), dataset, options);
        // setup overview
        $.plot($("#overviewCodesPerSecond"), dataset, prepareOverviewOptions(options));
    }
};

// Codes per second
function refreshCodesPerSecond(fixTimestamps) {
    var infos = codesPerSecondInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotCodesPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesCodesPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotCodesPerSecond", "#overviewCodesPerSecond");
        $('#footerCodesPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var transactionsPerSecondInfos = {
        data: {"result": {"minY": 8.316666666666666, "minX": 1.63089642E12, "maxY": 16.65, "series": [{"data": [[1.63089648E12, 8.366666666666667]], "isOverall": false, "label": "HTTP请求-failure", "isController": false}, {"data": [[1.63089648E12, 8.316666666666666], [1.63089642E12, 16.65]], "isOverall": false, "label": "HTTP请求-success", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.63089648E12, "title": "Transactions Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of transactions / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendTransactionsPerSecond"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y transactions / sec"
                }
            };
        },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesTransactionsPerSecond"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotTransactionsPerSecond"), dataset, options);
        // setup overview
        $.plot($("#overviewTransactionsPerSecond"), dataset, prepareOverviewOptions(options));
    }
};

// Transactions per second
function refreshTransactionsPerSecond(fixTimestamps) {
    var infos = transactionsPerSecondInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyTransactionsPerSecond");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotTransactionsPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTransactionsPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTransactionsPerSecond", "#overviewTransactionsPerSecond");
        $('#footerTransactionsPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var totalTPSInfos = {
        data: {"result": {"minY": 8.316666666666666, "minX": 1.63089642E12, "maxY": 16.65, "series": [{"data": [[1.63089648E12, 8.316666666666666], [1.63089642E12, 16.65]], "isOverall": false, "label": "Transaction-success", "isController": false}, {"data": [[1.63089648E12, 8.366666666666667]], "isOverall": false, "label": "Transaction-failure", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.63089648E12, "title": "Total Transactions Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of transactions / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendTotalTPS"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y transactions / sec"
                },
                colors: ["#9ACD32", "#FF6347"]
            };
        },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesTotalTPS"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotTotalTPS"), dataset, options);
        // setup overview
        $.plot($("#overviewTotalTPS"), dataset, prepareOverviewOptions(options));
    }
};

// Total Transactions per second
function refreshTotalTPS(fixTimestamps) {
    var infos = totalTPSInfos;
    // We want to ignore seriesFilter
    prepareSeries(infos.data, false, true);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotTotalTPS"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTotalTPS");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTotalTPS", "#overviewTotalTPS");
        $('#footerTotalTPS .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

// Collapse the graph matching the specified DOM element depending the collapsed
// status
function collapse(elem, collapsed){
    if(collapsed){
        $(elem).parent().find(".fa-chevron-up").removeClass("fa-chevron-up").addClass("fa-chevron-down");
    } else {
        $(elem).parent().find(".fa-chevron-down").removeClass("fa-chevron-down").addClass("fa-chevron-up");
        if (elem.id == "bodyBytesThroughputOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshBytesThroughputOverTime(true);
            }
            document.location.href="#bytesThroughputOverTime";
        } else if (elem.id == "bodyLatenciesOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshLatenciesOverTime(true);
            }
            document.location.href="#latenciesOverTime";
        } else if (elem.id == "bodyCustomGraph") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshCustomGraph(true);
            }
            document.location.href="#responseCustomGraph";
        } else if (elem.id == "bodyConnectTimeOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshConnectTimeOverTime(true);
            }
            document.location.href="#connectTimeOverTime";
        } else if (elem.id == "bodyResponseTimePercentilesOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimePercentilesOverTime(true);
            }
            document.location.href="#responseTimePercentilesOverTime";
        } else if (elem.id == "bodyResponseTimeDistribution") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimeDistribution();
            }
            document.location.href="#responseTimeDistribution" ;
        } else if (elem.id == "bodySyntheticResponseTimeDistribution") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshSyntheticResponseTimeDistribution();
            }
            document.location.href="#syntheticResponseTimeDistribution" ;
        } else if (elem.id == "bodyActiveThreadsOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshActiveThreadsOverTime(true);
            }
            document.location.href="#activeThreadsOverTime";
        } else if (elem.id == "bodyTimeVsThreads") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTimeVsThreads();
            }
            document.location.href="#timeVsThreads" ;
        } else if (elem.id == "bodyCodesPerSecond") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshCodesPerSecond(true);
            }
            document.location.href="#codesPerSecond";
        } else if (elem.id == "bodyTransactionsPerSecond") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTransactionsPerSecond(true);
            }
            document.location.href="#transactionsPerSecond";
        } else if (elem.id == "bodyTotalTPS") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTotalTPS(true);
            }
            document.location.href="#totalTPS";
        } else if (elem.id == "bodyResponseTimeVsRequest") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimeVsRequest();
            }
            document.location.href="#responseTimeVsRequest";
        } else if (elem.id == "bodyLatenciesVsRequest") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshLatenciesVsRequest();
            }
            document.location.href="#latencyVsRequest";
        }
    }
}

/*
 * Activates or deactivates all series of the specified graph (represented by id parameter)
 * depending on checked argument.
 */
function toggleAll(id, checked){
    var placeholder = document.getElementById(id);

    var cases = $(placeholder).find(':checkbox');
    cases.prop('checked', checked);
    $(cases).parent().children().children().toggleClass("legend-disabled", !checked);

    var choiceContainer;
    if ( id == "choicesBytesThroughputOverTime"){
        choiceContainer = $("#choicesBytesThroughputOverTime");
        refreshBytesThroughputOverTime(false);
    } else if(id == "choicesResponseTimesOverTime"){
        choiceContainer = $("#choicesResponseTimesOverTime");
        refreshResponseTimeOverTime(false);
    }else if(id == "choicesResponseCustomGraph"){
        choiceContainer = $("#choicesResponseCustomGraph");
        refreshCustomGraph(false);
    } else if ( id == "choicesLatenciesOverTime"){
        choiceContainer = $("#choicesLatenciesOverTime");
        refreshLatenciesOverTime(false);
    } else if ( id == "choicesConnectTimeOverTime"){
        choiceContainer = $("#choicesConnectTimeOverTime");
        refreshConnectTimeOverTime(false);
    } else if ( id == "choicesResponseTimePercentilesOverTime"){
        choiceContainer = $("#choicesResponseTimePercentilesOverTime");
        refreshResponseTimePercentilesOverTime(false);
    } else if ( id == "choicesResponseTimePercentiles"){
        choiceContainer = $("#choicesResponseTimePercentiles");
        refreshResponseTimePercentiles();
    } else if(id == "choicesActiveThreadsOverTime"){
        choiceContainer = $("#choicesActiveThreadsOverTime");
        refreshActiveThreadsOverTime(false);
    } else if ( id == "choicesTimeVsThreads"){
        choiceContainer = $("#choicesTimeVsThreads");
        refreshTimeVsThreads();
    } else if ( id == "choicesSyntheticResponseTimeDistribution"){
        choiceContainer = $("#choicesSyntheticResponseTimeDistribution");
        refreshSyntheticResponseTimeDistribution();
    } else if ( id == "choicesResponseTimeDistribution"){
        choiceContainer = $("#choicesResponseTimeDistribution");
        refreshResponseTimeDistribution();
    } else if ( id == "choicesHitsPerSecond"){
        choiceContainer = $("#choicesHitsPerSecond");
        refreshHitsPerSecond(false);
    } else if(id == "choicesCodesPerSecond"){
        choiceContainer = $("#choicesCodesPerSecond");
        refreshCodesPerSecond(false);
    } else if ( id == "choicesTransactionsPerSecond"){
        choiceContainer = $("#choicesTransactionsPerSecond");
        refreshTransactionsPerSecond(false);
    } else if ( id == "choicesTotalTPS"){
        choiceContainer = $("#choicesTotalTPS");
        refreshTotalTPS(false);
    } else if ( id == "choicesResponseTimeVsRequest"){
        choiceContainer = $("#choicesResponseTimeVsRequest");
        refreshResponseTimeVsRequest();
    } else if ( id == "choicesLatencyVsRequest"){
        choiceContainer = $("#choicesLatencyVsRequest");
        refreshLatenciesVsRequest();
    }
    var color = checked ? "black" : "#818181";
    if(choiceContainer != null) {
        choiceContainer.find("label").each(function(){
            this.style.color = color;
        });
    }
}

