package vo.info_obj;

public class ReferenceValueTypePair extends ValueTypePair{
	
	public int handle;

	public ReferenceValueTypePair(String t, int handle) {
		super(t);
		// TODO Auto-generated constructor stub
		this.handle=handle;
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof ReferenceValueTypePair))
			return false;
		ReferenceValueTypePair vtp=(ReferenceValueTypePair)obj;
		if(!(vtp instanceof ReferenceValueTypePair))
			return false;
		else{
			if(((ReferenceValueTypePair)vtp).type.toString().equals(this.type.toString())&&
					((ReferenceValueTypePair)vtp).handle==this.handle)
				return true;
		}
		return false;
	}
	
	@Override
	public ValueTypePair clone(){
		return new ReferenceValueTypePair(type, handle);
	}

}
