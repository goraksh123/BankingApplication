package com.fx.trading.currency;

import com.fx.trading.currency.Beans.FXStockHoldings;
import com.fx.trading.currency.Beans.Order;
import com.fx.trading.currency.Beans.User;
import com.fx.trading.currency.utils.OrderStatus;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class MatchingEngin {
 LinkedList<Order> buylist = new LinkedList<>();
 LinkedList<Order> salelist = new LinkedList<>();

public void BuyFXOrder(Order order){
    buylist.add(order);
    matchmaking(true);
}

public void SellFXOrder(Order order){
    salelist.add(order);
    matchmaking(false);
}


private void matchmaking(boolean isbuyer){
    Order buy = buylist.getFirst();
    Order sale = salelist.getFirst();

    /**
     * any one is null just return ..no ferther proccessing
     */
    if(buy ==null | sale == null)
        return;

    /**
     * considering even we purchesing USD Buy currency  in GBP
     * and Sale price in USD
     * for compirision for matching u need to exchange buy currency from GBP to USD
     * or sale currency from USD to GBP but we decided to go to compire 2nd currency
     * thats why conversion of GBP to USD and then compire
     *
     *
     */
    BigDecimal buy_OrderPrice_after_exchange = buy.getOrderPrice().multiply(buy.getStock().getExchangerate());
    BigDecimal sale_OrderPrice=sale.getOrderPrice();
   // if(buy_OrderPrice_after_exchange == sale_OrderPrice) {
      if (buy_OrderPrice_after_exchange.compareTo(sale_OrderPrice) == 0) {
        markfullBuy(buy);
        markfullsale(sale);
    }

    if(isbuyer)
    {
        if (buy_OrderPrice_after_exchange.compareTo(sale_OrderPrice) == -1) {
            markfullBuy(buy);
            markPartialSale(buy,sale);
        }

        if (buy_OrderPrice_after_exchange.compareTo(sale_OrderPrice) == 1) {
            markPartialBuy(buy,sale);
            markfullsale(sale);
        }
    }
    else{

        if (sale_OrderPrice.compareTo(buy_OrderPrice_after_exchange) == 1) {
            markPartialSale(buy,sale);
            markfullBuy(buy);
        }

        if (sale_OrderPrice.compareTo(buy_OrderPrice_after_exchange) == -1) {
             markfullsale(sale);
             markPartialBuy(buy,sale);
        }
    }
}

    public void markfullBuy(Order buy){
        /**
         * Total 5 steps for Fullmatch
         * 1)ark buy order as fullmatched
         * 2)add new prices on User Holdings with addition of new currency
         * 3)Currency Conversion to GBP to USD
         * 4)update holding in portfolio of user
         * 5)delete the first order/current from buylist
         * 6)remove money from wallet of User cosidering BASE currency of wallet for now GBP only
         */

        /**mark buy order as fullmatched*/
        buy.setStatus(OrderStatus.fullmatched);


        /**
         * add new prices on Holding
        Currency Conversion to GBP to USD for storing in Holding*/
        User user = buy.getUser();
        LinkedHashMap<String , FXStockHoldings> holdings =user.getHoldings();
        FXStockHoldings holding=holdings.get(buy.getStock().getFxid());

        BigDecimal OrderPrice=buy.getOrderPrice();
        /**
        Exchange:
         for Holding Object:
        as you are buying USD from GBP so exchange is invlove and new exchanged currency value added in holding

         for wallate:
        but for wallet not exchange as your expance is in same curreny so GBP conversion nor require
         */
        BigDecimal OrderPrice_after_exchange=OrderPrice.multiply(buy.getStock().getExchangerate());

        if(holding == null)
        {
            holding= new  FXStockHoldings();
            holding.setFxstock(buy.getStock());
            holding.setTotalprice(OrderPrice_after_exchange);
        }
        else{
            BigDecimal exesting_stockcurrency_price=holding.getTotalprice();
            BigDecimal final_stockcurrenty_price = exesting_stockcurrency_price.add(OrderPrice_after_exchange);
            holding.setTotalprice(final_stockcurrenty_price);
        }

        /**update holding in portfolio of user*/
        holdings.put(buy.getStock().getFxid(),holding);

        /**delete the first order from buylist which is above*/
        buylist.removeFirst();

        /**remove money from wallate as per GBP/USD 2/0.5 as per  exchange rate
         here you are buying so removing in their amount in GBP
         For Now wallet Supporting  on only GBP as base currency
         */
        /**
        Currency Exchange:
         for wallate echange is not require as base currency is GBP and you are also purchesing GBP/USD currency
         */
        BigDecimal current_balance=user.getWallet();
        BigDecimal final_balance_in_GBP=current_balance.subtract(OrderPrice);
        user.setWallet(current_balance);
}


    public void markfullsale(Order sale){
        /**
         * Total 5 steps for Fullmatch
         * 1)mark sale order as fullmatched
         * 2)add new prices on User Holdings with substracting of new prices/currency
         * 3)NOT REQUIRE THIS STEP IN SALE-->@@@ Currency Conversion to GBP to USD
         * 4)update holding in portfolio of user
         * 5)delete the first order/current from saleList
         * 6)adding money to wallet of User cosidering BASE currency of wallet for now GBP so USD to GBP conversion
         */


        /**mark full sell fullmatched*/
      sale.setStatus(OrderStatus.fullmatched);

        /** remove that much value from user holding*/
        User user = sale.getUser();
        LinkedHashMap<String ,FXStockHoldings> holdings =user.getHoldings();
        FXStockHoldings holding=holdings.get(sale.getStock().getFxid());

        /**
         * for GBPUSD holding is in USD so no conversion is required in Holding Object level
         *
         * But For Wallet
         * exchange is require as after selling USD need to convert to GBP as wallat Currency is GBP only
         *
         */
        BigDecimal OrderPrice=sale.getOrderPrice();
        //BigDecimal OrderPrice_after_exchange=OrderPrice.multiply(sale.getStock().getReverse_exchangerate());

        BigDecimal exesting_stockcurrency_price=holding.getTotalprice();
        BigDecimal final_stockcurrenty_price = exesting_stockcurrency_price.subtract(OrderPrice);
        holding.setTotalprice(final_stockcurrenty_price);

        /**update that value in user Portfolio*/
        holdings.put(sale.getStock().getFxid(),holding);

        /**delete the first order from buylist which is above*/
        salelist.removeFirst();

        /**
         * Currency Exchange:
         *for wallate echange is  require   because holding are in USD which you saleing and adding Moneny
         *in wallate which base  base currency is GBP
         */
        BigDecimal current_balance=user.getWallet();
        BigDecimal OrderPrice_after_revers_exchange=OrderPrice.multiply(sale.getStock().getReverse_exchangerate());
        BigDecimal final_balance_in_GBP=current_balance.add(OrderPrice_after_revers_exchange);
        user.setWallet(final_balance_in_GBP);
}


    public void markPartialBuy(Order buy, Order sale){

        /**
         * Total 5 steps for partiallymatched
         * 1)Mark buy order as partiallymatched
         * 2)add new prices on User Holdings with addition of new currency
         *   2a)fetching Holdings if any from portfolio
         *   2b)in partial buy we cosidering full sale amount that will buy which we are adding in holding
         *   2c)Currency Conversion to GBP to USD
         * 3)update holding in portfolio of user
         * 5)delete the first Order/current from buylist
         *     5a)in Partial we removing 1st original Order
         *     5b)we creating new Order with Pending partial OrderPrice
         *     5c)Pushing this new Pending partial Order on Original Order location i.e first
         * 6)remove money from wallet of User cosidering BASE currency of wallet for now GBP only
         *      6a)As base currency of wallate is GBP we are removing only that much GBP value
         *         which we procecced
         */

        /**mark buy order as partiallymatched*/
        buy.setStatus(OrderStatus.partiallymatched);

        /**
         * add new prices on Holding
         Currency Conversion to GBP to USD for storing in Holding*/

        //fetching Holdings if any from portfolio
        User user = buy.getUser();
        LinkedHashMap<String ,FXStockHoldings> holdings =user.getHoldings();
        FXStockHoldings holding=holdings.get(buy.getStock().getFxid());

        //in partial buy we cosidering full sale amount that will buy which we are adding in holding
        BigDecimal OrderPrice_new_already_exchanged_price =sale.getOrderPrice();
        BigDecimal buy_OrderPrice_after_exchange_original = buy.getOrderPrice().multiply(buy.getStock().getExchangerate());
        BigDecimal buy_OrderPrice_after_exchange_pending = buy_OrderPrice_after_exchange_original.subtract(OrderPrice_new_already_exchanged_price);


        if(holding == null)
        {
            holding= new  FXStockHoldings();
            holding.setFxstock(buy.getStock());
            holding.setTotalprice(OrderPrice_new_already_exchanged_price);
        }
        else{
            BigDecimal exesting_stockcurrency_price=holding.getTotalprice();
            BigDecimal final_stockcurrenty_price = exesting_stockcurrency_price.add(OrderPrice_new_already_exchanged_price);
            holding.setTotalprice(final_stockcurrenty_price);
        }

        /**update holding in portfolio of user*/
        holdings.put(buy.getStock().getFxid(),holding);


        /**delete the first order from buylist which is above  but we have referance of that Order buy */
        buylist.removeFirst();

        //setting Current OrderPrice in OriginalOrderPrice for OriginalOrderPrice Backup for re-processing pending order
        if(buy.getOriginalOrderPrice() == null)
        {
            buy.setOriginalOrderPrice(buy.getOrderPrice());
        }
        //setting PendingOrderPrice in OrderPrice
        //we have calculated pending OrderPrice in USD but in GBP-USD buy Assuming Buy Order in GBP
        //so we are converting USD back to GBP with parameter Reverse_exchangerate of FxStock
        buy.setOrderPrice(buy_OrderPrice_after_exchange_pending.multiply(buy.getStock().getReverse_exchangerate()));
        buylist.addFirst(buy);


        /**
         Currency Exchange:
         for wallate echange is not require as base currency is GBP and you are also purchesing GBP/USD currency
         */
        BigDecimal current_balance=user.getWallet();
        //As sale Order in USD converting to GBP i.e to BASE currency
        BigDecimal OrderPrice_new_base_currency_price =sale.getOrderPrice().multiply(buy.getStock().getReverse_exchangerate());
        BigDecimal final_balance_in_GBP=current_balance.subtract(OrderPrice_new_base_currency_price);
        user.setWallet(current_balance);


        /**
         * again calling matchmaking(True)  with True as it is Buy Order and as we haved pushed new partial Order
         */
        matchmaking(true);

    }



    public void markPartialSale(Order buy, Order sale){

        /**
         * Total 5 steps for partiallymatched
         * 1)Mark sale order as partiallymatched
         * 2)add new prices on User Holdings with addition of new currency
         *   2a)fetching Holdings if any from portfolio
         *   2b)in partial sale we cosidering full buy amount that will sale which we are adding in holding
         *   2c)Currency Conversion to GBP to USD for Buyvalue
         * 3)update holding in portfolio of user
         * 5)delete the first Order/current from salelist
         *     5a)in Partial we removing 1st original Order
         *     5b)we creating new Order with Pending partial OrderPrice
         *     5c)Pushing this new Pending partial Order on Original Order location i.e first
         * 6)adding money from wallet of User cosidering BASE currency of wallet for now GBP only
         *      6a)have we have sale USD then with current USD to GBP rate GBP current value need to add in wallet
         */


        /**mark sale order as partiallymatched*/
        sale.setStatus(OrderStatus.partiallymatched);


        /** remove that much value from user holding*/

        //fetching Holdings if any from portfolio
        User user = sale.getUser();
        LinkedHashMap<String ,FXStockHoldings> holdings =user.getHoldings();
        FXStockHoldings holding=holdings.get(sale.getStock().getFxid());

        //in partial sale we cosidering full buy amount that will sale which we are adding in holding
        BigDecimal sale_OrderPrice_after_exchange_new = buy.getOrderPrice().multiply(buy.getStock().getExchangerate());
        BigDecimal sale_OrderPrice_original = sale.getOrderPrice();
        BigDecimal sale_OrderPrice_pending = sale_OrderPrice_original.subtract(sale_OrderPrice_after_exchange_new);

        BigDecimal exesting_holding_stockcurrency_price=holding.getTotalprice();
        BigDecimal final_stockholding_currenty_price = exesting_holding_stockcurrency_price.subtract(sale_OrderPrice_after_exchange_new);
        holding.setTotalprice(final_stockholding_currenty_price);

        /**update that value in user Portfolio*/
        holdings.put(sale.getStock().getFxid(),holding);

        /**delete the first order from salelist which is above  but we have referance of that Order sale */
        salelist.removeFirst();

        //setting Current OrderPrice in OriginalOrderPrice for OriginalOrderPrice Backup for re-processing pending order
        if(sale.getOriginalOrderPrice() == null)
        {
            sale.setOriginalOrderPrice(sale.getOrderPrice());
        }

        //setting PendingOrderPrice in OrderPrice
        //we have calculated pending OrderPrice and it is sale so it is in USD only
        //as it is pending order we adding again first with pending partial OrderPrice
        sale.setOrderPrice(sale_OrderPrice_pending);
        salelist.addFirst(sale);

        /**
         * **Wallet Transition**
         *
         * Currency Exchange:
         *for wallate echange is  require   because holding are in USD which you saleing and adding Moneny
         *in wallate which base  base currency is GBP
         */
        BigDecimal current_balance=user.getWallet();
        BigDecimal OrderPrice_after_revers_exchange=sale_OrderPrice_after_exchange_new.multiply(sale.getStock().getReverse_exchangerate());
        BigDecimal final_balance_in_GBP=current_balance.add(OrderPrice_after_revers_exchange);
        user.setWallet(final_balance_in_GBP);

        /**
         * again calling matchmaking(FALSE)  with False as it is sale and we haved pushed new partial sale Order
         */
        matchmaking(false);
    }
}
