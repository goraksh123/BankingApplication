package com.fx.trading.currency.Beans;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FXStock {	
	private String Fxid ; //-GBPUSD
	private String Primary  ; //- GBP
	private String Secondary ;//- USD
	private BigDecimal exchangerate; // 2
	private BigDecimal reverse_exchangerate ; //0.5

}
