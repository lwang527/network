package com.hidi;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.SeqBlas;
import cern.colt.matrix.linalg.SmpBlas;

public class Dense {
	private double [][]xarray;
	private double [][]param;
	private ReLuType rlu;

	public Dense(double [][]xarray, double [][]param, ReLuType rlu){
		this.xarray = xarray;
		this.param = param;
		this.rlu = rlu;
	}
	
	public DoubleMatrix2D compute(){
		//input
		DoubleMatrix2D xmatrix = new DenseDoubleMatrix2D(xarray.length,xarray[0].length);
		xmatrix.assign(xarray);
		
		//parameter
		DoubleMatrix2D pmatrix = new DenseDoubleMatrix2D(param.length,param[0].length);
		pmatrix.assign(param);
    	
    	//split the A array to sub array [A0,Am]
		DoubleMatrix2D suba0 = pmatrix.viewPart(0, 0, pmatrix.rows(), 1);
		DoubleMatrix2D subam = pmatrix.viewPart(0, 1, pmatrix.rows(), pmatrix.columns() - 1);
    	
    	//initialize the constant x array
    	double [][] subxarr = MatrixUtil.initmatrix(suba0.columns(), xmatrix.columns());
    	DoubleMatrix2D subxmatrix = new DenseDoubleMatrix2D(subxarr.length,subxarr[0].length);
    	subxmatrix.assign(subxarr);
    	
    	DoubleMatrix2D multia0 = new DenseDoubleMatrix2D(suba0.rows(), subxmatrix.columns());
    	DoubleMatrix2D multi = new DenseDoubleMatrix2D(suba0.rows(), subxmatrix.columns());
    	//A0*1
    	SmpBlas.smpBlas.dgemm(false, false, 1, suba0, subxmatrix, 1, multia0);
    	//Am*Xm
    	SmpBlas.smpBlas.dgemm(false, false, 1, subam, xmatrix, 1, multi);
    	//A0 + Am*Xm
    	SeqBlas.seqBlas.daxpy(1, multia0, multi);
    	MatrixUtil.processmatrix(multi, rlu);
    	System.out.println(multi);
    	return multi;
	}
}
