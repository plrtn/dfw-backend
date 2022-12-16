package dfw.core.model;

import java.io.InputStream;

public record Shape(long id, InputStream geojson) { }
