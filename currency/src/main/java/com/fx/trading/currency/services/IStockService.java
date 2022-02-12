package com.fx.trading.currency.services;

import java.math.BigDecimal;

public interface IStockService {
	public void update_exchangerate(int FXid, BigDecimal exchangerate);
	public void update_reverse_exchangerate(int FXid, BigDecimal exchangerate);
}