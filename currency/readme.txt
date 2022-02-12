


in this application request landing on FxPlatformController
where 3 method has defined.
1)givelist
    this give the whole CurrencyStocklist which has lodded by application
    it can we watchlist  or wholelist of currency

2)buyFXOrder
    user call this method to buy currency stock with userid , stock and amount  that i.e how much he want to buy

4)saleFXOrder
     user can sale current from his existing portfolio

Wallet:
   currenly wallet support GBP as base currency
   so in buy stock conversion from GBP to USD
   and in sale stock conversion from USD to GBP

   wallet currently support GBP as base currency
   need some modification  to support diff time of currency

FXPlatform.java :FxPlatform portfolio initialisation done inside this class
MatchingEngin : this is main part and critical part  of this Fx Platform
        working and step for conversion mentioned in MatchinEngin

1)Order when come to FXPlatformController first

2)then call trouted to FXPlatformService
   2a)where Object or Order is packed for ferther proccessing
   2b) where we also changing status of Order to  PENDING


3)then call transfer to FXPlatform
   1)Portfolio Initialisation is done in FXPlatform
   1)inside FXPlatform as per stock their is stock specific Matchingengin is preset
   2) call transfer to their specific Matchingengin

4)MatchingEngin
   inside matching engin 2 list presert
   1 for buy and 2nd for sale
   new order place as per their order of placing time
   we compire 1st of both entry every time

  if buy order placed then
   flow go to buy list place that order in list
   but then it compaire first entry of buy Order with fist sale Order
   if both preset then
   1)if both same then both FULLMATCHED for proccessing
   2)if any of  which is small value , then  it will  FULLMATCHED and large value is PARTIALLYMATCHED.

   then order Updated in Holding of USer as per buy or sale it added or subtracted with currency conversion

   a)in buy if for GBPUSD ---base currency is GBP so while adding to holding it converted to USD which exchange rate of 2
   and added in User Holdings

   b)in sale --currency was in USD so it removed from holding as it is as order of saleing in USD only

  Wallat transaction:

   1)Cosidering base currency for wallet is GBP

    in buying we buying using GBP so amount just removing from wallet without exchange.
    in sale curreny USD sale and return getting in GBP so at that time USD to GBP currency exchange happened
    and USD to GBP is 0.5 exchange rate so converting to GBP and adding in wallet



Please check MatchingEngin related more details in MatchingEngin.java












