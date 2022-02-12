package com.fx.trading.currency.Beans;

import java.math.BigDecimal;

import lombok.*;


@Getter @Setter
public class FXStockHoldings {
   	private FXStock fxstock;
	private BigDecimal totalprice;
}
