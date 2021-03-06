package infrastructure.query.expressions;

/**
 * Terminal expression representing a placeholder.
 *
 * <p>See Interpreter pattern.
 *
 * @author Jon Freer
 */
public final class PlaceholderExpression extends TerminalExpression {

  /** Constructs a new {@link PlaceholderExpression}. */
  public PlaceholderExpression() {
    super(true);
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  public String interpret() {
    return "?";
  }
}
