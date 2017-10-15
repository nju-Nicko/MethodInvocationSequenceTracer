package vo.info_obj;

/**
 * 路径约束类
 * @author 倪陆章
 *
 */
public class ConstraintInfo {
	
	public String info;
	
	public ConstraintInfo(){
		info=null; //null表示无约束
	}
	
	public ConstraintInfo(String i){
		info=i;
	}
	
	public String toString(){
		return info==null?"true":info;
	}
	
	/**
	 * 与操作
	 * @param c 另一个PC
	 * @return 合取后的PC
	 */
	public ConstraintInfo and(ConstraintInfo c){
		ConstraintInfo newInfo=new ConstraintInfo();
		if(info==null||c.info==null)
			newInfo.info=(info==null)?c.info:info;
		else{
			newInfo.info="("+info+"&&"+c.info+")";
		}
		return newInfo;
	}
	
	/**
	 * 或操作
	 * @param c 另一个PC
	 * @return 析取后的PC
	 */
	public ConstraintInfo or(ConstraintInfo c){
		ConstraintInfo newInfo=new ConstraintInfo();
		if(info==null||c.info==null)
			newInfo.info=null;
		else{
			newInfo.info="("+info+"||"+c.info+")";
		}
		return newInfo;
	}
	
	/**
	 * 取反操作
	 * @return 取反后的PC
	 */
	public ConstraintInfo not(){
		ConstraintInfo newInfo=new ConstraintInfo();
		if(info==null){
			newInfo.info="false";
		}
		else
			newInfo.info="!"+info;
		return newInfo;
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof ConstraintInfo))
			return false;
		ConstraintInfo c=(ConstraintInfo)obj;
		if(info==null){
			return c.info==null;
		}
		return info.equals(c.info);
	}

}
