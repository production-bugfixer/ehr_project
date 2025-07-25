package com.ehr.authenticate.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.ehr.authenticate.config.MessageSourceFactory;

public class Controller {
	@Autowired
    public MessageSourceFactory messageSourceFactory;
}
