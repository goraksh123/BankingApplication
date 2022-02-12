package com.fx.trading.currency.controllers;


import com.fx.trading.currency.Beans.FXStock;
import com.fx.trading.currency.services.IFXPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("FXPlatForm")
public class FXPlatFormController {

@Autowired
IFXPlatformService fxPlatformService;

    @RequestMapping(method = RequestMethod.POST, value="/giveFXStockList", produces = "application/json")
    public List<FXStock> giveFXStockList(int userid){
        return fxPlatformService.giveFXStockList(userid);
    }

    @RequestMapping(method = RequestMethod.POST, value="/buyFXOrder", produces = "application/json")
    public Boolean buyFXOrder(int userid, String fxid, BigDecimal buyamount){
        return fxPlatformService.BuyFXOrder(userid,fxid,buyamount);
    }

    @RequestMapping(method = RequestMethod.POST, value="/saleFXOrder", produces = "application/json")
    public Boolean saleFXOrder(int userid, String fxid, BigDecimal buyamount){
        return fxPlatformService.SaleFXOrder(userid,fxid,buyamount);
    }
}
