package com.fx.trading.currency.configurations;

import com.fx.trading.currency.utils.SyncSequenceGenerator;
import org.springframework.context.annotation.Bean;

@Configuration
public class OtherConfigration {

    @Bean( name="SyncSequenceGenerator")
    public SyncSequenceGenerator syncSequenceGenerator()
    {
        return new SyncSequenceGenerator();
    }
}
