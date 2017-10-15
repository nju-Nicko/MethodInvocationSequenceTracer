package business_logic.memory;

import util.InitialValues;
import vo.common_obj.VariableInfo;

/**
 * 数组存储区
 * @author 倪陆章
 *
 */
public class ArrArea {
	
	public VariableInfo[][] arr;
	public int currentSize;
	public int maxSize;
	
	public ArrArea(){
		maxSize=InitialValues.arrAreaInitSize;
		arr=new VariableInfo[maxSize][];  //初始分配这几行
		currentSize=0;
	}
	
	/**
	 * 为一个cols列的数组分配空间并返回第一个单元的地址
	 * @param cols 列数
	 * @return 第一个单元的地址
	 */
	public int alloc(int cols){
		if(currentSize==maxSize){
			resize();
		}
		int pointer=currentSize;
		arr[pointer]=new VariableInfo[cols];
		currentSize++;
		return pointer;
	}
	
	/**
	 * 重新分配大小
	 */
	public void resize(){
		VariableInfo[][] tmp=new VariableInfo[maxSize][];
		for(int i=0; i<=maxSize-1; i++){
			tmp[i]=arr[i];
		}
		arr=new VariableInfo[2*maxSize][];
		for(int i=0; i<=maxSize-1; i++){
			arr[i]=tmp[i];
		}
		maxSize=2*maxSize;
	}
	
	/**
	 * 复制数组区域
	 */
	@Override
	public ArrArea clone(){
		ArrArea aa=new ArrArea();
		aa.currentSize=this.currentSize;
		aa.maxSize=this.maxSize;
		aa.arr=new VariableInfo[aa.maxSize][];
		for(int i=0; i<=this.currentSize-1; i++){
			for(int j=0; j<=this.arr[i].length-1; j++)
				aa.arr[i][j]=this.arr[i][j].clone();
		}
		return aa;
	}

}
