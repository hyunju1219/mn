package com.meongnyang.shop.dto.request.admin;

import com.meongnyang.shop.entity.Stock;
import lombok.Data;

@Data
public class ReqAlertSettingDto {
    private Long stockId;
    private int alertSetting;
    private Long minAlertQuantity;

    public Stock toEntity() {
        return Stock.builder()
                .id(stockId)
                .alertSetting(alertSetting)
                .minAlertQuantity(minAlertQuantity)
                .build();
    }
}
