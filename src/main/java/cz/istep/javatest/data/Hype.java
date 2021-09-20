package cz.istep.javatest.data;

public class Hype {

    private int positive;
    private int negative;

    public Hype(int positive, int negative) {
        this.positive = positive;
        this.negative = negative;
    }

    public int getPositive() {
        return positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public int getNegative() {
        return negative;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    @Override
    public String toString() {
        return "Hype{" +
                "positive=" + positive +
                ", negative=" + negative +
                '}';
    }
}
