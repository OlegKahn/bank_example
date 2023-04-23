package com.bank.publicinfo.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilMapper {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


}
