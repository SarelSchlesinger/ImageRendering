package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

public class PointLight extends Light implements LightSource {

    private final Point position;
    /**
     * kL, kC and kQ are constants variables for attenuation
     * kL -  for fixed attenuation dependent on distance
     * kC - for fixed attenuation non-dependent on distance
     * kQ - for fixed attenuation depending on the square distance
     */
    private double kL = 0d;
    private double kC = 1d;
    private double kQ = 0d;

    protected PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point point) {
        Color Ic = this.getIntensity();
        double distance = alignZero(point.distance(this.position));
        double factor = this.kC + this.kL * distance + this.kQ * distance * distance;
        return Ic.reduce(factor);
    }

    @Override
    public Vector getLight(Point point) {
        return point.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return this.position.distance(point);
    }

    /**
     * kL -  set fixed attenuation dependent on distance
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * kC - set fixed attenuation non-dependent on distance
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * kQ - for fixed attenuation depending on the square distance
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
}
