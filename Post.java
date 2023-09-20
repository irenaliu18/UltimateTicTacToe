package objects;

public class Post {
	
	public String id;
	public int x, y, w, h, big, small;

	public Post() {
		
	}
	
	public Post(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
	
	public int getBig() {
		return big;
	}
	
	public int getSmall() {
		return small;
	}

}