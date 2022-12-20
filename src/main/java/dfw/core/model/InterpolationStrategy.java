package dfw.core.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum InterpolationStrategy {

    CENTROID(1, InterpolationStrategy.CENTROID_SQL), AREAL_PROPORTION(2, InterpolationStrategy.AREAL_PROPORTION_SQL);

    private static final String CENTROID_SQL = """
            with f(lat, long, rad) as (
            	select ?, ?, ?
            )
            , point(p) as (
            	select st_setsrid(st_point(long, lat), 4326)::geography from f
            )
            , centroids(id, pop, inc, cent) as (
            	select "Key", population, income, ST_CENTROID(spatialobj)::geography from dfw_demo
            )
            , calc (id, pop, inc, ratio) as (
            	select id, pop, inc, 1
            	from centroids
            	cross join point
            	cross join f
            	where ST_DWithin(point.p, cent, f.rad, true)
            )
            select id, pop, inc, ratio from calc;
            """;

    private static final String AREAL_PROPORTION_SQL = """
            with f(lat, long, rad) as (
            	 select ?, ?, ?
            )
            , circle(c) as (
            	select st_buffer(st_setsrid(st_point(long, lat), 4326)::geography, f.rad, 32) from f
            )
            , calc(id, inc, pop, area_intersect, area) as (
            	select "Key", income, population, st_intersection(spatialobj, circle.c), spatialobj from dfw_demo
            	cross join circle
            	where st_intersects(spatialobj, circle.c)
            )
            select id, pop, inc, st_area(area_intersect, true)/st_area(area, true) as ratio from calc;
            """;

    @Getter
    private int id;

    @Getter
    @NonNull
    private String sql;

    public static InterpolationStrategy fromId(int id) {
        for (InterpolationStrategy is : InterpolationStrategy.values()) {
            if (is.getId() == id) {
                return is;
            }
        }

        throw new EnumConstantNotPresentException(InterpolationStrategy.class, String.valueOf(id));
    }

}
