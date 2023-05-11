package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

public class PointLight extends Light implements LightSource {

    private final Point position;
    /**
     * kConstant, kLinear and kQuadratic are constants variables for attenuation
     * kLinear -  for fixed attenuation dependent on distance
     * kConstant - for fixed attenuation non-dependent on distance
     * kQuadratic - for fixed attenuation depending on the square distance
     */
    private double kConstant = 1d;
    private double kLinear = 0d;
    private double kQuadratic = 0d;

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point point) {
        Color Ic = this.getIntensity();
        double distance = alignZero(point.distance(this.position));
        double factor = this.kConstant + this.kLinear * distance + this.kQuadratic * distance * distance;
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
     * kConstant - set fixed attenuation non-dependent on distance
     */
    public PointLight setkConstant(double kConstant) {
        this.kConstant = kConstant;
        return this;
    }

    /**
     * kLinear -  set fixed attenuation dependent on distance
     */
    public PointLight setkLinear(double kLinear) {
        this.kLinear = kLinear;
        return this;
    }

    /**
     * kQuadratic - for fixed attenuation depending on the square distance
     */
    public PointLight setkQuadratic(double kQuadratic) {
        this.kQuadratic = kQuadratic;
        return this;
    }
}
