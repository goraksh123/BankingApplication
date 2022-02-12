package com.fx.trading.currency.services;

import com.fx.trading.currency.Beans.FXStock;

import java.math.BigDecimal;
import java.util.List;

public interface IFXPlatformService {
    List<FXStock> giveFXStockList(int userid);
    public boolean BuyFXOrder(int userid, String fxid, BigDecimal buyamount);
    public boolean SaleFXOrder(int userid, String fxid, BigDecimal buyamount);
}