package com.hidi;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.SmpBlas;

public class MatrixUtil {

	public static double[][] loadfile(String path){
		String file = FileUtil.readFile(path);
		String datas[] = file.split(":");
		int rows = datas.length;
		int column = datas[0].split(",").length;
		double result[][] = new double[rows][column];
		for(int i = 0; i < rows; i ++) {
			String lines[] = datas[i].split(",");
			for(int j = 0; j < column; j ++){
				result[i][j] = Double.parseDouble(lines[j]);
			}
		}
		return result;
	}
	
	public static double[][] initmatrix(int rows, int columns) {
		double result[][] = new double[rows][columns];
		for(int i = 0; i < rows; i ++) {
			for(int j = 0; j < columns; j ++){
				result[i][j] = 1;
			}
		}
		return result;
	}
	
	public static void processmatrix(DoubleMatrix2D multi, ReLuType rlu){
		SmpBlas.smpBlas.assign(multi, new DFuncImpl(rlu));
	}
}
