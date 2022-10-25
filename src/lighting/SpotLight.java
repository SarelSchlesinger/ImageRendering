package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {

    private final Vector direction;
    private double narrowBeam = 0d;

    protected SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point point) {
        Color Ic = super.getIntensity(point);
        double Lv = this.getL(point).dotProduct(this.direction);
        double factor = Math.max(0, Lv);
        return Ic.scale(factor);
    }

    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    public double getNarrowBeam() {
        return this.narrowBeam;
    }
}
