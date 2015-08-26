package com.nullcognition.asyncservice;
// ersin 26/08/15 Copyright (c) 2015+ All rights reserved.

import com.joanzapata.android.asyncservice.api.annotation.AsyncService;
import com.joanzapata.android.asyncservice.api.annotation.Init;

@AsyncService
public class DemoService{

	public DemoService(){}
//	@ApplicationContext
//	static Context context;

	// not a singleton thus all field must be static
	// RestClient client
	static StringGenerator stringGenerator = new StringGenerator();

	//
	@Init static void init(){
		stringGenerator.setModifier(8);
	}

	public String getString(String string){

//		// runs asynchronously
		try{ Thread.sleep(1000);}
		catch(InterruptedException e){ e.printStackTrace();}
//
		return string + stringGenerator.generate();
		// returning null here will not propagate
		// the message
	}
}
