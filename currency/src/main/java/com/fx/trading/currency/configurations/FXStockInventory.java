package com.fx.trading.currency.configurations;

import com.fx.trading.currency.Beans.FXStock;
import com.fx.trading.currency.Beans.FXStockHoldings;
import com.fx.trading.currency.Beans.Order;
import com.fx.trading.currency.Beans.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

@Configuration
public class FXStockInventory {

	@Bean ( name="stocklist")
	public HashMap<String, FXStock> stocklist()
	{
		HashMap<String, FXStock> stocks = new HashMap<String,FXStock>();
		FXStock stock = new FXStock();
		stock.setFxid("GBPUSD");
		stock.setPrimary("GBP");
		stock.setSecondary("USD");
		stock.setExchangerate(new BigDecimal(2.0));
		stock.setReverse_exchangerate(new BigDecimal(0.5));
		stocks.put("GBPUSD",stock);
		return stocks;
	}

	@Bean ( name="userlist")
	public HashMap<Integer, User> userlist()
	{
		HashMap<Integer, User> userlist = new HashMap<Integer,User>();
		int User1 =002145;
		User user = new User();
		user.setUserid(User1);
		user.setCredential("PASSWORD");
		user.setDataOFJoining(new Date(2014, 02, 11));
		user.setWallet(new BigDecimal(500000.0));
		LinkedHashMap<String , FXStockHoldings> holdings= new LinkedHashMap<>();
		LinkedList<Order> orders = new LinkedList<>();
		user.setHoldings(holdings);
		user.setOrders(orders);
		userlist.put(User1,user );
		return userlist;
	}
}
