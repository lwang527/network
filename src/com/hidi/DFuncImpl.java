package com.hidi;

import cern.colt.function.DoubleFunction;

public class DFuncImpl implements DoubleFunction{

	private ReLuType relutype;
	
	public DFuncImpl(ReLuType type){
		this.relutype = type;
	}
	
	public double apply(double data) {
		double result = 0;
		if(this.relutype.equals(ReLuType.Sigmoid)){
			result = 1/(1 + Math.exp(0 - data));
		}else if(this.relutype.equals(ReLuType.Identify)){
			result = data;
		}else if(this.relutype.equals(ReLuType.Discrete)){
			result = data >= 0.5 ? 1 : -1;
		}
		return result;
	}
}
