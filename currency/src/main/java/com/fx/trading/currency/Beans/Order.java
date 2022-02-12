package com.fx.trading.currency.Beans;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fx.trading.currency.utils.OrderStatus;
import lombok.Data;

@Data
public class Order {
	public int orderid;
	public FXStock stock;
	public User user;
	public Boolean isBuy;
	public OrderStatus status;
	public LocalDateTime orderdate;
	public BigDecimal OrderPrice;
	public BigDecimal partialproccessedPrice;
	public BigDecimal OriginalOrderPrice;
}
