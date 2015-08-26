package com.nullcognition.asyncservice;
// ersin 26/08/15 Copyright (c) 2015+ All rights reserved.


public class StringGenerator{

	StringGenerator(){}

	int i = 1;
	int modifier = 2;

	public void setModifier(int mod){modifier = mod;}

	public String generate(){
		return " generated string and int " +
				String.valueOf(i * modifier);
	}
}
