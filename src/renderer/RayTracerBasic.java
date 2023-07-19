package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracer {

    private static final double DELTA = 0.1;
    /**
     * Depth of recursion for color calculate
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * Initial coefficient for recursive calculations of reflection and refraction
     */
    private static final Double3 INITIAL_K = Double3.ONE;

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        if (this.findClosestIntersection(ray) == null) {
            return this.scene.getBackground();
        }
        return this.calcColor(this.findClosestIntersection(ray), ray);
    }

    /**
     * calculate color using recursion
     *
     * @param geoPoint the closest intersection point between the ray and the object
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return this.calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                   .add(this.scene.getAmbientLight().getIntensity());
    }

    /**
     * A method that returns the color of the intersection point between a ray and an object.
     * If there is no intersection point the method returns the ambient light color.
     *
     * @param geoPoint the closest intersection point between the ray and the object
     * @param level    number of iterations in the recursion
     * @param k        coefficient for recursive calculations of reflection and refraction
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(geoPoint, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray.getDirection(), level, k));
    }

    /**
     * Method to calculate effects of diffusive and specular components
     *
     * @param geoPoint geometry and intersection point
     * @param ray      ray from camera
     * @return color includes diffusion and specular effects
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
        Color color = geoPoint.getGeometry().getEmission();;
        Vector normal = geoPoint.getGeometry().getNormal(geoPoint.getPoint());
        double nv = alignZero(normal.dotProduct(ray.getDirection()));
        if (nv == 0) return Color.BLACK;
        Material material = geoPoint.getGeometry().getMaterial();
        for (LightSource lightSource : scene.getLights()) {
            Vector lightDirection = lightSource.getLight(geoPoint.getPoint());
            double nl = alignZero(normal.dotProduct(lightDirection));
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                Double3 ktr = this.transparency(geoPoint, lightSource, lightDirection, normal, nv);
                //if (unshaded(geoPoint, lightSource, normal, nl, nv)) {
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = lightSource.getIntensity(geoPoint.getPoint()).scale(ktr);
                    color = color.add(lightIntensity.scale(calcDiffusive(material, nl)),
                                      lightIntensity.scale(calcSpecular(material, normal, lightDirection, nl, ray.getDirection())));
                }
            }
        }
        return color;
    }


    /**
     * Method to calculate transparency and reflections.
     *
     * @param geoPoint  intersection point
     * @param direction direction vector
     * @param level     number of iterations in the recursion
     * @param k         coefficient for recursive calculations of reflection and refraction
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Vector direction, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector normal = geoPoint.getGeometry().getNormal(geoPoint.getPoint());
        Double3 kkr = geoPoint.getGeometry().getMaterial().getKr().product(k);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            color = calcGlobalEffect(constructReflectedRay(geoPoint.getPoint(), direction, normal), level, geoPoint.getGeometry().getMaterial().getKr(), kkr);
        }
        Double3 kkt = geoPoint.getGeometry().getMaterial().getKt().product(k);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(geoPoint.getPoint(), direction, normal), level, geoPoint.getGeometry().getMaterial().getKt(), kkt));
        }
        return color;
    }

    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint geoPoint = findClosestIntersection(ray);
        return (geoPoint == null ? scene.getBackground() : calcColor(geoPoint, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * Method to find the closest intersection point to the ray's origin
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * Method to construct a new ray that reflected from the hit point where the main ray hits
     *
     * @param point hit's point of the main ray on the surface of the geometry
     */
    private Ray constructReflectedRay(Point point, Vector direction, Vector normal) {
        double nd = normal.dotProduct(direction);
        if (nd == 0) {
            return null;
        }
        Vector directionOfReflection = direction.subtract(normal.scale(2 * nd));
        return new Ray(point, directionOfReflection, normal);
    }

    /**
     * Method to construct a new ray that refracted from the hit point where the main ray hits
     *
     * @param point hit's point of the main ray on the surface of the geometry
     */
    private Ray constructRefractedRay(Point point, Vector direction, Vector normal) {
        return new Ray(point, direction, normal);
    }

    /**
     * Method to calculate light's diffusion
     *
     * @param nl the cosine of the angle between the vectors of the light source and the normal
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.getKd().scale(Math.abs(nl));
    }

    /**
     * Method to calculate light's specular component
     */
    private Double3 calcSpecular(Material material, Vector normal, Vector lightDirection, double nl, Vector rayDirection) {
        Vector r = lightDirection.add(normal.scale(-2 * nl)).normalize();
        if (-alignZero(r.dotProduct(rayDirection)) <= 0) {
            return Double3.ZERO;
        }
        return material.getKs().scale(Math.pow(-alignZero(r.dotProduct(rayDirection)), material.getShininess()));
    }

    /**
     * Boolean method to check if a given point is unshaded
     */
    @Deprecated
    private boolean unshaded(GeoPoint geoPoint, LightSource lightSource, Vector normal, double nl, double nv) {
        Vector lightDirection = lightSource.getLight(geoPoint.getPoint()).scale(-1);
        Vector deltaVector = normal.scale(nv < 0 ? DELTA : -DELTA);
        Ray lightRay = new Ray(geoPoint.getPoint().add(deltaVector), lightDirection);
        double maxDistance = lightSource.getDistance(geoPoint.getPoint());
        List<GeoPoint> intersections = this.scene.getGeometries().findGeoIntersections(lightRay, maxDistance);
        for (GeoPoint intersectionPoint : intersections) {
            if (alignZero(intersectionPoint.getPoint().distance(geoPoint.getPoint()) - maxDistance) <= 0 &&
                    intersectionPoint.getGeometry().getMaterial().getKt().equals(Double3.ZERO)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method to calculate the transparency coefficient of all geometries between geoPoint to the light source
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource lightSource, Vector lightDirection, Vector normal, double nv) {
        Vector lightDirectionFromGeoPointToLightSource = lightDirection.scale(-1);
        Ray lightRay;
        if (nv < 0) {
            lightRay = new Ray(geoPoint.getPoint(), lightDirectionFromGeoPointToLightSource, normal);
        } else {
            lightRay = new Ray(geoPoint.getPoint(), lightDirectionFromGeoPointToLightSource, normal.scale(-1));
        }
        double maxDistance = lightSource.getDistance(geoPoint.getPoint());
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay, maxDistance);
        if (intersections == null) {
            return Double3.ONE;
        }
        Double3 ktr = Double3.ONE;
        for (GeoPoint intersectionPoint : intersections) {
            ktr = ktr.product(intersectionPoint.getGeometry().getMaterial().getKt());
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                return ktr;
            }
        }
        return ktr;
    }
}