package com.fx.trading.currency.services;

import java.math.BigDecimal;
import java.util.Date;
import com.fx.trading.currency.pojo.OrderStatement;

public interface IUserService {

//wallet updation/wallet rechange functionality
		public void addInwallate(int userid, BigDecimal Amount  ) ;
		public void removefromwallate(int userid, BigDecimal Amount ) ;
		
		
//Statements and Order status
		public OrderStatement GiveLastOrderStatus(int userid);
	    public OrderStatement GiveCurrentMonthOrderStatus(int userid) ;
	    public OrderStatement GiveHistoricalOrderStatus(int userid, Date fromDate, Date todate) ;
}
