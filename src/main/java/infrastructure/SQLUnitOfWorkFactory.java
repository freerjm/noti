package infrastructure;

import domain.Audience;
import domain.EntitySQLFactory;
import domain.Notification;
import domain.Target;
import domain.Template;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Named;

public class SQLUnitOfWorkFactory extends UnitOfWorkFactory {

  private final EntitySQLFactory<Notification, UUID> notificationFactory;
  private final EntitySQLFactory<Target, UUID> targetFactory;
  private final EntitySQLFactory<Audience, UUID> audienceFactory;
  private final EntitySQLFactory<Template, UUID> templateFactory;
  private final ConnectionFactory connectionFactory;
  private final Tracer tracer;

  @Inject
  public SQLUnitOfWorkFactory(
      @Named("NotificationSQLFactory") EntitySQLFactory<Notification, UUID> notificationFactory,
      @Named("TargetSQLFactory") EntitySQLFactory<Target, UUID> targetFactory,
      @Named("AudienceSQLFactory") EntitySQLFactory<Audience, UUID> audienceFactory,
      @Named("TemplateSQLFactory") EntitySQLFactory<Template, UUID> templateFactory,
      ConnectionFactory connectionFactory,
      Tracer tracer) {
    this.notificationFactory = notificationFactory;
    this.targetFactory = targetFactory;
    this.audienceFactory = audienceFactory;
    this.templateFactory = templateFactory;
    this.connectionFactory = connectionFactory;
    this.tracer = tracer;
  }

  @Override
  public UnitOfWork createUnitOfWork() {
    String className = SQLRepositoryFactory.class.getName();
    String spanName = String.format("%s#createNotificationRepository", className);
    final Span span = this.tracer.buildSpan(spanName).asChildOf(this.tracer.activeSpan()).start();
    try (final Scope scope = this.tracer.scopeManager().activate(span, false)) {
      UnitOfWork uow =
          new SQLUnitOfWork(
              this.connectionFactory,
              this.notificationFactory,
              this.targetFactory,
              this.audienceFactory,
              this.templateFactory,
              this.tracer);
      return uow;
    } finally {
      span.finish();
    }
  }
}
