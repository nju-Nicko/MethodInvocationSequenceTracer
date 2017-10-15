package business_logic.memory;

import java.util.Iterator;
import java.util.Map;

import util.InitialValues;
import vo.common_obj.FieldInfo;
import vo.common_obj.FieldName;
import vo.memory_obj.ClassInfo;
import vo.memory_obj.FieldTable;
import vo.memory_obj.Instance;

/**
 * 内存堆区，存放对象实例
 * @author 倪陆章
 *
 */
public class InstanceSet {
		public Instance[] objs;
		public int currentSize;
		public int maxSize;
		
		public InstanceSet(){
			objs=new Instance[InitialValues.ObjPoolInitSize];  //初始分配这些对象空间
			maxSize=InitialValues.ObjPoolInitSize;
			currentSize=0;
		}
		
		/**
		 * 对象创建过程中的分配内存阶段。
		 * @param classname 类名
		 * @return 该对象的在池中的位置
		 */
		public int alloc(ClassInfo cl){
			if(currentSize==maxSize){
				resize();
			}
			int pointer=currentSize;
	        FieldTable ft=cl.ft;
			Iterator<Map.Entry<FieldName, FieldInfo>> ite=ft.fields.entrySet().iterator();
			Instance i=new Instance(cl.qualifiedName);
			while(ite.hasNext()){
				Map.Entry<FieldName, FieldInfo> e=ite.next();
				FieldInfo fi=e.getValue();
				int access_flags=fi.access_flags;
				if(!((access_flags&0x0008)==0x0008)){
					i.addOneField(fi.clone());
				}
			}
			objs[pointer]=i;
			currentSize++;
			return pointer;
		}
		
		/**
		 * 当对象池大小不够时重新分配空间
		 */
		public void resize(){
			Instance[] tmp=new Instance[maxSize];
			for(int i=0; i<=maxSize-1; i++){
				tmp[i]=objs[i];
			}
			objs=new Instance[2*maxSize];
			for(int i=0; i<=maxSize-1; i++){
				objs[i]=tmp[i];
			}
			maxSize=maxSize*2;
		}
		
		/**
		 * 复制堆区域
		 */
		@Override
		public InstanceSet clone(){
			InstanceSet jh=new InstanceSet();
			jh.currentSize=this.currentSize;
			jh.maxSize=this.maxSize;
			jh.objs=new Instance[jh.maxSize];
			for(int i=0; i<=this.currentSize-1; i++)
				jh.objs[i]=this.objs[i].clone();
			return jh;
		}
}
