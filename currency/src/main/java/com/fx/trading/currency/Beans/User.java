package com.fx.trading.currency.Beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import lombok.Data;

@Data
public class User {
	private int userid ; 
	private String credential ;
	private Date dataOFJoining ;
	private BigDecimal wallet ;
	//LinkedHashMap <FXSTOCKID_in_String ,FXStockHoldings_object_For_that_stock>
	LinkedHashMap <String ,FXStockHoldings>  holdings;
	LinkedList<Order> orders;
	
}
