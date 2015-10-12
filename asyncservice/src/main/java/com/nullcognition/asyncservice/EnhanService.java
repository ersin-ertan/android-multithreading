package com.nullcognition.asyncservice;
// ersin 27/08/15 Copyright (c) 2015+ All rights reserved.


import com.joanzapata.android.asyncservice.api.EnhancedService;
import com.joanzapata.android.asyncservice.api.annotation.Null;

import java.io.Serializable;
import java.util.List;

public abstract class EnhanService implements EnhancedService{

	EnhanService(){}

	@Override public void send(final Object message){
	}

	@Override public void cache(final String key, final Serializable object){

	}

	@Override public void cacheList(final String key, final List<? extends Serializable> object){

	}

	@Override public <T extends Serializable> T getCached(final String key, final Class<T> returnType){
		return null;
	}

	@Override public <T extends Serializable> List<T> getCachedList(final String key, final Class<T> returnType){
		return null;
	}

	@Override public void clearCache(final String key){

	}

	@Override public void clearCache(){

	}
}



