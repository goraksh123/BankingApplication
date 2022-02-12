package com.fx.trading.currency.services;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.fx.trading.currency.pojo.OrderStatement;


@Service
public class UserService implements IUserService{


//Wallate updation	
		@Override
		public void addInwallate(int userid, BigDecimal Amount) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removefromwallate(int userid, BigDecimal Amount) {
			// TODO Auto-generated method stub
			
		}
//Statements and Order status
		@Override
		public OrderStatement GiveLastOrderStatus(int userid) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OrderStatement GiveCurrentMonthOrderStatus(int userid) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		 public OrderStatement GiveHistoricalOrderStatus(int userid, Date fromDate, Date todate) {
			// TODO Auto-generated method stub
						return null;
		}
		

}
