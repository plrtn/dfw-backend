# Requirements

Project is using Java 17 for `Record` syntax, and is written using Eclipse. Not sure how IntelliJ handles it. You may have to mess around with java versions installed in your machine. Fun stuff.

- `mvn install` for deps
- expose port for CORS for frontend (set to `5173` by default, but can be configured inside `DfwEndpoint.java`.
-  a `env.properties` file containing `app.database.username` and `app.database.password` must be created and provided.
