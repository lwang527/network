package com.hidi;

import cern.colt.matrix.DoubleMatrix2D;

public class TestDense {

	public static void main(String[] args) {
		double[][] input_array = MatrixUtil.loadfile("input/input_layer.txt");
		double[][] input_param = MatrixUtil.loadfile("input/param_input.txt");
		
		Dense input = new Dense(input_array, input_param, ReLuType.Identify);
		DoubleMatrix2D hidden_matrix = input.compute();		
		
		double[][] logistic_param = MatrixUtil.loadfile("input/param_logistic.txt");
		
		Dense logistic = new Dense(hidden_matrix.toArray(), logistic_param, ReLuType.Sigmoid);
		DoubleMatrix2D activate_matrix = logistic.compute();
		
		MatrixUtil.processmatrix(activate_matrix, ReLuType.Discrete);
    	System.out.println(activate_matrix);
	}

}
