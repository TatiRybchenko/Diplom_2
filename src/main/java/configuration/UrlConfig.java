package configuration;

import org.aeonbits.owner.Config;

@Config.Sources({"file:src/main/conf.properties"})
public interface UrlConfig extends Config {

   @Key("url.base")
    String baseUrl();
}
