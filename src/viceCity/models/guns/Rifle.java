package viceCity.models.guns;

public class Rifle extends BaseGun {
    private final static int bulletsPerBarrel = 50;
    private final static int totalBullets = 500;
    private final static int rifleFiredBullets = 5;

    public Rifle(String name) {
        super(name, bulletsPerBarrel, totalBullets);
    }

    @Override
    public int fire() {
        if (this.getBulletsPerBarrel() == 0) {
            if (this.getTotalBullets() > 0) {
                int totalBulletsLeft = getTotalBullets() - bulletsPerBarrel;
                this.setBulletsPerBarrel(bulletsPerBarrel);
                this.setTotalBullets(totalBulletsLeft);
            }
            if (this.getBulletsPerBarrel() == 0) {
                return 0;
            }
        }
        this.setBulletsPerBarrel(this.getBulletsPerBarrel() - rifleFiredBullets);
        return rifleFiredBullets;
        //should fire 5 bullets
        // Some issue here fix it tomorrow.
    }
}
