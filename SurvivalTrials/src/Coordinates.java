
public class Coordinates {
	int x=0;
	int y=0;
	Coordinates(){
		
	}
	Coordinates(int x,int y){
		this.x=x;
		this.y=y;
	}
	Coordinates(Coordinates c) {
		this.x=c.x;
		this.y=c.y;
	}
	

	void setCoord(int _x,int _y) {
		x=_x;
		y=_y;
	}
	public boolean equals(Coordinates c){
		return (this.x==c.x && this.y==c.y);
	}
	public Coordinates directionalCoord(int direction) {
		Coordinates ret=new Coordinates(this.x+D.XMOD(direction),this.y+D.YMOD(direction));
		return ret;
	}
	public void setDirection(int direction) {
		this.x+=D.XMOD(direction);
		this.y+=D.YMOD(direction);
	}
	public Coordinates north() {
		return directionalCoord(D.NORTH);
	}
	public Coordinates northwest() {
		return directionalCoord(D.NORTHWEST);
	}
	public Coordinates northeast() {
		return directionalCoord(D.NORTHEAST);
	}
	public Coordinates west() {
		return directionalCoord(D.WEST);
	}
	public Coordinates east() {
		return directionalCoord(D.EAST);
	}
	public Coordinates southeast() {
		return directionalCoord(D.SOUTHEAST);
	}
	public Coordinates southwest() {
		return directionalCoord(D.SOUTHWEST);
	}
	public Coordinates south() {
		return directionalCoord(D.SOUTH);
	}
	public void setCoord(Coordinates coord) {
		setCoord(coord.x, coord.y);
	}
	
	@Override
	public String toString(){
		return "("+this.x+","+this.y+")";
	}
	
}
