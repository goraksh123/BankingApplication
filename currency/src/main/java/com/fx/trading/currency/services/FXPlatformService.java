package com.fx.trading.currency.services;

import com.fx.trading.currency.Beans.FXStock;
import com.fx.trading.currency.Beans.Order;
import com.fx.trading.currency.Beans.User;
import com.fx.trading.currency.FXPlatform;
import com.fx.trading.currency.utils.OrderStatus;
import com.fx.trading.currency.utils.SyncSequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FXPlatformService  implements IFXPlatformService {

    @Autowired()
    @Qualifier("stocklist")
    HashMap<String, FXStock> stocklist;

    @Autowired()
    @Qualifier("userlist")
    HashMap<Integer, User> userlist;

    @Autowired()
    @Qualifier("SyncSequenceGenerator")
    public SyncSequenceGenerator syncSequenceGenerator;

    @Autowired
    FXPlatform fxPlatformrepository;

    @Override
    public List<FXStock> giveFXStockList(int userid) {
        List<FXStock> FXStockList = stocklist.values().stream()
                .collect(Collectors.toList());
        return FXStockList;
    }

    @Override
    public boolean BuyFXOrder(int userid, String fxid, BigDecimal buyamount) {
        Order order = new Order();
        order.setOrderid((int) syncSequenceGenerator.getNext());
        order.setStock(stocklist.get(fxid));
        order.setUser(userlist.get(userid));
        order.setIsBuy(Boolean.TRUE);
        order.setStatus(OrderStatus.pending);
        order.setOrderdate(LocalDateTime.now());
        order.setOrderPrice(buyamount);

        fxPlatformrepository.BuyFXOrder(order);
        return true;
    }

    @Override
    public boolean SaleFXOrder(int userid, String fxid, BigDecimal buyamount) {

        Order order = new Order();
        order.setOrderid((int) syncSequenceGenerator.getNext());
        order.setStock(stocklist.get(fxid));
        order.setUser(userlist.get(userid));
        order.setIsBuy(Boolean.FALSE);
        order.setStatus(OrderStatus.pending);
        order.setOrderdate(LocalDateTime.now());
        order.setOrderPrice(buyamount);

        fxPlatformrepository.SellFXOrder(order);
        return true;
    }
}