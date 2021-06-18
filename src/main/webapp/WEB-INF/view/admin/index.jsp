<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>

<div class="container_fullwidth">
    <div class="container-fluid">
        <%--overview--%>
        <div class="card">
            <div class="card-body">
                <div class="overview">
                    <h4 class="mb-4">KẾT QUẢ BÁN HÀNG HÔM NAY</h4>
                    <div class="overview-detail">
                        <div class="row">
                            <div class="col-sm-3 col-md-3 detail-order">
                                <div class="detail">
                                    <!--Number of order-->
                                    <div>
                                        <span>1 </span><span>Hóa đơn</span>
                                    </div>
                                    <div>18,982,000</div>
                                </div>
                                <span class="separate"></span>
                            </div>
                            <div class="col-sm-3 col-md-3 detail-return-order">
                                <div class="detail">
                                    <div>
                                        <span>0 </span><span>Phiếu</span>
                                    </div>
                                    <div>Trả hàng</div>
                                </div>
                                <span class="separate"></span>
                            </div>
                            <div class="col-sm-3 col-md-3 detail-compare-yesterday">
                                <div class="detail">
                                    <div>
                                        <span><i class="fa fa-arrow-up"></i></span><span>88.57%</span>
                                    </div>
                                    <div>So với hôm qua</div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-md-3 detail-compare-last-month">
                                <div class="detail">
                                    <div>
                                        <span><i class="fa fa-arrow-up"></i></span><span>11.86%</span>
                                    </div>
                                    <div>So với cùng kì tháng trước</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--revenue--%>
        <div class="card">
            <div class="card-body">
                <div class="revenue">
                    <h4 class="mb-4">DOANH THU TUẦN NÀY</h4>
                    <div class="revenue-detail">
                        <canvas id="revenueChart"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        const labels = ["Mo", "Tu", "We", "Thu", "Fr", "Sa", "Su"];
        const data = {
            labels: labels,
            datasets: [{
                label: 'Chi nhánh trung tâm',
                data: [65, 59, 80, 81, 56, 55, 40],
                backgroundColor: ['rgba(75,192,192,0.8)']
            }]
        }

        const config = {
            type: 'bar',
            data: data,
            options: {
                y: {
                    beginAtZero: true
                }
            }
        }

        var myChart = new Chart(document.getElementById("revenueChart"), config)
    });
</script>