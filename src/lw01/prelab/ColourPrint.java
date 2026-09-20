package lw01.prelab;

public class ColourPrint extends PrintJob {

    public ColourPrint(String id, int pages) {
        super(id, pages);
    }

    @Override
    public int calculateCharge() {
        int pages = getPages();
        int charge = 2000; // Biaya setup per copy

        if (pages > 10) {
            charge += (10 * 1500) + ((pages - 10) * 1000);
        } else {
            charge += (pages * 1500);
        }
        return charge;
    }

    @Override
    public String label() {
        return "Colour";
    }
}
