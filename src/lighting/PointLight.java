package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

    private final Point position;
    /**
     * kC, kL, kQ are constants variables for attenuation
     */
    private double kC = 1d;
    private double kL = 0d;
    private double kQ = 0d;

    protected PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point point) {
        Color Ic = this.getIntensity();
        double distance = point.distance(this.position);
        double factor = this.kC + this.kL * distance + this.kQ * distance * distance;
        return Ic.reduce(factor);
    }

    @Override
    public Vector getL(Point point) {
        return point.subtract(position).normalize();
    }

    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
}
