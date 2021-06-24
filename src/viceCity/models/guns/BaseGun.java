package viceCity.models.guns;

import viceCity.common.ExceptionMessages;

public abstract class BaseGun implements Gun {
    private String name;
    private int bulletsPerBarrel;
    private int totalBullets;

    protected BaseGun(String name, int bulletsPerBarrel, int totalBullets) {
        this.setName(name);
        this.setBulletsPerBarrel(bulletsPerBarrel);
        this.setTotalBullets(totalBullets);
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException
                    (ExceptionMessages.NAME_NULL);
        }
        this.name = name;
    }

    protected void setBulletsPerBarrel(int bulletsPerBarrel) {
        if (bulletsPerBarrel < 0) {
            throw new IllegalArgumentException
                    (ExceptionMessages.BULLETS_LESS_THAN_ZERO);
        } else {
            this.bulletsPerBarrel = bulletsPerBarrel;
        }

    }

    protected void setTotalBullets(int totalBullets) {
        if (totalBullets < 0) {
            throw new IllegalArgumentException
                    (ExceptionMessages.TOTAL_BULLETS_LESS_THAN_ZERO);
        } else {
            this.totalBullets = totalBullets;
        }

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getBulletsPerBarrel() {
        return this.bulletsPerBarrel;
    }


    @Override
    public int getTotalBullets() {
        return this.totalBullets;
    }

    @Override
    public boolean canFire() {
        return this.getBulletsPerBarrel() > 0 || this.getTotalBullets() > 0;

    }
}
