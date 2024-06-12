import { AfterViewInit, Component, OnInit, ViewChild } from "@angular/core";
import {
  ApexNonAxisChartSeries,
  ApexChart,
  ApexStroke,
  ApexTooltip,
  ApexDataLabels,
  ApexFill,
  ApexLegend,
  ApexPlotOptions,
  ApexResponsive,
  ApexMarkers,
  ApexXAxis,
  ApexYAxis,
  ApexStates,
  ApexAxisChartSeries,
  ApexGrid,
  ApexTheme,
  ApexTitleSubtitle,
} from "ng-apexcharts";
import { DashboardService } from "./dashboard.service";
import { CoreConfigService } from "@core/services/config.service";
import { NgbModal, NgbModalRef } from "@ng-bootstrap/ng-bootstrap";

export interface ChartOptions {
  series?: ApexAxisChartSeries;
  chart?: ApexChart;
  xaxis?: ApexXAxis;
  dataLabels?: ApexDataLabels;
  grid?: ApexGrid;
  stroke?: ApexStroke;
  legend?: ApexLegend;
  title?: ApexTitleSubtitle;
  colors?: string[];
  tooltip?: ApexTooltip;
  plotOptions?: ApexPlotOptions;
  yaxis?: ApexYAxis;
  fill?: ApexFill;
  labels?: string[];
  markers: ApexMarkers;
  theme: ApexTheme;
}

export interface ChartOptions2 {
  // Apex-non-axis-chart-series
  series?: ApexNonAxisChartSeries;
  chart?: ApexChart;
  stroke?: ApexStroke;
  tooltip?: ApexTooltip;
  dataLabels?: ApexDataLabels;
  fill?: ApexFill;
  colors?: string[];
  legend?: ApexLegend;
  labels?: any;
  plotOptions?: ApexPlotOptions;
  responsive?: ApexResponsive[];
  markers?: ApexMarkers[];
  xaxis?: ApexXAxis;
  yaxis?: ApexYAxis;
  states?: ApexStates;
}

@Component({
  selector: "app-dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.scss"],
})
export class DashboardComponent implements OnInit, AfterViewInit {
  @ViewChild("modal") public modal: NgbModalRef;
  @ViewChild("topCategoryChartRef") topCategoryChartRef: any;
  public topCategoryChart: Partial<ChartOptions2>;

  @ViewChild("revenuePriceChartRef") revenuePriceChartRef: any;
  public revenuePriceChart: Partial<ChartOptions>;

  @ViewChild("revenueTotalChartRef") revenueTotalChartRef: any;
  public revenueTotalChart: Partial<ChartOptions>;

  public month: number;

  public isMenuToggled = false;
  chartColors = {
    column: {
      series1: "#826af9",
      series2: "#d2b0ff",
      bg: "#f8d3ff",
    },
    success: {
      shade_100: "#7eefc7",
      shade_200: "#06774f",
    },
    donut: {
      series1: "#ffe700",
      series2: "#00d4bd",
      series3: "#826bf8",
      series4: "#2b9bf4",
      series5: "#FFA1A1",
    },
    area: {
      series3: "#a4f8cd",
      series2: "#60f2ca",
      series1: "#2bdac7",
    },
  };

  constructor(
    private _dashboardService: DashboardService,
    private _coreConfigService: CoreConfigService,
    private _modalService: NgbModal
  ) {
    // Apex Donut Chart
    this.topCategoryChart = {
      series: [],
      chart: {
        height: 350,
        type: "donut",
      },
      colors: [],
      plotOptions: {
        pie: {
          donut: {
            labels: {
              show: true,
              name: {
                fontSize: "2rem",
                fontFamily: "Montserrat",
              },
              value: {
                fontSize: "1rem",
                fontFamily: "Montserrat",
                formatter: function (val) {
                  return parseInt(val) + " quyển";
                },
              },
              total: {
                show: true,
                fontSize: "1.5rem",
                label: "Operational",
                formatter: function (w) {
                  return "";
                },
              },
            },
          },
        },
      },
      legend: {
        show: true,
        position: "bottom",
      },
      labels: [],
      responsive: [
        {
          breakpoint: 480,
          options: {
            chart: {
              height: 300,
            },
            legend: {
              position: "bottom",
            },
          },
        },
      ],
    };
  }

  ngOnInit(): void {
    this._dashboardService.getTopCategory().subscribe((res) => {
      this.topCategoryChart = {
        series: Object.values(res.data),
        chart: {
          height: 350,
          type: "donut",
        },
        colors: [
          this.chartColors.donut.series1,
          this.chartColors.donut.series2,
          this.chartColors.donut.series3,
          this.chartColors.donut.series5,
        ],
        plotOptions: {
          pie: {
            donut: {
              labels: {
                show: true,
                name: {
                  fontSize: "2rem",
                  fontFamily: "Montserrat",
                },
                value: {
                  fontSize: "1rem",
                  fontFamily: "Montserrat",
                  formatter: function (val) {
                    return parseInt(val) + " quyển";
                  },
                },
                total: {
                  show: true,
                  fontSize: "1.5rem",
                  label: "Tổng cộng",
                  formatter: function (w: any) {
                    const arr: any[] = w.config.series;
                    return arr.reduce((a, b) => a + b, 0) + " quyển";
                  },
                },
              },
            },
          },
        },
        legend: {
          show: true,
          position: "bottom",
        },
        labels: Object.keys(res.data),
        responsive: [
          {
            breakpoint: 480,
            options: {
              chart: {
                height: 300,
              },
              legend: {
                position: "bottom",
              },
            },
          },
        ],
      };
    });

    this._dashboardService.getRevenuePrice().subscribe((res) => {
      this.revenuePriceChart = {
        series: [
          {
            name: "",
            data: Object.values(res.data) as number[],
          },
        ],
        chart: {
          type: "bar",
          height: 400,
          stacked: true,
          toolbar: {
            show: false,
          },
          events: {
            click: (event, chartContext, config) => {
              if (config.dataPointIndex != -1) {
                this.month = parseInt(
                  Object.keys(res.data)[config.dataPointIndex].split("/")[0]
                );
                this._modalService.open(this.modal, {
                  centered: true,
                  size: "lg",
                });
              }
            },
          },
        },
        grid: {
          xaxis: {
            lines: {
              show: true,
            },
          },
        },
        legend: {
          show: true,
          position: "top",
          horizontalAlign: "left",
        },
        plotOptions: {
          bar: {
            columnWidth: "35%",
            colors: {
              backgroundBarRadius: 10,
            },
          },
        },
        colors: ["#28C76F"],
        dataLabels: {
          enabled: true,
        },
        stroke: {
          show: true,
          width: 2,
          colors: ["transparent"],
        },
        xaxis: {
          categories: Object.keys(res.data),
        },
        yaxis: {
          title: {
            text: "",
          },
        },
        fill: {
          opacity: 1,
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return val + "";
            },
          },
        },
      };
    });

    this._dashboardService.getRevenueTotal().subscribe((res) => {
      this.revenueTotalChart = {
        series: [
          {
            name: "",
            data: Object.values(res.data) as number[],
          },
        ],
        chart: {
          type: "bar",
          height: 400,
          stacked: true,
          toolbar: {
            show: false,
          },
          events: {
            click: (event, chartContext, config) => {
              if (config.dataPointIndex != -1) {
                this.month = parseInt(
                  Object.keys(res.data)[config.dataPointIndex].split("/")[0]
                );
                this._modalService.open(this.modal, {
                  centered: true,
                  size: "lg",
                });
              }
            },
          },
        },
        grid: {
          xaxis: {
            lines: {
              show: true,
            },
          },
        },
        legend: {
          show: true,
          position: "top",
          horizontalAlign: "left",
        },
        plotOptions: {
          bar: {
            columnWidth: "35%",
            colors: {
              backgroundBarRadius: 10,
            },
          },
        },
        colors: ["#28C76F"],
        dataLabels: {
          enabled: true,
        },
        stroke: {
          show: true,
          width: 2,
          colors: ["transparent"],
        },
        xaxis: {
          categories: Object.keys(res.data),
        },
        yaxis: {
          title: {
            text: "",
          },
        },
        fill: {
          opacity: 1,
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return val + "";
            },
          },
        },
      };
    });
  }

  ngAfterViewInit() {
    // Subscribe to core config changes
    this._coreConfigService.getConfig().subscribe((config) => {
      // If Menu Collapsed Changes
      if (
        config.layout.menu.collapsed === true ||
        config.layout.menu.collapsed === false
      ) {
        setTimeout(() => {
          // Get Dynamic Width for Charts
          this.isMenuToggled = true;
          this.revenuePriceChart.chart.width =
            this.revenuePriceChartRef?.nativeElement.offsetWidth;
          this.revenueTotalChart.chart.width =
            this.revenueTotalChartRef?.nativeElement.offsetWidth;
          this.topCategoryChart.chart.width =
            this.topCategoryChartRef?.nativeElement.offsetWidth;
        }, 900);
      }
    });
  }

  export() {
    this._dashboardService.getRevenuePrice().subscribe((res) => {
      const separator = ",";
      let total = 0;
      const keys = ["STT", "Tháng", "Doanh thu"];
      const csvData =
        keys.join(separator) +
        "\n" +
        Object.keys(res.data)
          .map((key: any, index) => {
            total += res.data[key];
            return index + 1 + separator + key + separator + res.data[key];
          })
          .join("\n") +
        `\nTổng,,${total}`;
      const blob = new Blob(["\ufeff" + csvData], {
        type: "text/csv;charset=utf-8;",
      });
      const link = document.createElement("a");
      if (link.download !== undefined) {
        const url = URL.createObjectURL(blob);
        link.setAttribute("href", url);
        link.setAttribute("download", "Doanh thu");
        link.style.visibility = "hidden";
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      }
    });
  }
}
