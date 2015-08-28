package com.nullcognition.asyncservice;
// ersin 27/08/15 Copyright (c) 2015+ All rights reserved.


import com.joanzapata.android.asyncservice.api.ErrorMapper;
import com.joanzapata.android.asyncservice.api.annotation.AsyncService;
import com.joanzapata.android.asyncservice.api.annotation.CacheThenCall;
import com.joanzapata.android.asyncservice.api.annotation.ErrorManagement;
import com.joanzapata.android.asyncservice.api.annotation.Null;
import com.joanzapata.android.asyncservice.api.annotation.ThrowerParam;

import java.io.Serializable;
import java.util.List;

@AsyncService //(errorMapper = EService.StatusCodeMapper.class)
public class EService extends EnhanService{

	public static final String KEY = "cachable:{group}";

	@Null(UserNotFound.class) String getUser(String username){
		String user = null; // ...
//		if (user == null) send(new UserNotFound(username));
		return user; // annotation will take care of that
	}

	@CacheThenCall// in place of calling then saving in the db with key value pair
	public String getCacheable(){
		return "the network operation";
	}

	@CacheThenCall(value = KEY) // test this, not quite sure
	public String getString(int group){
		return "network operation";
	}

	@ErrorManagement({
//			                 @On(code = 409, send = MyException.class) // where is the on and send coming from
	                 })
	public String returnNetworkCall(String username){
		// if 404 would have to check codes and return the correct exception
		// so annotate the class
		return "could be error code";
	}

//	static class MyException extends ErrorMessage{ // what is ErrorMessage

//		public MyException(Throwable throwable, @ThrowerParam("username") String username){super(throwable);}
//	}


//	public class StatusCodeMapper implements ErrorMapper{
//		@Override
//		public int mapError(Throwable throwable) {
//			if (throwable instanceof HttpStatusCodeException) // what is going on here
//				return ((HttpStatusCodeException) throwable).getStatusCode().value(); // not sure
//			else return SKIP;
//		}
//	}

	@Override public void send(final Object message){
		super.send(message);
	}

	@Override public void cache(final String key, final Serializable object){
		super.cache(key, object);
	}

	@Override public void cacheList(final String key, final List<? extends Serializable> object){
		super.cacheList(key, object);
	}

	@Override public <T extends Serializable> T getCached(final String key, final Class<T> returnType){
		return super.getCached(key, returnType);
	}

	@Override public <T extends Serializable> List<T> getCachedList(final String key, final Class<T> returnType){
		return super.getCachedList(key, returnType);
	}

	@Override public void clearCache(final String key){
		super.clearCache(key);
	}

	@Override public void clearCache(){
		super.clearCache();
	}
}
