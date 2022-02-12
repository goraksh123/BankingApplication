package com.fx.trading.currency;

import com.fx.trading.currency.Beans.FXStock;
import com.fx.trading.currency.Beans.Order;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


import java.util.HashMap;
import java.util.Map;

@Repository
public class FXPlatform {


@Autowired()
@Qualifier("stocklist")
HashMap<String, FXStock> stocklist;


Map<FXStock, MatchingEngin> matchenginmap= new HashMap<FXStock, MatchingEngin>();


@PostConstruct
    public void init(){
        MatchEnginInicialisation();
    }

   private void MatchEnginInicialisation(){
       //List<FXStock> FXStockList = stocklist.values().stream().collect(Collectors.toList());
       stocklist.values().stream().forEach(stock -> matchenginmap.put(stock, new MatchingEngin()) );
    }

    public void BuyFXOrder(Order order) {
        FXStock fxstock = order.getStock();
        MatchingEngin engin= matchenginmap.get(fxstock);
        engin.BuyFXOrder(order);
    }


     public void SellFXOrder(Order order){
         FXStock fxstock = order.getStock();
         MatchingEngin engin= matchenginmap.get(fxstock);
         engin.SellFXOrder(order);
        }
}
