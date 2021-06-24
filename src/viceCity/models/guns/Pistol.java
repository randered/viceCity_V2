package viceCity.models.guns;

public class Pistol extends BaseGun {
    private final static int pistolBarrel = 10;
    private final static int totalBulletsPistol = 50;
    private final static int pistolFiredBullets = 1;

    public Pistol(String name) {
        super(name, pistolBarrel, totalBulletsPistol);
    }

    @Override
    public int fire() {
        //should fire 1 bullet
        // should reload
        if(this.getBulletsPerBarrel() == 0){
            this.reload();
            if(this.getBulletsPerBarrel() == 0){
                return 0;
            }
        }
        this.setBulletsPerBarrel(this.getBulletsPerBarrel() - pistolFiredBullets);
        return pistolFiredBullets;
    }
    private void reload(){
        if(this.getTotalBullets() > 0){
            int totalBulletsLeft = this.getTotalBullets() - pistolBarrel;
            this.setTotalBullets(totalBulletsLeft);
            this.setBulletsPerBarrel(pistolBarrel);
        }
    }
}
