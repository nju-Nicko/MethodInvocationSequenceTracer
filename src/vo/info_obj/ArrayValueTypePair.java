package vo.info_obj;

public class ArrayValueTypePair extends ValueTypePair{
	
	public int first_address;

	public ArrayValueTypePair(String t, int f) {
		super(t);
		// TODO Auto-generated constructor stub
		this.first_address=f;
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof ArrayValueTypePair))
			return false;
		ArrayValueTypePair vtp=(ArrayValueTypePair)obj;
		if(! (vtp instanceof ArrayValueTypePair))
			return false;
		else{
			if(((ArrayValueTypePair)vtp).type.toString().equals(this.type.toString())&&
					((ArrayValueTypePair)vtp).first_address==this.first_address)
				return true;
		}
		return false;
	}
	
	@Override
	public ValueTypePair clone(){
		return new ArrayValueTypePair(this.type, first_address);
	}

}
