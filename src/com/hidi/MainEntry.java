package com.hidi;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.SeqBlas;

public class MainEntry {
	private static int layer = 2;

	public static void main(String[] args) {
		//read input array
		double[][] xarray = MatrixUtil.loadfile("x.txt");
		DoubleMatrix2D xmatrix = new DenseDoubleMatrix2D(xarray.length,xarray[0].length);
		xmatrix.assign(xarray);
		System.out.println("xarray:" + xmatrix);
		System.out.println();
		
        for(int i = 1; i <= layer; i++){
        	//read parameter array on per layer
        	double param[][] = MatrixUtil.loadfile("param" + i + ".txt");
        	DoubleMatrix2D pmatrix = new DenseDoubleMatrix2D(param.length,param[0].length);
        	pmatrix.assign(param);
        	System.out.println("param " + i + ": " + pmatrix);
        	System.out.println();
        	
        	DoubleMatrix2D suba0 = pmatrix.viewPart(0, 0, pmatrix.rows(), 1);
//        	System.out.println("suba0:" + suba0);
        	DoubleMatrix2D subam = pmatrix.viewPart(0, 1, pmatrix.rows(), pmatrix.columns() - 1);
//        	System.out.println("subam:" + subam);
        	
        	double [][] subxarr = MatrixUtil.initmatrix(suba0.columns(), xmatrix.columns());
        	DoubleMatrix2D subxmatrix = new DenseDoubleMatrix2D(subxarr.length,subxarr[0].length);
        	subxmatrix.assign(subxarr);
//        	System.out.println("subxmatrix:" + subxmatrix);
        	
        	
        	xmatrix = Algebra.DEFAULT.mult(subam, xmatrix);
        	SeqBlas.seqBlas.daxpy(1, Algebra.DEFAULT.mult(suba0, subxmatrix), xmatrix);
//        	System.out.println("result:" + xmatrix);
        	
        	ReLuType rlu = ReLuType.Identify;
        	if(i == 2) rlu = ReLuType.Sigmoid;
        	SeqBlas.seqBlas.assign(xmatrix, new DFuncImpl(rlu));
        	System.out.println("computed: " + xmatrix);
        	System.out.println();
        	
        	double[][] correct = MatrixUtil.loadfile("layer" + i + ".txt");
    		DoubleMatrix2D cmatrix = new DenseDoubleMatrix2D(correct.length,correct[0].length);
    		cmatrix.assign(correct);
    		
    		SeqBlas.seqBlas.daxpy(-1, xmatrix, cmatrix);
    		System.out.println("magin: " + cmatrix);
    		System.out.println();
        }	
        
        SeqBlas.seqBlas.assign(xmatrix, new DFuncImpl(ReLuType.Discrete));
    	System.out.println(xmatrix);
    	System.out.println();
	}
}
