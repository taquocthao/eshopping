package com.tathao.eshopping.model.command;

import com.tathao.eshopping.model.dto.OrderOutletDTO;

public class OrderOutletCommand extends AbstractCommand<OrderOutletDTO> {

    private Integer total;
    private Integer totalWaitingForConfirm;
    private Integer totalPicking;
    private Integer totalDelivering;
    private Integer totalSuccess;
    private Integer totalCancel;
    private Integer totalReturn;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalWaitingForConfirm() {
        return totalWaitingForConfirm;
    }

    public void setTotalWaitingForConfirm(Integer totalWaitingForConfirm) {
        this.totalWaitingForConfirm = totalWaitingForConfirm;
    }

    public Integer getTotalPicking() {
        return totalPicking;
    }

    public void setTotalPicking(Integer totalPicking) {
        this.totalPicking = totalPicking;
    }

    public Integer getTotalDelivering() {
        return totalDelivering;
    }

    public void setTotalDelivering(Integer totalDelivering) {
        this.totalDelivering = totalDelivering;
    }

    public Integer getTotalSuccess() {
        return totalSuccess;
    }

    public void setTotalSuccess(Integer totalSuccess) {
        this.totalSuccess = totalSuccess;
    }

    public Integer getTotalCancel() {
        return totalCancel;
    }

    public void setTotalCancel(Integer totalCancel) {
        this.totalCancel = totalCancel;
    }

    public Integer getTotalReturn() {
        return totalReturn;
    }

    public void setTotalReturn(Integer totalReturn) {
        this.totalReturn = totalReturn;
    }
}
