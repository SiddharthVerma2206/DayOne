package main;

public class CollisionChecker {
	GamePanel gp;
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public boolean circleCollision(double x1, double y1, double r1, double x2, double y2, double r2) {
	    double distanceSquared = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
	    double radiusSum = r1 + r2;
	    return distanceSquared < radiusSum * radiusSum;
	}
	
}
