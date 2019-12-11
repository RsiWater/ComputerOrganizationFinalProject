
public class Pipline {
	public static void main(String[] args) {
		RegIDEX idex=new RegIDEX();
		ID id=new ID(idex);//IDEX需在main先創建
		id.startID(4266016);//每次使用ID呼叫startID並傳入instruction
		id.startID(268435456);
	}
}
