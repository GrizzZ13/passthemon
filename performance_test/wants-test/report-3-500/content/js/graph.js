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
        data: {"result": {"minY": 48.0, "minX": 0.0, "maxY": 21030.0, "series": [{"data": [[0.0, 48.0], [0.1, 50.0], [0.2, 52.0], [0.3, 55.0], [0.4, 60.0], [0.5, 60.0], [0.6, 60.0], [0.7, 61.0], [0.8, 62.0], [0.9, 64.0], [1.0, 65.0], [1.1, 65.0], [1.2, 67.0], [1.3, 68.0], [1.4, 70.0], [1.5, 71.0], [1.6, 71.0], [1.7, 72.0], [1.8, 73.0], [1.9, 73.0], [2.0, 75.0], [2.1, 75.0], [2.2, 76.0], [2.3, 76.0], [2.4, 78.0], [2.5, 79.0], [2.6, 79.0], [2.7, 80.0], [2.8, 80.0], [2.9, 80.0], [3.0, 81.0], [3.1, 82.0], [3.2, 82.0], [3.3, 83.0], [3.4, 83.0], [3.5, 84.0], [3.6, 84.0], [3.7, 86.0], [3.8, 87.0], [3.9, 88.0], [4.0, 88.0], [4.1, 88.0], [4.2, 88.0], [4.3, 89.0], [4.4, 90.0], [4.5, 90.0], [4.6, 93.0], [4.7, 93.0], [4.8, 94.0], [4.9, 95.0], [5.0, 95.0], [5.1, 96.0], [5.2, 96.0], [5.3, 97.0], [5.4, 97.0], [5.5, 99.0], [5.6, 99.0], [5.7, 100.0], [5.8, 100.0], [5.9, 101.0], [6.0, 101.0], [6.1, 102.0], [6.2, 102.0], [6.3, 103.0], [6.4, 103.0], [6.5, 104.0], [6.6, 104.0], [6.7, 104.0], [6.8, 106.0], [6.9, 107.0], [7.0, 108.0], [7.1, 109.0], [7.2, 109.0], [7.3, 109.0], [7.4, 110.0], [7.5, 111.0], [7.6, 112.0], [7.7, 113.0], [7.8, 113.0], [7.9, 113.0], [8.0, 114.0], [8.1, 114.0], [8.2, 116.0], [8.3, 116.0], [8.4, 117.0], [8.5, 118.0], [8.6, 119.0], [8.7, 119.0], [8.8, 119.0], [8.9, 119.0], [9.0, 120.0], [9.1, 121.0], [9.2, 122.0], [9.3, 123.0], [9.4, 123.0], [9.5, 124.0], [9.6, 125.0], [9.7, 126.0], [9.8, 128.0], [9.9, 129.0], [10.0, 130.0], [10.1, 131.0], [10.2, 131.0], [10.3, 132.0], [10.4, 133.0], [10.5, 133.0], [10.6, 133.0], [10.7, 133.0], [10.8, 133.0], [10.9, 135.0], [11.0, 137.0], [11.1, 137.0], [11.2, 137.0], [11.3, 138.0], [11.4, 138.0], [11.5, 139.0], [11.6, 139.0], [11.7, 139.0], [11.8, 140.0], [11.9, 140.0], [12.0, 141.0], [12.1, 141.0], [12.2, 141.0], [12.3, 142.0], [12.4, 144.0], [12.5, 144.0], [12.6, 146.0], [12.7, 146.0], [12.8, 148.0], [12.9, 148.0], [13.0, 149.0], [13.1, 150.0], [13.2, 150.0], [13.3, 151.0], [13.4, 152.0], [13.5, 154.0], [13.6, 154.0], [13.7, 155.0], [13.8, 156.0], [13.9, 156.0], [14.0, 157.0], [14.1, 158.0], [14.2, 158.0], [14.3, 159.0], [14.4, 159.0], [14.5, 159.0], [14.6, 160.0], [14.7, 161.0], [14.8, 163.0], [14.9, 164.0], [15.0, 165.0], [15.1, 165.0], [15.2, 166.0], [15.3, 167.0], [15.4, 167.0], [15.5, 167.0], [15.6, 168.0], [15.7, 168.0], [15.8, 170.0], [15.9, 171.0], [16.0, 172.0], [16.1, 174.0], [16.2, 175.0], [16.3, 175.0], [16.4, 176.0], [16.5, 177.0], [16.6, 177.0], [16.7, 178.0], [16.8, 178.0], [16.9, 178.0], [17.0, 180.0], [17.1, 182.0], [17.2, 182.0], [17.3, 182.0], [17.4, 183.0], [17.5, 183.0], [17.6, 185.0], [17.7, 186.0], [17.8, 188.0], [17.9, 189.0], [18.0, 189.0], [18.1, 190.0], [18.2, 190.0], [18.3, 191.0], [18.4, 191.0], [18.5, 191.0], [18.6, 192.0], [18.7, 192.0], [18.8, 194.0], [18.9, 194.0], [19.0, 194.0], [19.1, 195.0], [19.2, 196.0], [19.3, 197.0], [19.4, 202.0], [19.5, 204.0], [19.6, 204.0], [19.7, 204.0], [19.8, 205.0], [19.9, 206.0], [20.0, 207.0], [20.1, 208.0], [20.2, 209.0], [20.3, 209.0], [20.4, 211.0], [20.5, 211.0], [20.6, 212.0], [20.7, 212.0], [20.8, 214.0], [20.9, 214.0], [21.0, 215.0], [21.1, 215.0], [21.2, 219.0], [21.3, 222.0], [21.4, 223.0], [21.5, 225.0], [21.6, 226.0], [21.7, 226.0], [21.8, 229.0], [21.9, 229.0], [22.0, 230.0], [22.1, 230.0], [22.2, 231.0], [22.3, 231.0], [22.4, 232.0], [22.5, 232.0], [22.6, 233.0], [22.7, 233.0], [22.8, 233.0], [22.9, 234.0], [23.0, 235.0], [23.1, 235.0], [23.2, 236.0], [23.3, 236.0], [23.4, 236.0], [23.5, 236.0], [23.6, 237.0], [23.7, 237.0], [23.8, 239.0], [23.9, 239.0], [24.0, 239.0], [24.1, 239.0], [24.2, 240.0], [24.3, 241.0], [24.4, 241.0], [24.5, 241.0], [24.6, 242.0], [24.7, 242.0], [24.8, 242.0], [24.9, 242.0], [25.0, 243.0], [25.1, 244.0], [25.2, 244.0], [25.3, 244.0], [25.4, 245.0], [25.5, 245.0], [25.6, 247.0], [25.7, 247.0], [25.8, 247.0], [25.9, 248.0], [26.0, 248.0], [26.1, 248.0], [26.2, 248.0], [26.3, 248.0], [26.4, 250.0], [26.5, 250.0], [26.6, 250.0], [26.7, 251.0], [26.8, 253.0], [26.9, 253.0], [27.0, 254.0], [27.1, 255.0], [27.2, 256.0], [27.3, 257.0], [27.4, 257.0], [27.5, 257.0], [27.6, 258.0], [27.7, 258.0], [27.8, 260.0], [27.9, 261.0], [28.0, 263.0], [28.1, 263.0], [28.2, 264.0], [28.3, 264.0], [28.4, 265.0], [28.5, 265.0], [28.6, 266.0], [28.7, 266.0], [28.8, 268.0], [28.9, 268.0], [29.0, 271.0], [29.1, 271.0], [29.2, 272.0], [29.3, 272.0], [29.4, 273.0], [29.5, 275.0], [29.6, 276.0], [29.7, 276.0], [29.8, 278.0], [29.9, 278.0], [30.0, 279.0], [30.1, 279.0], [30.2, 280.0], [30.3, 282.0], [30.4, 282.0], [30.5, 282.0], [30.6, 284.0], [30.7, 284.0], [30.8, 285.0], [30.9, 285.0], [31.0, 287.0], [31.1, 290.0], [31.2, 290.0], [31.3, 291.0], [31.4, 292.0], [31.5, 295.0], [31.6, 297.0], [31.7, 299.0], [31.8, 304.0], [31.9, 304.0], [32.0, 305.0], [32.1, 308.0], [32.2, 309.0], [32.3, 310.0], [32.4, 312.0], [32.5, 313.0], [32.6, 313.0], [32.7, 315.0], [32.8, 316.0], [32.9, 317.0], [33.0, 320.0], [33.1, 320.0], [33.2, 321.0], [33.3, 321.0], [33.4, 325.0], [33.5, 325.0], [33.6, 326.0], [33.7, 326.0], [33.8, 327.0], [33.9, 327.0], [34.0, 328.0], [34.1, 330.0], [34.2, 333.0], [34.3, 333.0], [34.4, 335.0], [34.5, 336.0], [34.6, 337.0], [34.7, 338.0], [34.8, 340.0], [34.9, 340.0], [35.0, 341.0], [35.1, 341.0], [35.2, 341.0], [35.3, 343.0], [35.4, 344.0], [35.5, 344.0], [35.6, 346.0], [35.7, 346.0], [35.8, 348.0], [35.9, 348.0], [36.0, 349.0], [36.1, 350.0], [36.2, 351.0], [36.3, 351.0], [36.4, 351.0], [36.5, 352.0], [36.6, 354.0], [36.7, 354.0], [36.8, 354.0], [36.9, 358.0], [37.0, 359.0], [37.1, 360.0], [37.2, 361.0], [37.3, 364.0], [37.4, 368.0], [37.5, 372.0], [37.6, 373.0], [37.7, 376.0], [37.8, 377.0], [37.9, 379.0], [38.0, 381.0], [38.1, 381.0], [38.2, 381.0], [38.3, 383.0], [38.4, 384.0], [38.5, 386.0], [38.6, 387.0], [38.7, 388.0], [38.8, 389.0], [38.9, 391.0], [39.0, 391.0], [39.1, 391.0], [39.2, 393.0], [39.3, 394.0], [39.4, 394.0], [39.5, 395.0], [39.6, 395.0], [39.7, 397.0], [39.8, 397.0], [39.9, 398.0], [40.0, 399.0], [40.1, 403.0], [40.2, 404.0], [40.3, 407.0], [40.4, 407.0], [40.5, 409.0], [40.6, 409.0], [40.7, 413.0], [40.8, 414.0], [40.9, 417.0], [41.0, 418.0], [41.1, 422.0], [41.2, 423.0], [41.3, 424.0], [41.4, 425.0], [41.5, 426.0], [41.6, 429.0], [41.7, 430.0], [41.8, 431.0], [41.9, 432.0], [42.0, 433.0], [42.1, 436.0], [42.2, 437.0], [42.3, 438.0], [42.4, 439.0], [42.5, 442.0], [42.6, 443.0], [42.7, 445.0], [42.8, 445.0], [42.9, 447.0], [43.0, 448.0], [43.1, 453.0], [43.2, 455.0], [43.3, 456.0], [43.4, 456.0], [43.5, 457.0], [43.6, 459.0], [43.7, 460.0], [43.8, 460.0], [43.9, 461.0], [44.0, 461.0], [44.1, 462.0], [44.2, 462.0], [44.3, 465.0], [44.4, 465.0], [44.5, 468.0], [44.6, 469.0], [44.7, 471.0], [44.8, 472.0], [44.9, 474.0], [45.0, 477.0], [45.1, 481.0], [45.2, 483.0], [45.3, 485.0], [45.4, 488.0], [45.5, 491.0], [45.6, 493.0], [45.7, 496.0], [45.8, 499.0], [45.9, 502.0], [46.0, 502.0], [46.1, 503.0], [46.2, 503.0], [46.3, 508.0], [46.4, 509.0], [46.5, 515.0], [46.6, 516.0], [46.7, 518.0], [46.8, 521.0], [46.9, 526.0], [47.0, 526.0], [47.1, 529.0], [47.2, 532.0], [47.3, 537.0], [47.4, 538.0], [47.5, 541.0], [47.6, 542.0], [47.7, 542.0], [47.8, 545.0], [47.9, 548.0], [48.0, 548.0], [48.1, 550.0], [48.2, 551.0], [48.3, 552.0], [48.4, 554.0], [48.5, 555.0], [48.6, 555.0], [48.7, 559.0], [48.8, 559.0], [48.9, 560.0], [49.0, 561.0], [49.1, 562.0], [49.2, 563.0], [49.3, 568.0], [49.4, 568.0], [49.5, 569.0], [49.6, 569.0], [49.7, 570.0], [49.8, 571.0], [49.9, 572.0], [50.0, 573.0], [50.1, 575.0], [50.2, 575.0], [50.3, 575.0], [50.4, 577.0], [50.5, 577.0], [50.6, 578.0], [50.7, 578.0], [50.8, 578.0], [50.9, 580.0], [51.0, 582.0], [51.1, 582.0], [51.2, 584.0], [51.3, 584.0], [51.4, 584.0], [51.5, 585.0], [51.6, 585.0], [51.7, 586.0], [51.8, 586.0], [51.9, 586.0], [52.0, 586.0], [52.1, 588.0], [52.2, 588.0], [52.3, 589.0], [52.4, 589.0], [52.5, 590.0], [52.6, 590.0], [52.7, 593.0], [52.8, 593.0], [52.9, 595.0], [53.0, 595.0], [53.1, 596.0], [53.2, 598.0], [53.3, 600.0], [53.4, 601.0], [53.5, 601.0], [53.6, 601.0], [53.7, 605.0], [53.8, 605.0], [53.9, 607.0], [54.0, 608.0], [54.1, 609.0], [54.2, 610.0], [54.3, 611.0], [54.4, 611.0], [54.5, 612.0], [54.6, 613.0], [54.7, 616.0], [54.8, 616.0], [54.9, 616.0], [55.0, 617.0], [55.1, 621.0], [55.2, 621.0], [55.3, 621.0], [55.4, 622.0], [55.5, 624.0], [55.6, 628.0], [55.7, 629.0], [55.8, 629.0], [55.9, 629.0], [56.0, 629.0], [56.1, 630.0], [56.2, 630.0], [56.3, 632.0], [56.4, 632.0], [56.5, 632.0], [56.6, 633.0], [56.7, 633.0], [56.8, 634.0], [56.9, 634.0], [57.0, 635.0], [57.1, 635.0], [57.2, 636.0], [57.3, 637.0], [57.4, 637.0], [57.5, 638.0], [57.6, 638.0], [57.7, 638.0], [57.8, 639.0], [57.9, 639.0], [58.0, 639.0], [58.1, 640.0], [58.2, 640.0], [58.3, 641.0], [58.4, 641.0], [58.5, 642.0], [58.6, 642.0], [58.7, 642.0], [58.8, 643.0], [58.9, 644.0], [59.0, 644.0], [59.1, 645.0], [59.2, 645.0], [59.3, 647.0], [59.4, 647.0], [59.5, 648.0], [59.6, 649.0], [59.7, 649.0], [59.8, 649.0], [59.9, 649.0], [60.0, 649.0], [60.1, 650.0], [60.2, 650.0], [60.3, 651.0], [60.4, 651.0], [60.5, 653.0], [60.6, 653.0], [60.7, 655.0], [60.8, 655.0], [60.9, 655.0], [61.0, 655.0], [61.1, 656.0], [61.2, 657.0], [61.3, 657.0], [61.4, 658.0], [61.5, 659.0], [61.6, 660.0], [61.7, 660.0], [61.8, 662.0], [61.9, 663.0], [62.0, 663.0], [62.1, 664.0], [62.2, 664.0], [62.3, 665.0], [62.4, 666.0], [62.5, 666.0], [62.6, 667.0], [62.7, 667.0], [62.8, 668.0], [62.9, 668.0], [63.0, 669.0], [63.1, 671.0], [63.2, 671.0], [63.3, 671.0], [63.4, 672.0], [63.5, 673.0], [63.6, 673.0], [63.7, 674.0], [63.8, 675.0], [63.9, 675.0], [64.0, 676.0], [64.1, 677.0], [64.2, 677.0], [64.3, 677.0], [64.4, 678.0], [64.5, 678.0], [64.6, 679.0], [64.7, 679.0], [64.8, 680.0], [64.9, 681.0], [65.0, 681.0], [65.1, 682.0], [65.2, 682.0], [65.3, 685.0], [65.4, 687.0], [65.5, 688.0], [65.6, 689.0], [65.7, 689.0], [65.8, 689.0], [65.9, 690.0], [66.0, 691.0], [66.1, 692.0], [66.2, 692.0], [66.3, 695.0], [66.4, 698.0], [66.5, 700.0], [66.6, 701.0], [66.7, 701.0], [66.8, 701.0], [66.9, 704.0], [67.0, 704.0], [67.1, 710.0], [67.2, 714.0], [67.3, 715.0], [67.4, 716.0], [67.5, 716.0], [67.6, 717.0], [67.7, 719.0], [67.8, 719.0], [67.9, 722.0], [68.0, 722.0], [68.1, 723.0], [68.2, 723.0], [68.3, 725.0], [68.4, 725.0], [68.5, 726.0], [68.6, 727.0], [68.7, 728.0], [68.8, 728.0], [68.9, 729.0], [69.0, 730.0], [69.1, 730.0], [69.2, 730.0], [69.3, 731.0], [69.4, 731.0], [69.5, 732.0], [69.6, 732.0], [69.7, 733.0], [69.8, 734.0], [69.9, 737.0], [70.0, 737.0], [70.1, 738.0], [70.2, 739.0], [70.3, 740.0], [70.4, 740.0], [70.5, 740.0], [70.6, 740.0], [70.7, 741.0], [70.8, 741.0], [70.9, 742.0], [71.0, 742.0], [71.1, 743.0], [71.2, 743.0], [71.3, 744.0], [71.4, 744.0], [71.5, 745.0], [71.6, 745.0], [71.7, 745.0], [71.8, 745.0], [71.9, 746.0], [72.0, 746.0], [72.1, 750.0], [72.2, 751.0], [72.3, 751.0], [72.4, 751.0], [72.5, 752.0], [72.6, 753.0], [72.7, 754.0], [72.8, 755.0], [72.9, 758.0], [73.0, 758.0], [73.1, 759.0], [73.2, 759.0], [73.3, 761.0], [73.4, 762.0], [73.5, 762.0], [73.6, 762.0], [73.7, 765.0], [73.8, 765.0], [73.9, 766.0], [74.0, 767.0], [74.1, 768.0], [74.2, 770.0], [74.3, 772.0], [74.4, 774.0], [74.5, 775.0], [74.6, 776.0], [74.7, 778.0], [74.8, 779.0], [74.9, 780.0], [75.0, 780.0], [75.1, 783.0], [75.2, 785.0], [75.3, 788.0], [75.4, 789.0], [75.5, 790.0], [75.6, 791.0], [75.7, 792.0], [75.8, 794.0], [75.9, 795.0], [76.0, 796.0], [76.1, 797.0], [76.2, 798.0], [76.3, 800.0], [76.4, 800.0], [76.5, 801.0], [76.6, 802.0], [76.7, 803.0], [76.8, 804.0], [76.9, 804.0], [77.0, 805.0], [77.1, 805.0], [77.2, 806.0], [77.3, 806.0], [77.4, 808.0], [77.5, 808.0], [77.6, 809.0], [77.7, 810.0], [77.8, 810.0], [77.9, 812.0], [78.0, 812.0], [78.1, 814.0], [78.2, 815.0], [78.3, 818.0], [78.4, 819.0], [78.5, 821.0], [78.6, 822.0], [78.7, 822.0], [78.8, 823.0], [78.9, 824.0], [79.0, 824.0], [79.1, 826.0], [79.2, 827.0], [79.3, 828.0], [79.4, 829.0], [79.5, 829.0], [79.6, 830.0], [79.7, 830.0], [79.8, 831.0], [79.9, 832.0], [80.0, 832.0], [80.1, 833.0], [80.2, 833.0], [80.3, 833.0], [80.4, 834.0], [80.5, 834.0], [80.6, 834.0], [80.7, 835.0], [80.8, 835.0], [80.9, 835.0], [81.0, 836.0], [81.1, 837.0], [81.2, 837.0], [81.3, 837.0], [81.4, 837.0], [81.5, 837.0], [81.6, 838.0], [81.7, 838.0], [81.8, 838.0], [81.9, 839.0], [82.0, 839.0], [82.1, 839.0], [82.2, 839.0], [82.3, 839.0], [82.4, 840.0], [82.5, 841.0], [82.6, 841.0], [82.7, 841.0], [82.8, 841.0], [82.9, 842.0], [83.0, 842.0], [83.1, 842.0], [83.2, 843.0], [83.3, 844.0], [83.4, 845.0], [83.5, 845.0], [83.6, 845.0], [83.7, 846.0], [83.8, 846.0], [83.9, 846.0], [84.0, 846.0], [84.1, 847.0], [84.2, 848.0], [84.3, 848.0], [84.4, 848.0], [84.5, 852.0], [84.6, 852.0], [84.7, 854.0], [84.8, 854.0], [84.9, 863.0], [85.0, 864.0], [85.1, 866.0], [85.2, 867.0], [85.3, 868.0], [85.4, 868.0], [85.5, 870.0], [85.6, 870.0], [85.7, 872.0], [85.8, 872.0], [85.9, 873.0], [86.0, 877.0], [86.1, 878.0], [86.2, 878.0], [86.3, 882.0], [86.4, 883.0], [86.5, 886.0], [86.6, 887.0], [86.7, 888.0], [86.8, 890.0], [86.9, 890.0], [87.0, 891.0], [87.1, 891.0], [87.2, 893.0], [87.3, 893.0], [87.4, 894.0], [87.5, 895.0], [87.6, 897.0], [87.7, 898.0], [87.8, 900.0], [87.9, 900.0], [88.0, 901.0], [88.1, 901.0], [88.2, 902.0], [88.3, 903.0], [88.4, 903.0], [88.5, 903.0], [88.6, 903.0], [88.7, 907.0], [88.8, 908.0], [88.9, 909.0], [89.0, 909.0], [89.1, 910.0], [89.2, 911.0], [89.3, 913.0], [89.4, 914.0], [89.5, 915.0], [89.6, 916.0], [89.7, 917.0], [89.8, 917.0], [89.9, 918.0], [90.0, 919.0], [90.1, 920.0], [90.2, 920.0], [90.3, 922.0], [90.4, 922.0], [90.5, 922.0], [90.6, 923.0], [90.7, 923.0], [90.8, 924.0], [90.9, 925.0], [91.0, 926.0], [91.1, 928.0], [91.2, 928.0], [91.3, 929.0], [91.4, 931.0], [91.5, 932.0], [91.6, 932.0], [91.7, 933.0], [91.8, 933.0], [91.9, 934.0], [92.0, 935.0], [92.1, 937.0], [92.2, 939.0], [92.3, 943.0], [92.4, 944.0], [92.5, 949.0], [92.6, 949.0], [92.7, 951.0], [92.8, 951.0], [92.9, 953.0], [93.0, 954.0], [93.1, 956.0], [93.2, 958.0], [93.3, 961.0], [93.4, 961.0], [93.5, 962.0], [93.6, 962.0], [93.7, 965.0], [93.8, 965.0], [93.9, 966.0], [94.0, 967.0], [94.1, 970.0], [94.2, 970.0], [94.3, 971.0], [94.4, 971.0], [94.5, 973.0], [94.6, 973.0], [94.7, 974.0], [94.8, 974.0], [94.9, 976.0], [95.0, 976.0], [95.1, 979.0], [95.2, 979.0], [95.3, 980.0], [95.4, 981.0], [95.5, 981.0], [95.6, 981.0], [95.7, 983.0], [95.8, 984.0], [95.9, 985.0], [96.0, 986.0], [96.1, 989.0], [96.2, 990.0], [96.3, 991.0], [96.4, 991.0], [96.5, 995.0], [96.6, 998.0], [96.7, 1000.0], [96.8, 1002.0], [96.9, 1006.0], [97.0, 1008.0], [97.1, 1009.0], [97.2, 1010.0], [97.3, 1014.0], [97.4, 1016.0], [97.5, 1019.0], [97.6, 1026.0], [97.7, 1031.0], [97.8, 1035.0], [97.9, 1048.0], [98.0, 1052.0], [98.1, 1060.0], [98.2, 1072.0], [98.3, 1077.0], [98.4, 1080.0], [98.5, 1081.0], [98.6, 1088.0], [98.7, 1095.0], [98.8, 1097.0], [98.9, 1103.0], [99.0, 1103.0], [99.1, 1118.0], [99.2, 1124.0], [99.3, 1161.0], [99.4, 1167.0], [99.5, 1183.0], [99.6, 1195.0], [99.7, 1218.0], [99.8, 1270.0], [99.9, 2455.0], [100.0, 21030.0]], "isOverall": false, "label": "HTTP请求", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 100.0, "title": "Response Time Percentiles"}},
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
        data: {"result": {"minY": 1.0, "minX": 0.0, "maxY": 206.0, "series": [{"data": [[0.0, 85.0], [600.0, 198.0], [2400.0, 1.0], [700.0, 147.0], [200.0, 186.0], [800.0, 172.0], [900.0, 134.0], [1000.0, 33.0], [1100.0, 11.0], [300.0, 123.0], [1200.0, 4.0], [21000.0, 1.0], [100.0, 206.0], [400.0, 88.0], [500.0, 111.0]], "isOverall": false, "label": "HTTP请求", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 100, "maxX": 21000.0, "title": "Response Time Distribution"}},
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
        data: {"result": {"minY": 1.0, "minX": 0.0, "ticks": [[0, "Requests having \nresponse time <= 500ms"], [1, "Requests having \nresponse time > 500ms and <= 1,500ms"], [2, "Requests having \nresponse time > 1,500ms"], [3, "Requests in error"]], "maxY": 809.0, "series": [{"data": [[0.0, 686.0]], "color": "#9ACD32", "isOverall": false, "label": "Requests having \nresponse time <= 500ms", "isController": false}, {"data": [[1.0, 809.0]], "color": "yellow", "isOverall": false, "label": "Requests having \nresponse time > 500ms and <= 1,500ms", "isController": false}, {"data": [[2.0, 1.0]], "color": "orange", "isOverall": false, "label": "Requests having \nresponse time > 1,500ms", "isController": false}, {"data": [[3.0, 4.0]], "color": "#FF6347", "isOverall": false, "label": "Requests in error", "isController": false}], "supportsControllersDiscrimination": false, "maxX": 3.0, "title": "Synthetic Response Times Distribution"}},
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
        data: {"result": {"minY": 1.0, "minX": 1.63089636E12, "maxY": 236.6380000000002, "series": [{"data": [[1.63089636E12, 115.86573146292594], [1.63089642E12, 1.0]], "isOverall": false, "label": "deleteWants", "isController": false}, {"data": [[1.63089642E12, 109.23199999999997]], "isOverall": false, "label": "checkFavorite", "isController": false}, {"data": [[1.63089636E12, 236.6380000000002]], "isOverall": false, "label": "addWants", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.63089642E12, "title": "Active Threads Over Time"}},
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
        data: {"result": {"minY": 54.0, "minX": 1.0, "maxY": 11742.5, "series": [{"data": [[2.0, 932.0], [3.0, 678.0], [4.0, 900.3333333333334], [5.0, 692.0], [6.0, 701.0], [7.0, 893.0], [8.0, 766.0], [10.0, 855.6], [11.0, 852.3333333333334], [12.0, 658.5], [14.0, 923.5], [15.0, 605.0], [16.0, 805.0], [17.0, 906.5], [18.0, 940.3333333333334], [19.0, 666.0], [20.0, 939.3333333333334], [21.0, 774.0], [22.0, 621.0], [23.0, 927.0], [24.0, 953.5], [25.0, 794.1666666666666], [26.0, 652.0], [27.0, 587.5], [28.0, 54.0], [29.0, 228.0], [30.0, 712.6666666666666], [31.0, 753.2], [33.0, 897.0], [34.0, 736.0], [35.0, 806.0], [37.0, 253.8], [36.0, 560.1428571428572], [39.0, 633.3333333333333], [38.0, 347.0], [41.0, 538.1666666666667], [40.0, 538.875], [42.0, 359.66666666666663], [43.0, 657.3333333333333], [44.0, 801.3333333333334], [45.0, 528.75], [47.0, 723.3333333333334], [46.0, 301.0], [49.0, 510.8], [48.0, 296.6666666666667], [51.0, 498.0], [50.0, 210.8], [53.0, 267.125], [52.0, 302.1666666666667], [55.0, 298.7142857142857], [54.0, 504.72727272727275], [57.0, 333.1428571428571], [56.0, 360.62499999999994], [58.0, 280.125], [59.0, 148.6], [60.0, 530.6999999999999], [61.0, 224.75], [62.0, 237.61538461538458], [63.0, 229.2], [67.0, 659.6], [66.0, 309.1], [65.0, 307.75], [64.0, 544.3333333333334], [71.0, 259.3333333333333], [70.0, 528.4166666666667], [68.0, 355.6666666666667], [69.0, 293.14285714285717], [74.0, 351.4285714285714], [73.0, 513.1428571428572], [72.0, 432.0], [75.0, 638.0], [76.0, 656.2], [79.0, 426.22222222222223], [77.0, 216.0], [78.0, 357.0], [83.0, 453.0], [82.0, 488.4444444444444], [80.0, 342.0], [81.0, 210.42857142857144], [87.0, 338.8], [86.0, 613.5714285714286], [84.0, 597.75], [85.0, 255.5], [91.0, 705.0], [89.0, 392.2], [88.0, 368.33333333333337], [90.0, 365.3333333333333], [95.0, 366.3333333333333], [93.0, 526.5], [92.0, 674.75], [94.0, 377.85714285714283], [99.0, 500.1], [96.0, 209.6], [98.0, 318.5], [97.0, 400.27272727272725], [103.0, 632.7500000000001], [101.0, 370.5], [102.0, 413.0], [100.0, 380.5], [106.0, 380.6666666666667], [105.0, 370.1818181818182], [104.0, 307.1666666666667], [107.0, 288.2], [111.0, 472.875], [109.0, 770.0], [110.0, 356.2], [108.0, 332.5], [115.0, 456.14285714285717], [113.0, 500.125], [112.0, 190.75], [114.0, 479.4], [119.0, 390.9], [118.0, 420.0], [116.0, 519.8333333333334], [117.0, 320.3333333333333], [123.0, 338.09090909090907], [122.0, 428.33333333333337], [121.0, 835.6666666666666], [120.0, 295.0], [127.0, 371.2857142857143], [126.0, 821.3333333333334], [124.0, 407.87500000000006], [125.0, 552.3333333333333], [135.0, 537.0], [134.0, 422.0909090909091], [132.0, 446.6923076923077], [131.0, 702.3333333333334], [129.0, 622.8888888888889], [128.0, 415.1666666666667], [130.0, 251.0], [143.0, 657.5], [142.0, 482.16666666666663], [141.0, 576.75], [139.0, 456.22222222222223], [138.0, 451.7142857142857], [137.0, 466.3999999999999], [140.0, 367.0], [136.0, 292.75], [151.0, 708.4], [150.0, 489.8], [148.0, 454.0], [147.0, 522.0], [146.0, 431.6666666666667], [145.0, 555.3333333333333], [144.0, 421.5], [149.0, 485.0], [159.0, 550.6666666666666], [154.0, 578.6666666666666], [153.0, 698.6666666666666], [157.0, 167.0], [156.0, 355.25], [152.0, 442.5], [155.0, 592.5], [158.0, 663.3333333333334], [166.0, 668.5], [164.0, 560.7857142857143], [160.0, 408.44444444444446], [165.0, 291.5], [162.0, 489.57142857142856], [161.0, 484.0], [163.0, 341.0], [174.0, 572.5], [173.0, 628.7777777777778], [172.0, 440.42857142857144], [171.0, 459.4], [170.0, 518.0], [169.0, 518.8888888888889], [168.0, 409.83333333333337], [175.0, 344.25], [182.0, 744.0], [181.0, 637.25], [179.0, 489.2], [178.0, 563.0], [177.0, 503.0], [176.0, 487.3333333333333], [183.0, 489.1111111111111], [180.0, 424.0], [190.0, 692.5714285714286], [187.0, 646.1666666666666], [185.0, 425.2857142857143], [184.0, 712.0], [191.0, 403.75], [188.0, 376.4], [189.0, 680.0], [186.0, 520.75], [196.0, 724.5714285714286], [193.0, 500.3333333333333], [199.0, 341.0], [197.0, 379.6666666666667], [198.0, 555.2222222222222], [195.0, 654.0], [192.0, 586.0], [207.0, 635.4], [206.0, 452.16666666666663], [205.0, 309.3333333333333], [201.0, 471.5], [203.0, 640.0], [202.0, 638.0], [200.0, 635.0], [215.0, 748.1428571428572], [212.0, 789.1], [209.0, 664.0], [208.0, 684.5], [214.0, 338.0], [210.0, 534.2857142857142], [223.0, 808.0], [218.0, 591.0], [217.0, 661.5], [216.0, 775.75], [222.0, 355.0], [220.0, 341.0], [219.0, 431.7142857142857], [221.0, 449.0], [231.0, 789.1111111111111], [226.0, 804.5], [224.0, 623.0], [229.0, 471.2], [225.0, 395.0], [230.0, 466.0], [228.0, 436.7142857142857], [238.0, 692.0], [237.0, 761.0], [235.0, 743.25], [236.0, 469.3333333333333], [234.0, 465.0], [233.0, 459.5], [232.0, 457.5], [242.0, 135.5], [245.0, 1010.0], [244.0, 856.8333333333334], [254.0, 974.0], [253.0, 790.0], [269.0, 871.5], [266.0, 839.1666666666667], [265.0, 745.0], [264.0, 725.0], [258.0, 795.5], [256.0, 777.25], [279.0, 903.6], [276.0, 814.0], [273.0, 956.2], [297.0, 337.38888888888886], [298.0, 234.0], [295.0, 373.5], [302.0, 860.3333333333334], [300.0, 759.0], [290.0, 980.0], [289.0, 717.0], [288.0, 981.0], [318.0, 805.0], [317.0, 779.0], [315.0, 869.0], [313.0, 904.0], [312.0, 970.0], [311.0, 892.0], [309.0, 897.0], [308.0, 723.0], [306.0, 971.0], [305.0, 820.0], [333.0, 948.5], [332.0, 822.0], [328.0, 944.0], [327.0, 909.2857142857143], [323.0, 966.6666666666666], [320.0, 966.0], [351.0, 890.2], [350.0, 901.0], [347.0, 911.8333333333334], [348.0, 926.0], [387.0, 391.65384615384613], [399.0, 790.5], [398.0, 846.0], [397.0, 807.3333333333334], [396.0, 847.0], [394.0, 843.5], [415.0, 821.8333333333333], [410.0, 832.3333333333334], [409.0, 828.0], [408.0, 825.8333333333335], [406.0, 834.0], [405.0, 788.0], [403.0, 841.5], [401.0, 741.0], [400.0, 846.3333333333334], [429.0, 649.0], [428.0, 677.0], [427.0, 663.6666666666666], [425.0, 645.0], [422.0, 673.0], [421.0, 677.0], [433.0, 636.25], [432.0, 650.0], [1.0, 11742.5]], "isOverall": false, "label": "HTTP请求", "isController": false}, {"data": [[153.83599999999984, 542.4473333333324]], "isOverall": false, "label": "HTTP请求-Aggregated", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 433.0, "title": "Time VS Threads"}},
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
        data : {"result": {"minY": 3494.4333333333334, "minX": 1.63089636E12, "maxY": 16619.9, "series": [{"data": [[1.63089636E12, 5311.35], [1.63089642E12, 16619.9]], "isOverall": false, "label": "Bytes received per second", "isController": false}, {"data": [[1.63089636E12, 7237.416666666667], [1.63089642E12, 3494.4333333333334]], "isOverall": false, "label": "Bytes sent per second", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.63089642E12, "title": "Bytes Throughput Over Time"}},
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
        data: {"result": {"minY": 437.28942115768496, "minX": 1.63089636E12, "maxY": 595.1841841841839, "series": [{"data": [[1.63089636E12, 595.1841841841839], [1.63089642E12, 437.28942115768496]], "isOverall": false, "label": "HTTP请求", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.63089642E12, "title": "Response Time Over Time"}},
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
        data: {"result": {"minY": 393.0359281437122, "minX": 1.63089636E12, "maxY": 594.5065065065072, "series": [{"data": [[1.63089636E12, 594.5065065065072], [1.63089642E12, 393.0359281437122]], "isOverall": false, "label": "HTTP请求", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.63089642E12, "title": "Latencies Over Time"}},
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
        data: {"result": {"minY": 198.10010010010004, "minX": 1.63089636E12, "maxY": 211.00199600798408, "series": [{"data": [[1.63089636E12, 198.10010010010004], [1.63089642E12, 211.00199600798408]], "isOverall": false, "label": "HTTP请求", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.63089642E12, "title": "Connect Time Over Time"}},
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
        data: {"result": {"minY": 48.0, "minX": 1.63089636E12, "maxY": 2455.0, "series": [{"data": [[1.63089636E12, 2455.0], [1.63089642E12, 801.0]], "isOverall": false, "label": "Max", "isController": false}, {"data": [[1.63089636E12, 961.0], [1.63089642E12, 668.2]], "isOverall": false, "label": "90th percentile", "isController": false}, {"data": [[1.63089636E12, 1161.0], [1.63089642E12, 741.3599999999997]], "isOverall": false, "label": "99th percentile", "isController": false}, {"data": [[1.63089636E12, 999.0], [1.63089642E12, 690.0]], "isOverall": false, "label": "95th percentile", "isController": false}, {"data": [[1.63089636E12, 48.0], [1.63089642E12, 60.0]], "isOverall": false, "label": "Min", "isController": false}, {"data": [[1.63089636E12, 677.0], [1.63089642E12, 346.0]], "isOverall": false, "label": "Median", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.63089642E12, "title": "Response Time Percentiles Over Time (successful requests only)"}},
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
    data: {"result": {"minY": 95.5, "minX": 10.0, "maxY": 21030.0, "series": [{"data": [[67.0, 248.0], [277.0, 539.0], [77.0, 95.5], [10.0, 881.0], [195.0, 642.0], [213.0, 167.0], [432.0, 848.0], [229.0, 253.0]], "isOverall": false, "label": "Successes", "isController": false}, {"data": [[77.0, 21030.0], [195.0, 731.0], [229.0, 403.5]], "isOverall": false, "label": "Failures", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 432.0, "title": "Response Time Vs Request"}},
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
    data: {"result": {"minY": 0.0, "minX": 10.0, "maxY": 881.0, "series": [{"data": [[67.0, 248.0], [277.0, 539.0], [77.0, 93.0], [10.0, 881.0], [195.0, 641.5], [213.0, 167.0], [432.0, 848.0], [229.0, 247.0]], "isOverall": false, "label": "Successes", "isController": false}, {"data": [[77.0, 0.0], [195.0, 731.0], [229.0, 402.5]], "isOverall": false, "label": "Failures", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 432.0, "title": "Latencies Vs Request"}},
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
        data: {"result": {"minY": 8.333333333333334, "minX": 1.63089636E12, "maxY": 16.666666666666668, "series": [{"data": [[1.63089636E12, 16.666666666666668], [1.63089642E12, 8.333333333333334]], "isOverall": false, "label": "hitsPerSecond", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.63089642E12, "title": "Hits Per Second"}},
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
        data: {"result": {"minY": 0.016666666666666666, "minX": 1.63089636E12, "maxY": 16.65, "series": [{"data": [[1.63089636E12, 16.65], [1.63089642E12, 8.283333333333333]], "isOverall": false, "label": "200", "isController": false}, {"data": [[1.63089642E12, 0.016666666666666666]], "isOverall": false, "label": "Non HTTP response code: org.apache.http.conn.HttpHostConnectException", "isController": false}, {"data": [[1.63089642E12, 0.05]], "isOverall": false, "label": "500", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.63089642E12, "title": "Codes Per Second"}},
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
        data: {"result": {"minY": 0.06666666666666667, "minX": 1.63089636E12, "maxY": 16.65, "series": [{"data": [[1.63089642E12, 0.06666666666666667]], "isOverall": false, "label": "HTTP请求-failure", "isController": false}, {"data": [[1.63089636E12, 16.65], [1.63089642E12, 8.283333333333333]], "isOverall": false, "label": "HTTP请求-success", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.63089642E12, "title": "Transactions Per Second"}},
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
        data: {"result": {"minY": 0.06666666666666667, "minX": 1.63089636E12, "maxY": 16.65, "series": [{"data": [[1.63089636E12, 16.65], [1.63089642E12, 8.283333333333333]], "isOverall": false, "label": "Transaction-success", "isController": false}, {"data": [[1.63089642E12, 0.06666666666666667]], "isOverall": false, "label": "Transaction-failure", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.63089642E12, "title": "Total Transactions Per Second"}},
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

