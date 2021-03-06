import api.representations.RepresentationFactory;
import api.representations.json.JSONRepresentationFactory;
import api.representations.siren.SirenRepresentationFactory;
import api.representations.xml.XMLRepresentationFactory;
import api.representations.yaml.YAMLRepresentationFactory;
import com.fasterxml.jackson.jaxrs.yaml.JacksonYAMLProvider;
import com.fasterxml.jackson.jaxrs.yaml.YAMLMediaTypes;
import configuration.NotiConfiguration;
import io.dropwizard.setup.Environment;
import io.opentracing.util.GlobalTracer;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public final class RepresentationModule extends NotiModule {

  private static final String JSON_REPRESENTATION_FACTORY = "JSONRepresentationFactory";
  private static final String XML_REPRESENTATION_FACTORY = "XMLRepresentationFactory";
  private static final String SIREN_REPRESENTATION_FACTORY = "SirenRepresentationFactory";
  private static final MediaType APPLICATION_X_YAML_TYPE = new MediaType("application", "x-yaml");
  private static final MediaType TEXT_X_YAML_TYPE = new MediaType("text", "x-yaml");
  private static final MediaType TEXT_VND_TAML_TYPE = new MediaType("text", "vnd.yaml");

  public RepresentationModule(NotiConfiguration configuration, Environment environment) {
    super(configuration, environment);
  }

  @Override
  public void configure() {

    Map<MediaType, RepresentationFactory> representationIndustry = new HashMap<>();
    RepresentationFactory jsonRepresentationFactory =
        new JSONRepresentationFactory(GlobalTracer.get());
    RepresentationFactory xmlRepresentationFactory =
        new XMLRepresentationFactory(GlobalTracer.get());
    RepresentationFactory sirenRepresentationFactory =
        new SirenRepresentationFactory(GlobalTracer.get());
    RepresentationFactory yamlRepresentationFactory =
        new YAMLRepresentationFactory(GlobalTracer.get());

    representationIndustry.put(jsonRepresentationFactory.getMediaType(), jsonRepresentationFactory);
    representationIndustry.put(xmlRepresentationFactory.getMediaType(), xmlRepresentationFactory);
    representationIndustry.put(
        sirenRepresentationFactory.getMediaType(), sirenRepresentationFactory);
    representationIndustry.put(
        YAMLMediaTypes.APPLICATION_JACKSON_YAML_TYPE, yamlRepresentationFactory);
    representationIndustry.put(YAMLMediaTypes.TEXT_JACKSON_YAML_TYPE, yamlRepresentationFactory);
    representationIndustry.put(APPLICATION_X_YAML_TYPE, yamlRepresentationFactory);
    representationIndustry.put(TEXT_X_YAML_TYPE, yamlRepresentationFactory);
    representationIndustry.put(TEXT_VND_TAML_TYPE, yamlRepresentationFactory);

    // register representation factories with environment.
    this.getEnvironment()
        .jersey()
        .register(
            new AbstractBinder() {
              @Override
              protected void configure() {
                this.bind(representationIndustry)
                    .to(new TypeLiteral<Map<MediaType, RepresentationFactory>>() {});
                this.bind(JSONRepresentationFactory.class)
                    .named(JSON_REPRESENTATION_FACTORY)
                    .to(RepresentationFactory.class);
                this.bind(XMLRepresentationFactory.class)
                    .named(XML_REPRESENTATION_FACTORY)
                    .to(RepresentationFactory.class);
                this.bind(SirenRepresentationFactory.class)
                    .named(SIREN_REPRESENTATION_FACTORY)
                    .to(RepresentationFactory.class);
              }
            });

    this.getEnvironment().jersey().register(JacksonYAMLProvider.class);
  }
}
