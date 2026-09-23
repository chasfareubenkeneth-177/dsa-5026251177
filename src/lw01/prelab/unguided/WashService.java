package lw01.prelab.unguided;

public abstract class WashService implements Billable {
    private String id;
    private int days;

    protected WashService(String id, int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Jumlah hari harus bernilai positif.");
        }
        this.id = id;
        this.days = days;
    }

    public String getId() {
        return id;
    }

    public int getDays() {
        return days;
    }

    public int calculateCharge(int units) {
        if (units <= 0) {
            throw new IllegalArgumentException("Jumlah unit harus bernilai positif.");
        }
        return units * calculateCharge();
    }

    public String label() {
        return "Service";
    }

    public String summary() {
        return id + " | " + label() + " | " + calculateCharge();
    }
}

