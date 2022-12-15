package geometries;

import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * This class represents a group of shapes in the space that represent a picture.
 * Composite class which includes components and composite geometries.
 */
public class Geometries extends Intersectable {

    /**
     * geometriesList - list of all components in the scene
     */
    public List<Intersectable> geometriesList = new LinkedList<>();

    public Geometries(Intersectable... geometriesList) {

        this.add(geometriesList);
    }

    public void add(Intersectable... newGeometriesList) {
        this.geometriesList.addAll(Arrays.asList(newGeometriesList));
    }

    /**
     * This method returns all intersection points with this group of shapes
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        for (Intersectable geometry : this.geometriesList) {
            List<GeoPoint> geometryIntersections = geometry.findGeoIntersections(ray, maxDistance);
            if (geometryIntersections != null) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                intersections.addAll(geometryIntersections);
            }
        }
        return intersections;
    }

    @Override
    public String toString() {
        return "Geometries{" +
                "geometriesList=" + geometriesList +
                "} " + super.toString();
    }
}
