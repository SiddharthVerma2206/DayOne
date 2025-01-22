package entity;

public class Weapon {
	private String name ;
	private int reloadTime , delTime , damage, maxBullets;
	private String imagePath;
	
	public Weapon(String name , int reloadTime , int delTime, int maxBullets , int damage , String imagePath) {
		this.name = name ;
		this.reloadTime = reloadTime ;
		this.delTime = delTime ;
		this.maxBullets = maxBullets;
		this.damage = damage;
		this.imagePath = imagePath;
	}
	
	// Getters
    public int getReloadTime() { return reloadTime; }
    public int getDelTime() { return delTime; }
    public int getDamage() { return damage; }
    public int getMaxBullets() { return maxBullets; }
    public String getImagePath() { return imagePath; }
    public String getName() { return name; }
}
