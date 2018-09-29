package infrastructure;

import java.sql.Types;

public final class MessageMetadata extends MetadataMapper {

  public static final String ID = "id";
  public static final String FROM = "from";
  public static final String TO = "to";
  public static final String CONTENT = "content";
  public static final String STATUS = "status";
  public static final String EXTERNAL_ID = "externalID";

  public MessageMetadata() {
    super(new DataMap("message", "M"));
    this.getDataMap().addColumn("id", Types.BIGINT, ID);
    this.getDataMap().addColumn("`from`", Types.VARCHAR, FROM);
    this.getDataMap().addColumn("`to`", Types.VARCHAR, TO);
    this.getDataMap().addColumn("content", Types.VARCHAR, CONTENT);
    this.getDataMap().addColumn("status", Types.VARCHAR, STATUS);
    this.getDataMap().addColumn("external_id", Types.VARCHAR, EXTERNAL_ID);
  }
}
